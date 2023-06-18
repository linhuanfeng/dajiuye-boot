package com.lhf.dajiuye.controller.swipper;

//import com.lhf.dajiuye.api.bean.CommonResult;
//import com.lhf.dajiuye.api.bean.Meta;
//import com.lhf.dajiuye.api.bean.swipper.Province;
//import com.lhf.dajiuye.api.service.swipper.AreaService;
//import com.lhf.dajiuye.web.app.constants.RedisCacheName;
import com.lhf.dajiuye.bean.Cascade;
import com.lhf.dajiuye.bean.swipper.Province;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.swipper.AreaService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/swipper/area")
@CacheConfig(cacheNames = RedisCacheName.PREFIX +"/swipper/area")
public class AreaController {

    @Resource
    private AreaService areaService;

    /**
     * 获取地点信息
     * @return
     * @throws IOException
     */
    @GetMapping("/placedata")
    @Cacheable
    public R placeList() {
        List<Province> provinceDataList = areaService.getFullPlaceDataList();
        return R.ok().put("list",provinceDataList);
    }

    /**
     * 获取地点信息-管理系统
     * @return
     * @throws IOException
     */
    @GetMapping("/placedataManage")
    @Cacheable
    public R placeListManage() {
        List<Cascade> cascades = areaService.getFullPlaceDataListCascade();
        return R.ok().put("list",cascades);
    }
}
