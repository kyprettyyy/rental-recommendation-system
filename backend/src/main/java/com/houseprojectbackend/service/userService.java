package com.houseprojectbackend.service;

import com.houseprojectbackend.po.RequestResponse;
import com.houseprojectbackend.po.UserInformation;

public interface userService {
    RequestResponse finByUserInfoUsernamePass(UserInformation userInformation);

    RequestResponse saveInfoUsername(UserInformation userInformation);

    RequestResponse countUserInformationByUsername(String Username);
}
