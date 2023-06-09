package com.lhf.dajiuye.controller.Idempotent;

//import com.lhf.dajiuye.api.bean.CommonResult2;
//import com.lhf.dajiuye.api.bean.Meta;
//import com.lhf.dajiuye.api.service.user.ApiIdempotentTokenService;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.service.user.ApiIdempotentTokenService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 接口幂等token服务
 */
@RestController
@RequestMapping("/idempotent/token")
public class TokenController {

    @Resource
    private ApiIdempotentTokenService apiIdempotentTokenService;

    /**
     * 得到接口幂等的token标识
     * @return
     */
    @GetMapping("/create")
    public R createToken(){
        return R.ok().setData(apiIdempotentTokenService.createToken());
    }

    // token校验端点不暴露
//    public void checkToken(){
//        apiIdempotentTokenService.checkToken();
//    }
}
