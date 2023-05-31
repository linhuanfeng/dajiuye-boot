package com.lhf.dajiuye.service.swipper.impl;

//import com.lhf.dajiuye.api.bean.swipper.School;
//import com.lhf.dajiuye.api.service.swipper.SchoolService;
//import com.lhf.dajiuye.swipper.service.mapper.SchoolDataMapper;
//import org.apache.dubbo.config.annotation.DubboService;
import com.lhf.dajiuye.bean.swipper.School;
import com.lhf.dajiuye.mapper.swipper.SchoolDataMapper;
import com.lhf.dajiuye.service.swipper.SchoolService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService,InitializingBean {

    @Autowired
    private SchoolDataMapper schoolDataMapper;

    /**
     * 获取校招列表数据
     * @param schoolId
     * @return
     */
    @Override
    public List<School> getSchoolDataList(int schoolId) {
        List<School> schoolDataList = schoolDataMapper.getSchoolDataList(schoolId);
        return schoolDataList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
