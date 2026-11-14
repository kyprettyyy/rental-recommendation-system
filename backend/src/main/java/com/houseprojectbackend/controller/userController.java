package com.houseprojectbackend.controller;

import com.houseprojectbackend.po.RequestResponse;
import com.houseprojectbackend.po.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.houseprojectbackend.service.userService;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService;

    /**
     * 注册
     * @param userInfo
     * @return
     */
    @PostMapping("/")
    public RequestResponse userRegistration(UserInformation userInfo) {
        return userService.saveInfoUsername(userInfo);
    }

    /**
     * 查找该账户是否被注册
     * @param username
     * @return
     */
    @GetMapping("/{username}")
    public RequestResponse findUserExistsYesNo(@PathVariable("username")String username){
        return userService.countUserInformationByUsername(username);
    }

    /**
     * 登陆
     * @param userInfo
     * @return
     */
    @GetMapping("/")
    public RequestResponse login(UserInformation userInfo){
        return userService.finByUserInfoUsernamePass(userInfo);
    }
}
