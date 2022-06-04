package com.wx.employment.service;

import com.wx.employment.PO.User;
import com.wx.employment.VO.Location;
import com.wx.employment.common.Result;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author wbl
 */
public interface UserService {
    /**
     * 登录，如果是新用户，返回需要注册，否则返回user
     * @param jsCode
     * @return
     */
    public Result login(String jsCode);

    /**
     * 获取用户的地址
     * @Param request
     * @return
     */
    public Result<Location> getUserLocation(HttpServletRequest request);

    /**
     * 更新用户的地址信息
     * @param request
     * @param location
     * @return
     */
    public Result updateUserLocation(Location location,HttpServletRequest request);

    /**
     * 获取上次登录的时间
     * @param request
     * @return
     */
    public Result<LocalDateTime> getLastLoginTime(HttpServletRequest request);

}
