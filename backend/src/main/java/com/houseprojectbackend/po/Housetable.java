package com.houseprojectbackend.po;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
@Data
@ToString
@Entity
@Table(name = "housetable")
public class Housetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 地址区域
     */
    private String houseCity;

    /**
     * 小区名称
     */
    private String houseTitle;

    /**
     * 房屋总价
     */
    private String houseTotalMoney;

    /**
     * 房屋优势
     */
    private String houseAdvange;

    /**
     * 租聘方式
     */
    private String houseRentForm;

    /**
     * 房屋类型
     */
    private String houseType;

    /**
     * 房屋建筑面积
     */
    private String houseBuildingArea;

    /**
     * 房屋朝向
     */
    private String houseOrientation;

    /**
     * 维护情况
     */
    private String houseDecoration;

    /**
     * 入住时间
     */
    private String houseDate;

    /**
     * 所在楼层
     */
    private String houseFloor;

    /**
     * 电梯配备
     */
    private String houseElevator;

    /**
     * 有无燃气
     */
    private String houseFuelGas;

    /**
     * 租期
     */
    private String houseRentTerm;

    /**
     * 看房方式
     */
    private String houseWatch;

    /**
     * 配套设施
     */
    private String houseSupporting;

    /**
     * 房屋照片
     */
    private String houseImg;

    /**
     * 房屋URL地址
     */
    private String houseUrl;

    /**
     * 爬取时间
     */
    private String houseCrawlTime;
    /**
     *省份
     */
    private String province;
}

