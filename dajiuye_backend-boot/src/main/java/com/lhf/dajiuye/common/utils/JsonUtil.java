package com.lhf.dajiuye.common.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
    public static String ObjectToJson(Object obj){
        return JSON.toJSONString(obj);
    }
    public static <T> T JsonToObject(String json,Class<T> claz){
        return JSON.parseObject(json,claz);
    }
}
