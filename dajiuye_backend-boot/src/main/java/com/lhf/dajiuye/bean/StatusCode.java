package com.lhf.dajiuye.bean;

import java.io.Serializable;

/**
 * 定义一个关于执行状态结果的枚举类
 */
public enum StatusCode implements Serializable {
    SUCCESS("success",200),ERROR("error",500); // 枚举成员用‘,’分开
    private String msg;
    private Integer code;

    StatusCode(String msg,Integer code){
        this.msg = msg;
        this.code = code;
    }
    StatusCode(Integer code){
        this.code = code;
    }
    StatusCode(String msg){
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}
