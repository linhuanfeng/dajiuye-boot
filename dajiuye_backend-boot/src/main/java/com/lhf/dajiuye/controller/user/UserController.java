package com.lhf.dajiuye.controller.user;

import com.github.pagehelper.PageInfo;
//import com.lhf.dajiuye.api.bean.CommonResult2;
//import com.lhf.dajiuye.api.bean.Meta;
//import com.lhf.dajiuye.api.bean.Params2;
//import com.lhf.dajiuye.api.bean.user.User;
//import com.lhf.dajiuye.api.service.user.MyUserService;
//import com.lhf.dajiuye.web.app.constants.RedisCacheName;
import com.lhf.dajiuye.bean.Params2;
import com.lhf.dajiuye.bean.user.User;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.user.MyUserService;
import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/user")
@Slf4j
@CacheConfig(cacheNames = RedisCacheName.PREFIX +"/user/user")
public class UserController {

    @Resource
    MyUserService userService;

    /**
     * 获取openId对应的用户的所有信息
     * @param openId
     * @return
     */
    @GetMapping("/getUser")
    @Cacheable
//    @OpenIdHandle
    public R getUser(@RequestParam("openId") String openId){
        User user = userService.getUserByOpenId(openId);
        return R.ok().setData(user);
    }

    /**
     * 获取所有的投递者信息
     * @param params2
     * @return
     */
    @GetMapping("/getusers")
    @Cacheable
    public R TGetUsers(Params2 params2){
        PageInfo<User> users = userService.getUsers(params2);
        return R.ok().put("list",users);
    }
}
