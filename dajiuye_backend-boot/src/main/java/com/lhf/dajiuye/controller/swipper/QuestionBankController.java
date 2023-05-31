package com.lhf.dajiuye.controller.swipper;

//import com.hu.health.common.utils.PageUtils;
//import com.lhf.dajiuye.api.common.utils.R;
//import com.lhf.dajiuye.api.bean.CommonResult;
//import com.lhf.dajiuye.api.bean.Meta;
//import com.lhf.dajiuye.api.bean.swipper.QuestionBank;
//import com.lhf.dajiuye.api.service.swipper.QuestionBankService;
//import com.lhf.dajiuye.api.util.PageUtils;
//import com.lhf.dajiuye.web.app.constants.RedisCacheName;
import com.lhf.dajiuye.bean.CommonResult;
import com.lhf.dajiuye.bean.Meta;
import com.lhf.dajiuye.bean.swipper.QuestionBank;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.swipper.QuestionBankService;
import com.lhf.dajiuye.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/swipper/question")
@Slf4j
@CacheConfig(cacheNames = RedisCacheName.PREFIX +"/swipper/question")
public class QuestionBankController {

    @Resource
    private QuestionBankService questionBankService;

    /**
     * 列表
     */
    @GetMapping("/list")
    // @RequiresPermissions("community:area:list")
    @Cacheable
    public Object list(@RequestParam Map<String, Object> params){
        PageUtils page = questionBankService.queryPage(params);
        List<?> list = page.getList();
        List<QuestionBank> collect = list.stream().map(o -> (QuestionBank) o).collect(Collectors.toList());
        return new CommonResult<QuestionBank>(collect,new Meta("获取成功",200));
    }



    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    // @RequiresPermissions("community:area:info")
    @Cacheable
    public R info(@PathVariable("id") Long id){
        QuestionBank questionBank = questionBankService.getById(id);

        return R.ok().put("questionBank", questionBank);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    // @RequiresPermissions("community:area:save")
    public R save(@RequestBody QuestionBank questionBank){
        questionBankService.save(questionBank);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    // @RequiresPermissions("community:area:update")
    public R update(@RequestBody QuestionBank questionBank){
        questionBankService.updateById(questionBank);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    // @RequiresPermissions("community:area:delete")
    public R delete(@RequestBody Long[] ids){
        questionBankService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
