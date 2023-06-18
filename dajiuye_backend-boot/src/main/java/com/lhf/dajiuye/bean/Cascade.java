package com.lhf.dajiuye.bean;

import lombok.Data;

import java.util.List;

/**
 * 级联选择器抽象对象
 */
@Data
public class Cascade {
    private Integer id;
    private String name;
    private List<Cascade> children;
}
