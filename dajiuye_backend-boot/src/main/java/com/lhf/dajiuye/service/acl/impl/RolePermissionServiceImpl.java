package com.lhf.dajiuye.service.acl.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.lhf.dajiuye.acl.service.mapper.RolePermissionMapper;
//import com.lhf.dajiuye.api.bean.acl.RolePermission;
//import com.lhf.dajiuye.api.service.acl.RolePermissionService;
import com.lhf.dajiuye.bean.acl.RolePermission;
import com.lhf.dajiuye.mapper.acl.RolePermissionMapper;
import com.lhf.dajiuye.service.acl.RolePermissionService;
//import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class  RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}
