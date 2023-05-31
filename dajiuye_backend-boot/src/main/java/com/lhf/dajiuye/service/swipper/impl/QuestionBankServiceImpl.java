package com.lhf.dajiuye.service.swipper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.lhf.dajiuye.api.bean.swipper.QuestionBank;
//import com.lhf.dajiuye.api.common.utils.Query;
//import com.lhf.dajiuye.api.service.swipper.QuestionBankService;
//import com.lhf.dajiuye.api.util.PageUtils;
//import com.lhf.dajiuye.swipper.service.mapper.QuestionBankMapper;
import com.lhf.dajiuye.bean.swipper.QuestionBank;
import com.lhf.dajiuye.common.utils.Query;
import com.lhf.dajiuye.mapper.swipper.QuestionBankMapper;
import com.lhf.dajiuye.service.swipper.QuestionBankService;
import com.lhf.dajiuye.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<QuestionBank> page = this.page(
                new Query<QuestionBank>().getPage(params),
                new QueryWrapper<QuestionBank>()
        );

        return new PageUtils(page);
    }
}
