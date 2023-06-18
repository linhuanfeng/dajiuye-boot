package com.lhf.dajiuye.controller.job;

import com.lhf.dajiuye.bean.Cascade;
import com.lhf.dajiuye.bean.job.CategoryLarge;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.constants.RedisCacheName;
//import org.apache.dubbo.config.annotation.DubboReference;
import com.lhf.dajiuye.service.job.CategoryLargeService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/job/category")
@CacheConfig(cacheNames = RedisCacheName.PREFIX + "/job/category")
public class CategoryController {

    @Resource
    private CategoryLargeService categoryLargeService;

    /**
     * 获取职位分类信息
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/jobcatdata")
//    @PreAuthorize("hasAuthority('user.list')")
    @Cacheable
    public R jobcatList() {
        List<CategoryLarge> categoryLargeDataList = categoryLargeService.getFullCategoryDataList();
        return R.ok().put("list",categoryLargeDataList);
    }

    /**
     * 获取职位分类信息-管理系统
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/jobcatdataManage")
//    @PreAuthorize("hasAuthority('user.list')")
    @Cacheable
    public R jobcatListManage() {
        List<Cascade> cascades = categoryLargeService.getFullCategoryDataListCascade();
        return R.ok().put("list", cascades);
    }
}
