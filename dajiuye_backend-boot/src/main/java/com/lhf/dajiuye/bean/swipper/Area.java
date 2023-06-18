package com.lhf.dajiuye.bean.swipper;

import lombok.Data;

import java.util.List;

/**
 * 地区
 */
@Data
public class Area {
    private Integer id;
    private String name;
    private List<Area> children;
}
