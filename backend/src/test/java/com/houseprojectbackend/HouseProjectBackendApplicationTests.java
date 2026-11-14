package com.houseprojectbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.houseprojectbackend.dao.HousetableRepository;
import com.houseprojectbackend.dao.UserRepository;
import com.houseprojectbackend.po.Housetable;
import com.houseprojectbackend.po.UserBehavior;
import com.houseprojectbackend.po.UserInformation;
import com.houseprojectbackend.service.recommendService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Temporal;
import java.util.HashMap;
import java.util.Optional;

import static java.lang.System.out;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableCaching
@SpringBootTest
@ComponentScan(basePackages = {"com.houseprojectbackend.service.*"})
class HouseProjectBackendApplicationTests {
    @Autowired
    private recommendService recommendServiceImpl;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HousetableRepository housetableRepository;

    @Test
    void testRecommend() {
        recommendServiceImpl.gatherInformation(new UserBehavior(2L, 11, "2023-04-26 19:19:19", "精装"));
    }

    @Test
    void getRedis() {
        out.println(recommendServiceImpl.RecommendedContent("11",""));
    }

    @Test
    void getHash() {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        out.println(hash);
    }
    @Test
    void TestUser(){
        out.println(userRepository.findUserInformationByUsernameAndPass("123456", "123456"));
        out.println(userRepository.countUserInformationByUsername("123456"));
    }

    @Test
    void TestHouse(){
        Housetable housetable = new Housetable();
        housetable.setHouseCity("越秀");
        housetableRepository.findAll(Example.of(housetable)).forEach(out::println);
    }

}
