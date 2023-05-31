package com.lhf.dajiuye.controller.swipper;

//import com.hu.health.common.utils.PageUtils;
//import com.lhf.dajiuye.api.common.utils.R;
//import com.lhf.dajiuye.api.bean.CommonResult;
//import com.lhf.dajiuye.api.bean.Meta;
//import com.lhf.dajiuye.api.bean.swipper.CommunityBank;
//import com.lhf.dajiuye.api.service.swipper.CommunityBankService;
//import com.lhf.dajiuye.api.util.PageUtils;
//import com.lhf.dajiuye.web.app.constants.RedisCacheName;
import com.lhf.dajiuye.bean.CommonResult;
import com.lhf.dajiuye.bean.Meta;
import com.lhf.dajiuye.bean.swipper.CommunityBank;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.swipper.CommunityBankService;
import com.lhf.dajiuye.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/swipper/community")
@Slf4j
@CacheConfig(cacheNames = RedisCacheName.PREFIX +"/swipper/community")
public class CommunityBankController {

    @Resource
    private CommunityBankService communityBankService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @Cacheable
    // @RequiresPermissions("community:area:list")
    public Object list(@RequestParam Map<String, Object> params){
        PageUtils page = communityBankService.queryPage(params);
        List<?> list = page.getList();
        List<CommunityBank> collect = list.stream().map(o -> (CommunityBank) o).collect(Collectors.toList());
        return new CommonResult<CommunityBank>(collect,new Meta("获取成功",200));
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @Cacheable
    // @RequiresPermissions("community:area:info")
    public R info(@PathVariable("id") Long id){
        CommunityBank communityBank = communityBankService.getById(id);

        return R.ok().put("communityBank", communityBank);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("community:area:save")
    public R save(@RequestBody CommunityBank communityBank){
        communityBankService.save(communityBank);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @CacheEvict(allEntries = true)
    // @RequiresPermissions("community:area:update")
    public R update(@RequestBody CommunityBank communityBank){
        communityBankService.updateById(communityBank);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @CacheEvict(allEntries = true)
    // @RequiresPermissions("community:area:delete")
    public R delete(@RequestBody Long[] ids){
        communityBankService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
