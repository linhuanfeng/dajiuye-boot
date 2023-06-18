package com.lhf.dajiuye.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * 通用分页查询参数
 */
public class PageParam {
    private int pageSize = 10;
    private int pageNum = 1;
}
