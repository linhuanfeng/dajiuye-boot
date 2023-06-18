package com.lhf.dajiuye.bean.swipper;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CatItems implements Serializable {
    private Integer id;
    private String name;
    private String imageSrc;
    private String openType;
    private String navigatorUrl;

}
