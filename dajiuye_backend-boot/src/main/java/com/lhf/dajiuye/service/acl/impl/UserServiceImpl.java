package com.lhf.dajiuye.service.acl.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.lhf.dajiuye.acl.service.mapper.UserMapper;
//import com.lhf.dajiuye.api.bean.user.User;
//import com.lhf.dajiuye.api.service.acl.UserService;
import com.lhf.dajiuye.bean.user.User;
import com.lhf.dajiuye.mapper.acl.UserMapper;
import com.lhf.dajiuye.service.acl.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("user_name", username));
    }
}
