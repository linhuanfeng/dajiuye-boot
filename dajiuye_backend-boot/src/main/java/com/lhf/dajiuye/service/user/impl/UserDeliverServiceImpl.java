package com.lhf.dajiuye.service.user.impl;

//import com.lhf.dajiuye.api.bean.user.Userdeliver;
//import com.lhf.dajiuye.api.service.user.UserDeliverService;
//import com.lhf.dajiuye.user.service.mapper.UserDeliverMapper;

import com.lhf.dajiuye.bean.user.Userdeliver;
import com.lhf.dajiuye.mapper.user.UserDeliverMapper;
import com.lhf.dajiuye.service.user.UserDeliverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDeliverServiceImpl implements UserDeliverService {

    @Resource
    private UserDeliverMapper mapper;

    @Override
    public void saveDeliver(Userdeliver userdeliver) {
        mapper.saveDeliver(userdeliver);
    }

    @Override
    public void updateDeliver(Userdeliver userdeliver) {
        mapper.updateDeliver(userdeliver);
    }
}
