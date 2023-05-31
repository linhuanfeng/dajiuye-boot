package com.lhf.dajiuye.service.job.impl;

//import com.lhf.dajiuye.api.bean.job.Company;
//import com.lhf.dajiuye.api.service.job.CompanyService;
//import com.lhf.dajiuye.job.service.mapper.CompanyMapper;
import com.lhf.dajiuye.bean.job.Company;
import com.lhf.dajiuye.mapper.job.CompanyMapper;
import com.lhf.dajiuye.service.job.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyMapper mapper;

    @Override
    public List<Company> getComData(String comId) {
        return mapper.getComDataList(comId);
    }
}
