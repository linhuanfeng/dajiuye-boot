package com.lhf.dajiuye.bean.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信登录后返回的用户加密信息
 */
@Data
@NoArgsConstructor
public class LoginInfo implements Serializable {
    String code;
    String encryptedData;
    String iv;
}
