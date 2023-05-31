package com.lhf.dajiuye.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * 查询参数
 */
public class Params implements Serializable {
    private Integer pageSize = 10;
    private Integer pageNum = 1;
    // 查询的内容
    String query = "";
    // 分类id
    String cid = "";
    // 职位类型，1实习，2校招，3社招
    public Integer jobType = -1;
    private String jobAge = "";
    private String jobSalary = "";
}
