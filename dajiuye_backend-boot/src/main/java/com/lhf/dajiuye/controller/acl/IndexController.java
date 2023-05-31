package com.lhf.dajiuye.controller.acl;

import com.alibaba.fastjson.JSONObject;
//import com.hu.health.base.utils.R;
//import com.lhf.dajiuye.api.service.acl.IndexService;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.service.acl.IndexService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.hu.health.common.com.hu.health.base.utils.R2;

@RestController
@RequestMapping("/acl/index")
//@CrossOrigin
public class IndexController {

    @Resource
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public R info(){
        //获取当前登录用户用户名
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return R.ok().setData(new HashMap<String,Object>() {
        });
//        return R.ok().setData(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public R getMenu(){
        //获取当前登录用户用户名
//        String username = SecurxityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu("username");
        return R.ok().put("permissionList", permissionList);
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }

}
