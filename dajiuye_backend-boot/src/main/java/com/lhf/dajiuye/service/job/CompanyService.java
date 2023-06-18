package com.lhf.dajiuye.service.job;



import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lhf.dajiuye.bean.CompanyParam;
import com.lhf.dajiuye.bean.Params;
import com.lhf.dajiuye.bean.job.Company;
import com.lhf.dajiuye.service.acl.IndexService;

import java.util.List;

public interface CompanyService extends IService<Company> {
    /**
     * 获取公司列表信息
     * @param comId
     * @return
     */
    List<Company> getComData(String comId);

    /**
     * 获取公司列表信息
     * @param params
     * @return
     */
    PageInfo<Company> getComDataManage(CompanyParam params);
}
