package com.lhf.dajiuye.controller.acl;

//import com.hu.health.base.utils.R;
//import com.lhf.dajiuye.api.bean.acl.Permission;
//import com.lhf.dajiuye.api.service.acl.PermissionService;
import com.lhf.dajiuye.bean.acl.Permission;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.service.acl.PermissionService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/acl/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    //获取全部菜单
    @GetMapping
    public R indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenu();
        return R.ok().put("children",list);
    }

    @PostMapping("/doAssign")
    public R doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return R.ok();
    }

    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().put("children", list);
    }

    @GetMapping("permissions")
    public R getPermissionsByOpenId(@RequestParam("openId") String openId) {
        List<String> list = permissionService.selectPermissionValueByUserId(openId);
        return R.ok().put("children", list);
    }

}

