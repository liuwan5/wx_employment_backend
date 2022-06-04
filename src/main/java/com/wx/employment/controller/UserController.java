package com.wx.employment.controller;


import com.wx.employment.PO.User;
import com.wx.employment.VO.Location;
import com.wx.employment.common.Result;
import com.wx.employment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wbl
 * @since 2022-06-03
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public Result login(@RequestParam String openid){
        return userService.login(openid);
    }

    @GetMapping("/getLocation")
    public Result getLocation(HttpServletRequest request){
        return userService.getUserLocation(request);
    }

    @PostMapping("/updateLocation")
    public Result updateLocation(@RequestBody Location location, HttpServletRequest request){
        return userService.updateUserLocation(location,request);
    }

    @GetMapping("/getLastLoginTime")
    public Result getLastLoginTime(HttpServletRequest request){
        return userService.getLastLoginTime(request);
    }

}
