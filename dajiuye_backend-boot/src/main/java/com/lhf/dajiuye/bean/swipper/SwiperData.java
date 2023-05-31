package com.lhf.dajiuye.bean.swipper;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SwiperData implements Serializable {
    private String id;
    private String imageSrc;
    private String title;
    private int goodsId;
    private String navigatorUrl;

}
