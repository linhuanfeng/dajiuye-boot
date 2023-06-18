package com.lhf.dajiuye.service.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhf.dajiuye.bean.CompanyParam;
import com.lhf.dajiuye.bean.Params;
import com.lhf.dajiuye.bean.job.Company;
import com.lhf.dajiuye.bean.job.Job;
import com.lhf.dajiuye.mapper.job.CompanyMapper;
import com.lhf.dajiuye.service.job.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper,Company> implements CompanyService {

    @Resource
    private CompanyMapper mapper;

    @Override
    public List<Company> getComData(String comId) {
        return mapper.getComDataList(comId);
    }

    @Override
    public PageInfo<Company> getComDataManage(CompanyParam params) {
        Integer pageNum = params.getPageNum();
        Integer pageSize = params.getPageSize();
        // threadLocal<Page>中设置分页对象Page
        PageHelper.startPage(pageNum, pageSize);

        // 本质是一个Page对象
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        if(StringUtils.hasLength(params.getQuery())){
            wrapper.like("com_full_name",params.getQuery()).or().like("com_min_name",params.getQuery());
        }
        List<Company> list = list(wrapper);

        int pages=list.size();

        if(list instanceof Page){
            Page page = (Page) list;
            pages=page.getPages(); // 数据库真正的总页数
        }

        PageInfo<Company> pageInfo = new PageInfo<>(list);
        pageInfo.setPages(pages); // 设置真正的总页数

        return pageInfo;
    }
}
