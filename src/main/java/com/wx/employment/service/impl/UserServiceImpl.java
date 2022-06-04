package com.wx.employment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wx.employment.DAO.UserDao;
import com.wx.employment.PO.User;
import com.wx.employment.VO.Location;
import com.wx.employment.common.Result;
import com.wx.employment.config.Constant;
import com.wx.employment.service.UserService;
import com.wx.employment.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author wbl
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Result login(String jsCode) {
        try {
            String openid = getOpenidByJsCode(jsCode);
            if(openid == null){
                return Result.executeFailure("登录失败");
            }
            // 第一种方式直接输入列的字段，第二种方式使用lambda表达式，可以避免输入错误的字段
            //User user = userDao.getOne(new QueryWrapper<User>().eq("openid",openid));

            User user = userDao.getOne(new QueryWrapper<User>().lambda().eq(User::getOpenid, openid));
            // 第一次使用程序，自动注册登录
            if (user == null) {
                user = register(openid);
                if(user == null){
                    return Result.executeFailure( "登录失败");
                }
            }
            // 登录更新上次登录的时间
            LocalDateTime time = LocalDateTime.now();
            user.setLastLogin(time);
            userDao.updateById(user);
            // 返回token，下次请求就需要token
            return Result.executeSuccess("登录成功", JwtUtil.getToken(user));
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.toString());
            return Result.executeFailure( "登录失败");
        }
    }

    public User register(String openid) {
        try{
        User user = new User();
        LocalDateTime time = LocalDateTime.now();
        user.setOpenid(openid)
            .setCreatedTime(time)
            .setLastLogin(time);
        userDao.save(user);
        return user;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Result<Location> getUserLocation(HttpServletRequest request) {
        try {
            User nowUser = JwtUtil.getNowUser(request);
            // 人工确认未登录，未使用shiro
            if(nowUser == null){
                return Result.noLogin();
            }
            Location location = new Location();
            User user = userDao.getById(nowUser.getUid());
            BeanUtils.copyProperties(user, location);
            return Result.getSuccess(location);
        }catch (Exception e){
            log.error(e.toString());
            return Result.getFailure();
        }
    }

    @Override
    public Result updateUserLocation(Location location, HttpServletRequest request) {
        try {
            User nowUser = JwtUtil.getNowUser(request);
            // 人工确认未登录，未使用shiro
            if(nowUser == null){
                return Result.noLogin();
            }
            if(location.isValid()){
                LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                lambdaUpdateWrapper.eq(User::getUid, nowUser.getUid())
                        .set(User::getLocation, location.getLocation())
                        .set(User::getLatitude, location.getLatitude())
                        .set(User::getLongitude, location.getLongitude());
                userDao.update(null, lambdaUpdateWrapper);
                return Result.updSuccess();
            }
            else {
                return Result.executeFailure("地址不能为空");
            }
        }catch (Exception e){
            log.error(e.toString());
            return Result.updFailure();
        }
    }

    @Override
    public Result<LocalDateTime> getLastLoginTime(HttpServletRequest request) {
        User nowUser = JwtUtil.getNowUser(request);
        // 人工确认未登录，未使用shiro
        if(nowUser == null){
            return Result.noLogin();
        }
        try {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.select(User::getLastLogin).eq(User::getUid, nowUser.getUid());
            User user = userDao.getOne(lambdaQueryWrapper);
            return Result.getSuccess(user.getLastLogin());
        }catch (Exception e){
            log.error(e.toString());
            return Result.getFailure();
        }
    }

    /**
     * 根据临时code获取openid
     */
    public String getOpenidByJsCode(String jsCode){
        String openid = null;
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+ Constant.APPID +"&secret="+Constant.APPSECRET+"&js_code="+jsCode+"&grant_type=authorization_code";
        HttpGet httpGet = new HttpGet(url);

        // 响应模型
        CloseableHttpResponse response = null;

        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                String str = EntityUtils.toString(responseEntity);
//                System.out.println("响应内容为:" + str);
                JSONObject jsonObject = JSONObject.parseObject(str);
                openid = jsonObject.getString("openid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return openid;
    }
}
