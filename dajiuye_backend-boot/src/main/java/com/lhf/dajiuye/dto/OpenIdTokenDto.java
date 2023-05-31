package com.lhf.dajiuye.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OpenIdTokenDto implements Serializable {
    private String openId;
    private String token;
}
