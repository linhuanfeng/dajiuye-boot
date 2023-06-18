package com.lhf.dajiuye.bean.swipper;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Swiper implements Serializable {
    private Integer id;
    private String imageSrc;
    private String title;
    private int goodsId;
    private String navigatorUrl;

}
