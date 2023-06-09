package com.lhf.dajiuye.controller.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
//import com.lhf.dajiuye.api.bean.*;
//import com.lhf.dajiuye.api.bean.job.Job;
//import com.lhf.dajiuye.api.constant.JobMqConstants;
//import com.lhf.dajiuye.api.service.job.JobDataService;
//import com.lhf.dajiuye.api.service.message.KafkaService;
//import com.lhf.dajiuye.api.service.user.ApiIdempotent;
//import com.lhf.dajiuye.web.app.constants.RedisCacheName;
//import com.lhf.dajiuye.web.app.log.SysLogAnnotation;
import com.lhf.dajiuye.annotation.ApiIdempotent;
import com.lhf.dajiuye.annotation.SysLogAnnotation;
import com.lhf.dajiuye.bean.Params;
import com.lhf.dajiuye.bean.job.Job;
import com.lhf.dajiuye.common.utils.R;
import com.lhf.dajiuye.constant.JobMqConstants;
import com.lhf.dajiuye.constants.RedisCacheName;
import com.lhf.dajiuye.service.job.JobDataService;
import com.lhf.dajiuye.service.message.KafkaService;
import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/job/job")
@Slf4j
@CacheConfig(cacheNames = RedisCacheName.PREFIX + "job:job:")
public class JobController {

    @Resource
    private JobDataService jobDataService;

    @Resource
    private KafkaService kafkaService;

    @GetMapping("/delete")
    public R delete(String jobId){
        boolean id = jobDataService.remove(new QueryWrapper<Job>().eq("job_id",jobId));
        return R.ok().setData(id);
    }

    /**
     * 新增职位
     *
     * @param job
     * @return
     */
    @PostMapping("/saveJobBoot")
    @ApiIdempotent
//    @PreAuthorize("hasAuthority('job:create')")
    @SysLogAnnotation
    public R saveBoot(@RequestBody Job job) {
        job.setJobId(UUID.randomUUID().toString().replace("-", ""));
        job.setJobReleaseTime(new Date());
        boolean save = jobDataService.save(job);
        return R.ok().setData(save);
    }

    /**
     * 更新职位
     *
     * @param job
     * @return
     */
    @PostMapping("/updateJob")
    @ApiIdempotent
//    @PreAuthorize("hasAuthority('job:create')")
    @SysLogAnnotation
    public R update(@RequestBody Job job) {
        boolean jobId = jobDataService.update(job,
                new UpdateWrapper<Job>().eq("job_id", job.getJobId()));
        // true表示更新成功
        return R.ok().setData(job);
    }


    /**
     * 获取职位信息列表(mysql)
     *
     * @param params 分页数据
     * @return
     * @throws IOException 参数有jobtype=    空串
     *                     参数没有jobtype   null
     */
    @GetMapping("/jobs")
//    @PreAuthorize("hasAuthority('job:list')")
    @Cacheable
    public R getJobs(Params params) {
        params.adjust();
        PageInfo<Job> jobDataList = jobDataService.getJobs(params);
        return R.ok().put("list",jobDataList);
    }

    /**
     * 获取职位信息列表(mysql)
     *
     * @param params 分页数据
     * @return
     * @throws IOException 参数有jobtype=    空串
     *                     参数没有jobtype   null
     */
    @GetMapping("/jobsManage")
//    @PreAuthorize("hasAuthority('job:list')")
    @Cacheable
    public R getJobsManage(Params params) {
        params.adjust();
        PageInfo<Job> jobDataList = jobDataService.getJobsManage(params);
        return R.ok().put("list", jobDataList);
//        return new CommonResult2<PageInfo>(jobDataList,new Meta("获取成功",200));
    }

    /**
     * 获取职位信息列表(es)
     *
     * @param params
     * @return
     */
    @GetMapping("/jobsByEs")
//    @PreAuthorize("hasAuthority('job:list')")
    @SysLogAnnotation
    @Cacheable
    public R getJobsByEs(Params params) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PageInfo<Job> jobDataList = jobDataService.queryByEs(params);
        PageInfo<Job> jobDataList = jobDataService.getJobs(params);
        return R.ok().put("list",jobDataList);
    }

    /**
     * 增加职位热度
     * 发送到kafka消息
     *
     * @return
     * @throws IOException
     */
    @PostMapping("/jobView")
    @SysLogAnnotation
    public R jobView(@RequestBody String jobId) {
//        kafkaService.sendMessage(jobId);
        mqSend(jobId, JobMqConstants.JOB_VIEW_KEY);
        return R.ok().setData("增加权值成功");
    }

    /**
     * 投递简历，异步执行
     * <p>
     * 要发消息给hr通知收到简历
     * 用户投递次数增加
     * 职位标记的投递计数也要标记
     *
     * @param jobId
     * @return
     */
    @PostMapping("/jobDelivery")
    @SysLogAnnotation
    public R jobDelivery(@RequestBody String jobId) {
//        kafkaService.sendMessage(jobId);
        mqSend(jobId, JobMqConstants.JOB_DELIVERY_KEY);
        return R.ok().setData("增加权值成功");
    }

    @PostMapping("/jobCommunicate")
    @SysLogAnnotation
    public R jobCommunicate(@RequestBody String jobId) {
//        kafkaService.sendMessage(jobId);
        mqSend(jobId, JobMqConstants.JOB_COMMUNICATE_KEY);
        return R.ok().setData("增加权值成功");
    }

    private void mqSend(String jobId, String routingKey) {
        // 3.发送消息
//        rabbitTemplate.convertAndSend(JobMqConstants.JOB_EXCHANGE, routingKey, jobId);
    }

    /**
     * 根据id获取职位
     *
     * @param jobId
     * @return
     */
    @GetMapping("job")
    @SysLogAnnotation
    @Cacheable
    public R job(@RequestParam(value = "jobId") String jobId) {
        Job job = jobDataService.getJobById(jobId);
        return R.ok().setData(job);
    }

    /**
     * 根据id获取职位
     *
     * @param jobId
     * @return
     */
    @GetMapping("jobBoot")
    @SysLogAnnotation
    @Cacheable
    public R jobBoot(@RequestParam(value = "jobId") String jobId) {
        Job job =jobDataService.getOne(new QueryWrapper<Job>().eq("job_id",jobId));
        return R.ok().put("data",job);
    }

    /**
     * 根据id获取职位
     *
     * @param comId
     * @return
     */
    @GetMapping("/jobsByComId")
    @SysLogAnnotation
    @Cacheable
    public R listByCom(@RequestParam(value = "comId") String comId) {
        PageInfo<Job> pageInfo = jobDataService.getJobsByComId(new Params(), comId);
        return R.ok().put("list",pageInfo);
    }


    /**
     * 根据职位分类cid获取职位列表
     *
     * @param params
     * @return
     * @throws IOException ModelAttribute 得用 x-www-form-urlencoded格式 不能用json
     */
    @PostMapping("/jobsByCid")
//    @PreAuthorize("hasAuthority('job:list')")
    @SysLogAnnotation
    @Cacheable
    public R jobsByCid(@RequestBody Params params) {
        PageInfo<Job> jobDataList = jobDataService.getJobsByCid(params);
        return R.ok().put("list",jobDataList);
    }

    /**
     * 获取投递职位的反馈
     *
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param state
     * @return
     * @throws IOException
     */
    @GetMapping("/jobsFeedback")
    @SysLogAnnotation
    @Cacheable
    public R jobsFeedback(@RequestParam("pagenum") String pageNum,
                               @RequestParam("pagesize") String pageSize,
                               @RequestParam("userid") String userId,
                               @RequestParam("state") String state) throws IOException {
        PageInfo<Job> jobsFeedback = jobDataService.getJobsFeedback(Integer.parseInt(pageNum), Integer.parseInt(pageSize), userId, Integer.parseInt(state));
        return R.ok().put("list",jobsFeedback);
    }

    /**
     * 职位定制
     *
     * @param job
     * @return
     */
    @PostMapping("/jobsCustom")
    @SysLogAnnotation
    public R jobCustom(@RequestBody Job job) {
        List<Job> jobs = jobDataService.getJobsCustom(job);
        return R.ok().put("list",jobs);
    }

    /**
     * 职位模糊匹配（mysql）
     *
     * @param params
     * @return
     * @throws IOException
     */
    @GetMapping("/jobsSearch")
    @SysLogAnnotation
    public R qSearch(Params params) {
        PageInfo<Job> jobDataList = jobDataService.query(params);
        return R.ok().put("list",jobDataList);
    }
}
