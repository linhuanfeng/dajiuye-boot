package com.lhf.dajiuye.service.user;

/**
 * API接口幂等校验服务
 */
public interface ApiIdempotentTokenService {
    /**
     * token一定要是字符串，不然会产生精度问题
     * @return
     */
    String createToken();
    void checkToken(String token);
}
