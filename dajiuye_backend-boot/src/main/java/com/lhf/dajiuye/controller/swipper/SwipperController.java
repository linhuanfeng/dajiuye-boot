package com.lhf.dajiuye.controller.swipper;

//import com.lhf.dajiuye.api.bean.swipper.CatItems;
//import com.lhf.dajiuye.api.bean.CommonResult;
//import com.lhf.dajiuye.api.bean.Meta;
//import com.lhf.dajiuye.api.bean.swipper.SwiperData;
//import com.lhf.dajiuye.api.service.swipper.SwipperDataService;
//import com.lhf.dajiuye.web.app.constants.RedisCacheName;

import com.lhf.dajiuye.bean.CommonResult;
import com.lhf.dajiuye.bean.Meta;
import com.lhf.dajiuye.bean.swipper.CatItems;
import com.lhf.dajiuye.bean.swipper.SwiperData;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.swipper.SwipperDataService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/swipper/swipper")
@CacheConfig(cacheNames = RedisCacheName.PREFIX + "/swipper/swipper")
public class SwipperController {
    @Resource
    private SwipperDataService swipperDataService;

    /**
     * 获取首页轮播图图片
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/swiperdata")
//    @PreAuthorize("hasAuthority('swipper:select')")
    @Cacheable
    public Object swiperdata() {
        List<SwiperData> swiperDataList = swipperDataService.getswiperDataList();
        return new CommonResult<SwiperData>(swiperDataList, new Meta("获取成功", 200));
    }

    /**
     * 获取分类导航图，比如校招、实习、分类、导航
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/catitems")
//    @PreAuthorize("hasAuthority('catitems:select')")
    @Cacheable
    public Object catitems() {
        List<CatItems> catItemsList = swipperDataService.getcatItemsList();
        return new CommonResult<CatItems>(catItemsList, new Meta("获取成功", 200));
    }
}
