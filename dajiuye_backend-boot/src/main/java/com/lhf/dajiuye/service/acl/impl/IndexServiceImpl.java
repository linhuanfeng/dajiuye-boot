package com.lhf.dajiuye.service.acl.impl;

import com.alibaba.fastjson.JSONObject;
//import com.lhf.dajiuye.api.bean.acl.Role;
//import com.lhf.dajiuye.api.bean.user.User;
//import com.lhf.dajiuye.api.service.acl.IndexService;
//import com.lhf.dajiuye.api.service.acl.PermissionService;
//import com.lhf.dajiuye.api.service.acl.RoleService;
//import com.lhf.dajiuye.api.service.acl.UserService;
import com.lhf.dajiuye.bean.acl.Role;
import com.lhf.dajiuye.bean.user.User;
import com.lhf.dajiuye.service.acl.IndexService;
import com.lhf.dajiuye.service.acl.PermissionService;
import com.lhf.dajiuye.service.acl.RoleService;
import com.lhf.dajiuye.service.acl.UserService;
//import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.selectByUsername(username);
        if (null == user) {
            //throw new GuliException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }

        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId().toString());
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if (roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId().toString());
        redisTemplate.opsForValue().set(username, permissionValueList);

        result.put("name", user.getNickName());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     *
     * @param username
     * @return
     */
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);

        //根据用户id获取用户菜单权限
        List<JSONObject> permissionList = permissionService.selectPermissionByUserId(user.getId().toString());
        return permissionList;
    }


}
