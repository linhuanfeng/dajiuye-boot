package com.lhf.dajiuye.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
//import com.lhf.dajiuye.api.bean.Params2;
//import com.lhf.dajiuye.api.bean.user.User;
//import com.lhf.dajiuye.api.service.user.MyUserService;
//import com.lhf.dajiuye.user.service.mapper.UserDataMapper;
//import org.apache.dubbo.config.annotation.DubboService;
import com.lhf.dajiuye.bean.Params2;
import com.lhf.dajiuye.bean.job.Job;
import com.lhf.dajiuye.bean.user.User;
import com.lhf.dajiuye.mapper.job.JobDataMapper;
import com.lhf.dajiuye.mapper.user.UserDataMapper;
import com.lhf.dajiuye.service.user.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyUserServiceImpl extends ServiceImpl<UserDataMapper, User> implements MyUserService {

    @Autowired
    UserDataMapper mapper;

    @Override
    public User getUserByOpenId(String openId) {
        User user = mapper.getUserByOpenId(openId);
        return user;
    }

    @Override
    public User getUserById(String Id) {
        User user = mapper.getUserById(Id);
        return user;
    }

    @Override
    public PageInfo<User> getUsers(Params2 params2) {
        PageHelper.startPage(params2.getPageNo(), params2.getPageSize());
        List<User> users = mapper.getUsers(params2.getId(), params2.getState());
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public int saveUser(User user) {
        int res = mapper.insertUser(user);
        return res;
    }

    @Override
    public int updateUser(User user) {
        int res = mapper.updateUser(user);
        return res;
    }

    @Override
    public int updateUserResumeByOpenId(String resumeUrl, String openId) {
        int res = mapper.updateUserResume(resumeUrl, openId);
        return res;
    }
}
