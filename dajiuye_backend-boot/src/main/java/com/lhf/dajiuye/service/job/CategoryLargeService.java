package com.lhf.dajiuye.service.job;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhf.dajiuye.bean.Cascade;
import com.lhf.dajiuye.bean.job.CategoryLarge;

import java.util.List;

public interface CategoryLargeService extends IService<CategoryLarge> {
    /**
     * 获取大中小信息列表
     * @return
     */
    List<CategoryLarge> getFullCategoryDataList();
    /**
     * 获取大中小信息列表-封装级联选择器对象
     * @return
     */
    List<Cascade> getFullCategoryDataListCascade();
}
