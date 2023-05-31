package com.lhf.dajiuye.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysLog implements Serializable {


    private String id;//id

    private String name;

    private String operationUser;//操作人

    private String path;//请求路径

    private Date startTime;//方法执行时间

    private Date endTime;

    private Long totalTime;

    private String claz;

    private String method;

    private Object[] parameter;//方法入参


    private String action;//方法描述

    private Integer sysType;//系统类型

    private Integer opType;//操作类型

    private String RemoteAddr;

    private String error;

}
