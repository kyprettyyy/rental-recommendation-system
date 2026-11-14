package com.houseprojectbackend.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RequestResponse<T> {

    //响应成功状态
    @JsonIgnore
    private final Integer success = 200;
    //响应失败
    @JsonIgnore
    private final Integer error = 400;
    /**
     * 回响消息
     */
    private String message;
    /**
     * 回响状态
     */
    private Integer resStatus;
    /**
     * 回响 数据
     */
    private T data;

    /**
     * 设置回响数据
     * @param object
     * @return
     * @param <T>
     */
    public static <T> RequestResponse<T> setData(Object object){
        RequestResponse requestResponse = new RequestResponse();
        requestResponse.data = object;
        return requestResponse;
    }

    /***
     * 设置回响应成功 消息
     * @param message
     * @return
     */
    public RequestResponse success(String message){
        this.resStatus = success;
        this.message = message;
        return this;
    }

    /***
     * 设置回响应 失败 消息
     * @param message
     * @return
     */
    public RequestResponse error(String message){
        this.resStatus = error;
        this.message = message;
        return this;
    }

}
