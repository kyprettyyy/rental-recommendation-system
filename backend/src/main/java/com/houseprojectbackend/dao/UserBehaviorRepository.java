package com.houseprojectbackend.dao;

import com.houseprojectbackend.po.UserBehavior;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBehaviorRepository extends MongoRepository<UserBehavior,Integer> {
    UserBehavior findUserBehaviorByUserId(Integer userId);
}

