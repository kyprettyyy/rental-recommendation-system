package com.houseprojectbackend.service;

import com.houseprojectbackend.po.RequestResponse;
import com.houseprojectbackend.po.UserBehavior;


public interface recommendService {
    /***
     * 收集点击信息
     * @return
     */
    void gatherInformation(UserBehavior ub);

    /**
     * 推荐内容
     *
     * @param id
     * @return
     */
    RequestResponse RecommendedContent(String id,String province);
}
