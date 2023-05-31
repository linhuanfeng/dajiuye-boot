package com.lhf.dajiuye.controller.swipper;

//import com.lhf.dajiuye.api.bean.CommonResult;
//import com.lhf.dajiuye.api.bean.Meta;
//import com.lhf.dajiuye.api.bean.swipper.School;
//import com.lhf.dajiuye.api.service.swipper.SchoolService;
//import com.lhf.dajiuye.web.app.constants.RedisCacheName;
import com.lhf.dajiuye.bean.CommonResult;
import com.lhf.dajiuye.bean.Meta;
import com.lhf.dajiuye.bean.swipper.School;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.swipper.SchoolService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/swipper/school/")
@CacheConfig(cacheNames = RedisCacheName.PREFIX +"/swipper/school/")
public class SchoolController {

    @Resource
    private SchoolService schoolService;

    /**
     * 获取校招图片
     * @param schId
     * @return
     * @throws IOException
     */
    @GetMapping("/schooldata")
    @Cacheable
    public Object schoolList(@RequestParam(value = "schoolId",required = false,defaultValue = "0") String schId) throws IOException {
        List<School> schoolDataList = schoolService.getSchoolDataList(Integer.parseInt(schId));
        return new CommonResult<School>(schoolDataList,new Meta("获取成功",200));
    }
}
