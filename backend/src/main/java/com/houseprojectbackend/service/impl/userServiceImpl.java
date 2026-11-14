package com.houseprojectbackend.service.impl;

import com.houseprojectbackend.dao.UserRepository;
import com.houseprojectbackend.po.RequestResponse;
import com.houseprojectbackend.po.UserInformation;
import com.houseprojectbackend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public RequestResponse finByUserInfoUsernamePass(UserInformation userInformation) {
        UserInformation user = userRepository.findUserInformationByUsernameAndPass(userInformation.getUsername(), userInformation.getPass());
        RequestResponse requestResponse;
        if (user != null) {
            requestResponse = RequestResponse.setData(user).success("登陆成功");
        } else {
            requestResponse = RequestResponse.setData(null).success("帐号或密码错误");
        }
        return requestResponse;
    }

    @Override
    public RequestResponse saveInfoUsername(UserInformation userInformation) {
        return RequestResponse.setData(userRepository.save(userInformation)).success("注册成功");
    }

    @Override
    public RequestResponse countUserInformationByUsername(String Username) {
        return RequestResponse.setData(userRepository.countUserInformationByUsername(Username)).success("成功");
    }
}
