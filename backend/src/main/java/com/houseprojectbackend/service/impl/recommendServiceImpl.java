package com.houseprojectbackend.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.houseprojectbackend.dao.HousetableRepository;
import com.houseprojectbackend.dao.UserBehaviorRepository;
import com.houseprojectbackend.po.Housetable;
import com.houseprojectbackend.po.RequestResponse;
import com.houseprojectbackend.po.UserBehavior;
import com.houseprojectbackend.service.AvailabilityService;
import com.houseprojectbackend.service.ConfigService;
import com.houseprojectbackend.service.recommendService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class recommendServiceImpl extends ConfigService implements recommendService {
    @Autowired
    private UserBehaviorRepository userBehaviorRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HousetableRepository housetableRepository;
    @Autowired
    private AvailabilityService availabilityService;

    @Override
    public void gatherInformation(UserBehavior ub) {
        //行为写入MongoDB
        userBehaviorRepository.save(ub);
        //行为写入Kafka
        kafkaTemplate.send(topic(), ub.toString());
    }

    @Override
    public RequestResponse RecommendedContent(String id,String province) {
        try {
            System.out.println(id);
            //获取RedisTemplate Hash 操作对象
            HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
            //判断Redis Hash中是否有这个推荐列表
            Boolean findIdTF = hashOperations.hasKey(key(), id);
            RequestResponse response;
            //如果有该推荐列表则从Redis 中取出推荐列表 否则 从Mysql 中随机
            if (findIdTF) {
                //获取推荐
                List<Integer> recommendList = objectMapper.readValue(hashOperations.get(key(), id), ArrayList.class);
                response = RequestResponse.setData(housetableRepository.findAllById(recommendList)).success("成功");
                /**
                 * 推荐列表已经消费则清空推荐 或 向推荐服务发送请求再次推荐
                 * 向推荐服务请求再次推荐的则是根据用户的历史行为数据进行推荐
                 */
                kafkaTemplate.send(topic(),new UserBehavior(Integer.parseInt(id),recommended_again()).toString());
            } else {
                HashMap hash = new HashMap();
                hash.put("itemSize",0);
                hash.put("province",province);
                response = availabilityService.ComplexQueries(hash);
            }
            return response;
        } catch (JsonProcessingException e) {
            return RequestResponse.setData("").error("失败");
        }
    }
}
