package com.lhf.dajiuye.controller.acl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.hu.health.base.utils.R;
//import com.lhf.dajiuye.api.bean.acl.Role;
//import com.lhf.dajiuye.api.service.acl.RoleService;
import com.lhf.dajiuye.bean.acl.Role;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.service.acl.RoleService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//import org.springframework.data.domain.Page;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/acl/role")
//@CrossOrigin
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("{page}/{limit}")
    public R index(
            @PathVariable Long page,

            @PathVariable Long limit,
            Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        return R.ok().put("items", pageParam.getRecords()).put("total", pageParam.getTotal());
    }

    @PostMapping("save")
    public R save(@RequestBody Role role) {
        roleService.save(role);
        return R.ok();
    }
}

