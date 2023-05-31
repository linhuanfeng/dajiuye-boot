package com.lhf.dajiuye.mapper.acl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhf.dajiuye.bean.user.User;
import org.apache.ibatis.annotations.Mapper;
//import com.lhf.dajiuye.api.bean.user.User;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
