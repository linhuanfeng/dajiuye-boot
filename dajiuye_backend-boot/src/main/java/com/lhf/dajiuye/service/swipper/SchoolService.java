package com.lhf.dajiuye.service.swipper;

//import com.lhf.dajiuye.api.bean.swipper.School;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhf.dajiuye.bean.job.Job;
import com.lhf.dajiuye.bean.swipper.School;

import java.util.List;

public interface SchoolService extends IService<School> {

    /**
     * 获取校招列表数据
     * @param schId
     * @return
     */
    List<School> getSchoolDataList(int schId);
}
