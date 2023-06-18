package com.lhf.dajiuye.utils;


import com.alibaba.fastjson.JSON;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;

/**
 * jwt使用rsa非对称加解密工具类
 */
public class JwtUtil {
    private static final String SECRET="sdfdsfs"; // 密匙
    public static String generateJwtTokenHMAC(String payLoad){
        Assert.notNull(payLoad,"payLoad不能为空");
        try {
            return  JWT.create()
                        .withIssuer("LHF")
                        .withClaim("userInfo", JSON.toJSONString(payLoad))
                        .sign(Algorithm.HMAC256(SECRET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验token是否合法，合法返回负载信息，否则抛异常
     * @param token
     * @return
     */
    public static String checkToken(String token) {
        return "";
    }
//        JWTVerifier verifier = null;
//        try {
//            verifier = JWT.require(Algorithm.HMAC256(SECRET))
//                    .withIssuer("LHF")
//                    .build();
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//        DecodedJWT jwt = verifier.verify(token);
//        Claim claim = jwt.getClaim("userInfo");
//        String payload = claim.asString();
//        return JSON.parseObject(payload, String.class);
//    }
}
