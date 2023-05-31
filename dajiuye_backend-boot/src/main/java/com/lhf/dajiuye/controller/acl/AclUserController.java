package com.lhf.dajiuye.controller.acl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.hu.health.base.utils.R;
//import com.lhf.dajiuye.api.bean.user.User;
//import com.lhf.dajiuye.api.service.acl.RoleService;
//import com.lhf.dajiuye.api.service.acl.UserService;
import com.lhf.dajiuye.bean.user.User;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.service.acl.RoleService;
import com.lhf.dajiuye.service.acl.UserService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/acl/user")
//@CrossOrigin
public class AclUserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @GetMapping("{page}/{limit}")
    public R index(
            @PathVariable Long page,

            @PathVariable Long limit,

                    User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
//            wrapper.like("username",userQueryVo.getUsername());
//        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        return R.ok().put("items", pageModel.getRecords()).put("total", pageModel.getTotal());
    }

    @PostMapping("save")
    public R save(@RequestBody User user) {
//        user.setPassword(MD5.encrypt(user.getPassword()));
        try {
            // 数据库唯一性约束，存在会报错
            userService.save(user);
            // 默认添加管理员权限
            roleService.saveUserRoleRealtionShip(user.getId().toString(),new String[]{"1"});
        }catch (Exception e){
            //
        }


        return R.ok();
    }

    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return R.ok(roleMap);
    }

    @PostMapping("/doAssign")
    public R doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return R.ok();
    }
}

