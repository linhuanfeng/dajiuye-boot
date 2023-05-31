package com.lhf.dajiuye.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.util.Map;


public class WxUtil {
    /**
     * 过滤微信解密后的用户信息
     * @param realMsg
     * @return
     */
    public static Map strToMap(String realMsg){
        if(!StringUtils.hasLength(realMsg)){
            return null;
        }
        Map jsonMap = JSON.parseObject(realMsg, Map.class);
        if(jsonMap.getOrDefault("watermark","null")!=null){
            // watermark里面有appId,出于安全剔除它
            jsonMap.remove("watermark");
        }
        return jsonMap;
    }
}
