package com.houseprojectbackend.dao;

import com.houseprojectbackend.po.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface UserRepository extends JpaRepository<UserInformation,Long> {
    UserInformation findUserInformationByUsernameAndPass(String Username,String Pass);
    Integer countUserInformationByUsername(String Username);
}
