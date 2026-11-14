package com.houseprojectbackend.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@Document
public class UserBehavior {
    @Id
    private Long id;

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 行为时间
     */
    private String behaviorTime;
    /**
     * 点击内容
     */
    private String goods;

    @Override
    public String toString() {
        return userId+","+goods;
    }


    public UserBehavior(Integer userId, String goods) {
        this.userId = userId;
        this.goods = goods;
    }

    public UserBehavior(Long id, Integer userId, String behaviorTime, String goods) {
        this.id = id;
        this.userId = userId;
        this.behaviorTime = behaviorTime;
        this.goods = goods;
    }
}
