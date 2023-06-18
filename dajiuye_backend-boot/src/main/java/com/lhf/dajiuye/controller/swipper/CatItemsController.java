package com.lhf.dajiuye.controller.swipper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.lhf.dajiuye.annotation.ApiIdempotent;
import com.lhf.dajiuye.annotation.SysLogAnnotation;
import com.lhf.dajiuye.bean.PageParam;
import com.lhf.dajiuye.bean.job.Job;
import com.lhf.dajiuye.bean.swipper.CatItems;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.swipper.CatItemsService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/swipper/catItems")
@CacheConfig(cacheNames = RedisCacheName.PREFIX + "/swipper/catItems")
public class CatItemsController {
    @Resource
    private CatItemsService catItemsService;

    /**
     * 获取分类导航图，比如校招、实习、分类、导航
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/catitems")
//    @PreAuthorize("hasAuthority('catitems:select')")
    @Cacheable
    public R catitems(PageParam param) {
        PageInfo<CatItems> pageInfo = catItemsService.getCatItemsList(param);
        return R.ok().put("list",pageInfo);
    }

    /**
     * 更新职位
     *
     * @param catItems
     * @return
     */
    @PostMapping("/updateCatItems")
    @ApiIdempotent
//    @PreAuthorize("hasAuthority('job:create')")
    @SysLogAnnotation
    public R update(@RequestBody CatItems catItems) {
        boolean id = catItemsService.update(catItems,
                new UpdateWrapper<CatItems>().eq("id", catItems.getId()));
        // true表示更新成功
        return R.ok().setData(id);
    }
}
