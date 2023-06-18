package com.lhf.dajiuye.controller.swipper;


import com.lhf.dajiuye.bean.swipper.InterviewBank;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.swipper.InterviewBankService;
import com.lhf.dajiuye.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/swipper/interview")
@Slf4j
@CacheConfig(cacheNames = RedisCacheName.PREFIX +"/swipper/interview")
public class InterviewBankController {

    @Resource
    private InterviewBankService interviewBankService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @Cacheable
    // @RequiresPermissions("community:area:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = interviewBankService.queryPage(params);
        List<?> list = page.getList();
        List<InterviewBank> collect = list.stream().map(o -> (InterviewBank) o).collect(Collectors.toList());
        return R.ok().put("list",collect);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @Cacheable
    // @RequiresPermissions("community:area:info")
    public R info(@PathVariable("id") Long id){
        InterviewBank interviewBank = interviewBankService.getById(id);

        return R.ok().put("interviewBank", interviewBank);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("community:area:save")
    public R save(@RequestBody InterviewBank interviewBank){
        interviewBankService.save(interviewBank);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @CacheEvict(allEntries = true)
    // @RequiresPermissions("community:area:update")
    public R update(@RequestBody InterviewBank interviewBank){
        interviewBankService.updateById(interviewBank);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @CacheEvict(allEntries = true)
    // @RequiresPermissions("community:area:delete")
    public R delete(@RequestBody Long[] ids){
        interviewBankService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
