package com.lhf.dajiuye.bean.swipper;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class District implements Serializable {
    Integer id;
    String districtName;
    String districtAdcode;
    String cityAdcode;
    String cityId;
    String provinceAdcode;
    String provinceId;
}
