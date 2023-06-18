package com.lhf.dajiuye.controller.swipper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.lhf.dajiuye.annotation.ApiIdempotent;
import com.lhf.dajiuye.annotation.SysLogAnnotation;
import com.lhf.dajiuye.bean.PageParam;
import com.lhf.dajiuye.bean.swipper.CatItems;
import com.lhf.dajiuye.bean.swipper.Swiper;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.swipper.SwipperDataService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/swipper/swipper")
@CacheConfig(cacheNames = RedisCacheName.PREFIX + "/swipper/swipper")
public class SwiperDataController {
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
    public R swiperdata(PageParam param) {
        PageInfo<Swiper> pageInfo = swipperDataService.getSwiperDataList(param);
        return R.ok().put("list",pageInfo);
    }

    /**
     * 更新职位
     *
     * @param swiper
     * @return
     */
    @PostMapping("/updateSwiper")
    @ApiIdempotent
//    @PreAuthorize("hasAuthority('job:create')")
    @SysLogAnnotation
    public R update(@RequestBody Swiper swiper) {

        boolean id = swipperDataService.update(swiper,
                new UpdateWrapper<Swiper>().eq("id", swiper.getId()));
        // true表示更新成功
        return R.ok().setData(id);
    }
}
