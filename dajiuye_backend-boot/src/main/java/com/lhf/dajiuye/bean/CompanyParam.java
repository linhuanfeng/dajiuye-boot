package com.lhf.dajiuye.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompanyParam {
    private int pageSize = 10;
    private int pageNum = 1;
    // 查询的内容
    private String query = "";
}
