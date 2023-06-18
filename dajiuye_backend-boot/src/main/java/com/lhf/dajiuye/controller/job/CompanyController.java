package com.lhf.dajiuye.controller.job;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.lhf.dajiuye.annotation.ApiIdempotent;
import com.lhf.dajiuye.annotation.SysLogAnnotation;
import com.lhf.dajiuye.bean.CompanyParam;
import com.lhf.dajiuye.bean.job.Company;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.job.CompanyService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/job/company")
@CacheConfig(cacheNames = RedisCacheName.PREFIX + "/job/company")
public class CompanyController {

    @Resource
    private CompanyService companyService;

    /**
     * 获取公司信息列表
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/comdata")
    @Cacheable
    public R comdata(@RequestParam(value = "comId", required = false, defaultValue = "") String comId) throws IOException {
        List<Company> comDataList = companyService.getComData(comId);
        return R.ok().put("list",comDataList);
    }

    /**
     * 获取公司信息列表
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/list")
    @Cacheable
    public R list(CompanyParam param) throws IOException {
        PageInfo<Company> pageInfo = companyService.getComDataManage(param);
        return R.ok().put("list", pageInfo);
    }

    /**
     * 更新职位
     *
     * @param company
     * @return
     */
    @PostMapping("/updateCompany")
    @ApiIdempotent
//    @PreAuthorize("hasAuthority('job:create')")
    @SysLogAnnotation
    public R update(@RequestBody Company company) {
        boolean comId = companyService.update(company,
                new UpdateWrapper<Company>().eq("com_id", company.getComId()));
        // true表示更新成功
        return R.ok().setData(comId);
    }
}
