package com.houseprojectbackend.controller;

import cn.hutool.core.date.DateUtil;
import com.houseprojectbackend.po.RequestResponse;
import com.houseprojectbackend.po.UserBehavior;
import com.houseprojectbackend.service.recommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 推荐
 */
@RestController
@RequestMapping("/data")
public class recommendController {

    @Autowired
    private recommendService recommendService;

    /**
     * 推荐
     *
     * @param id 用户id
     * @return 推荐数据
     */
    @GetMapping("/{id}/{province}")
    public RequestResponse recommend(@PathVariable("id") String id,@PathVariable("province") String province) {
        return recommendService.RecommendedContent(id,province);
    }

    /***
     * 行为收集
     * @param id      用户ID
     * @param content 房屋ID
     */
    @PostMapping("/{id}/{content}")
    public RequestResponse Behavior_capture(@PathVariable("id") Integer id, @PathVariable("content") String content) {
        recommendService.gatherInformation(new UserBehavior(
                System.currentTimeMillis(),
                id,
                DateUtil.formatDateTime(new Date()),
                content
        ));
        return RequestResponse.setData("").success("成功");
    }

}
