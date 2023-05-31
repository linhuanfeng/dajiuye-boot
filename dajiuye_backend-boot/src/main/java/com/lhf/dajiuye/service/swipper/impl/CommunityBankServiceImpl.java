package com.lhf.dajiuye.service.swipper.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhf.dajiuye.bean.swipper.CommunityBank;
import com.lhf.dajiuye.common.utils.Query;
import com.lhf.dajiuye.mapper.swipper.CommunityBankMapper;
import com.lhf.dajiuye.service.swipper.CommunityBankService;
import com.lhf.dajiuye.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CommunityBankServiceImpl extends ServiceImpl<CommunityBankMapper, CommunityBank> implements CommunityBankService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommunityBank> page = this.page(
                new Query<CommunityBank>().getPage(params),
                new QueryWrapper<CommunityBank>()
        );

        return new PageUtils(page);
    }
}
