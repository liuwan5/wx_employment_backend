package com.wx.employment.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wx.employment.PO.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * @author wbl
 * @description: JWT的工具类
 */

public class JwtUtil {
    /**
     * 秘钥
     */
    private static final String SECRET = "fight wins";
    /**
     * 有效期
     */
    private static final int EXPIRE_TIME = 60*24*7;

    /**
     * @description: 创建一个Token
     */
    public static String getToken(User user) {
        JWTCreator.Builder builder = JWT.create();
        // 添加实体信息，即payload
        builder.withClaim("uid", user.getUid());
        builder.withClaim("openid",user.getOpenid());

        // 过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, EXPIRE_TIME);
        builder.withExpiresAt(instance.getTime());

        // 选择使用的算法 和 秘钥
        //System.out.println("签发一个token:"+builder.sign(Algorithm.HMAC256(SECRET)));
        return builder.sign(Algorithm.HMAC256(SECRET));
    }


    /**
     * @description: 验证一个token.
     * @description: 注意：verify会抛出异常，既定的运行时异常
     */
    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * @description: 获取token包含的payload信息，即User
     */
    public static User getTokenInfo(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        User user = new User();
        user.setUid(decodedJWT.getClaim("uid").asInt());
        user.setOpenid(String.valueOf(decodedJWT.getClaim("openid").asInt()));
        return user;
    }


    public static UidWithExpire getUidWithExpire(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        UidWithExpire uidWithExpire = new UidWithExpire();
        uidWithExpire.uid = decodedJWT.getClaim("uid").asInt();
        uidWithExpire.expire = decodedJWT.getExpiresAt();
        return uidWithExpire;
    }

    public static User getNowUser(HttpServletRequest request){
        if(request.getHeader("token") == null){
            return null;
        }
        return JwtUtil.getTokenInfo(request.getHeader("token"));
    }

    public static class UidWithExpire{
        int uid;
        Date expire;

        public int getUid() {
            return uid;
        }

        public Date getExpire() {
            return expire;
        }
    }

}
