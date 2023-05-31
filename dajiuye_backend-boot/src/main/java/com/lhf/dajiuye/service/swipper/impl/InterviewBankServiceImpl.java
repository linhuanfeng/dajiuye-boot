package com.lhf.dajiuye.service.swipper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.lhf.dajiuye.api.bean.swipper.InterviewBank;
//import com.lhf.dajiuye.api.common.utils.Query;
//import com.lhf.dajiuye.api.service.swipper.InterviewBankService;
//import com.lhf.dajiuye.api.util.PageUtils;
//import com.lhf.dajiuye.swipper.service.mapper.InterviewBankMapper;
import com.lhf.dajiuye.bean.swipper.InterviewBank;
import com.lhf.dajiuye.common.utils.Query;
import com.lhf.dajiuye.mapper.swipper.InterviewBankMapper;
import com.lhf.dajiuye.service.swipper.InterviewBankService;
import com.lhf.dajiuye.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class InterviewBankServiceImpl extends ServiceImpl<InterviewBankMapper, InterviewBank> implements InterviewBankService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InterviewBank> page = this.page(
                new Query<InterviewBank>().getPage(params),
                new QueryWrapper<InterviewBank>()
        );

        return new PageUtils(page);
    }
}
