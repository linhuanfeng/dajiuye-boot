package com.lhf.dajiuye.bean.job;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultTo {
    /**
     * 查询到的所有商品
     */
    private List<Job> questionEntityList;

    /**
     * 总记录数
     */
    private Long totalCount;
}
