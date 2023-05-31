package com.lhf.dajiuye.common.to;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SecOrderTo implements Serializable {

    // 订单号
    private String orderSn;

    private Long userId;

    private Long skuId;
}
