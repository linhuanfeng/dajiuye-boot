package com.lhf.dajiuye.service.job;


//import com.lhf.dajiuye.api.bean.job.Company;

import com.lhf.dajiuye.bean.job.Company;

import java.util.List;

public interface CompanyService {
    /**
     * 获取公司列表信息
     * @param comId
     * @return
     */
    List<Company> getComData(String comId);
}
