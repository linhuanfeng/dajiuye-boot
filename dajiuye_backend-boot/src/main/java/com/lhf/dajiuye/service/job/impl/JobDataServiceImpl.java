package com.lhf.dajiuye.service.job.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
//import com.lhf.dajiuye.api.bean.Params;
//import com.lhf.dajiuye.api.bean.job.Job;
//import com.lhf.dajiuye.api.service.job.JobDataService;
//import com.lhf.dajiuye.api.service.job.SearchService;
//import com.lhf.dajiuye.job.service.mapper.JobDataMapper;
import com.lhf.dajiuye.bean.Params;
import com.lhf.dajiuye.bean.job.Job;
import com.lhf.dajiuye.mapper.job.JobDataMapper;
import com.lhf.dajiuye.service.job.JobDataService;
import com.lhf.dajiuye.service.job.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

@Service
public class JobDataServiceImpl extends ServiceImpl<JobDataMapper, Job> implements JobDataService {

//    @Autowired
//    CheckTokenFeignService checkTokenFeignService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private JobDataMapper jobDataMapper;

    @Autowired
    private RedisServiceImpl redisService;

//    @Override
//    public CheckTokenFeignService getfeign() {
//        return checkTokenFeignService;
//    }

//    @Override
//    public void addJobScore(String message) {
//        addJobScore(message);
//    }

    @Override
    public void jobDelivery(String message) {
        addJobScore(message);
    }

    public void saveJob(Job job) {
        job.setJobId(UUID.randomUUID().toString().replace("-", ""));
        job.setJobReleaseTime(new Date());
        jobDataMapper.saveJob(job);
    }

    /**
     * 首页职位列表
     * 获取职位信息列表数据(有Id则查找单个)
     * MySQL数据+redis数据
     *
     * @return
     */
    @Override
    public PageInfo<Job> getJobs( Params params) {

        Integer pageNum = params.getPageNum();
        Integer pageSize = params.getPageSize();
        // threadLocal<Page>中设置分页对象Page
        PageHelper.startPage(pageNum, pageSize);

        // 本质是一个Page对象
        List<Job> jobDataList = jobDataMapper.getJobs(params);

        int pages=jobDataList.size();

        if(jobDataList instanceof Page){
            Page page = (Page) jobDataList;
            pages=page.getPages(); // 数据库真正的总页数
        }

        // 获取热点职位
        List<Job> hotJobs = redisService.getHotJobs(params);

        // 去重,并把jobDataList尾加到hotJobs中
        jobDataList = handleResult(hotJobs, jobDataList);

        PageInfo<Job> pageInfo = new PageInfo<>(jobDataList);
        pageInfo.setPages(pages); // 设置真正的总页数

        return pageInfo;
    }

    /**
     * 去重,并把jobDataList尾加到hotJobs中
     * @param hotJobs
     * @param jobDataList Page分页对象
     * @return hotJobs+jobDataList
     */
    private List<Job> handleResult(List<Job> hotJobs, List<Job> jobDataList) {
        if (CollectionUtils.isEmpty(hotJobs)) {
            // hotJobs表示缓存没有，返回数据库的就行jobDataList
            return jobDataList;
        }
        if(hotJobs instanceof ArrayList){
            // 确保只最多扩容一次
            ((ArrayList<Job>) hotJobs).ensureCapacity(hotJobs.size()+jobDataList.size());
        }
        Map<String, String> map = new HashMap<>();
        // 将hotJobs放到map
        hotJobs.forEach(job -> map.put(job.getJobId(), "1"));
        // 将常规的jobDataList的加入到hostJobs中
        jobDataList.forEach(job -> {
            if (map.get(job.getJobId()) == null) {
                // 在不重复的情况下，将jobDataList元素尾加
                hotJobs.add(0,job);
            }
        });
        return hotJobs;
    }


    /**
     * 根据职位分类cid返回职位列表
     * MySQL
     *
     * @return
     */
    @Override
    public PageInfo<Job> getJobsByCid(Params params) {

        // 这段代码表示，程序开始分页了，page默认值是1，pageSize默认是10，意思是从第1页开始，每页显示10条记录
        PageHelper.startPage(params.getPageNum(), params.getPageSize());

        List<Job> jobDataList = jobDataMapper.getJobsByCid(params);
        PageInfo<Job> pageInfo = new PageInfo<>(jobDataList);

        return pageInfo;
    }

    /**
     * 获取职位列表（comId）
     *
     * @return
     */
    @Override
    public PageInfo<Job> getJobsByComId(Params params,String comId) {
        if(params==null)
            params=new Params();
        // 这段代码表示，程序开始分页了，page默认值是1，pageSize默认是10，意思是从第1页开始，每页显示10条记录
        PageHelper.startPage(params.getPageNum(), params.getPageSize());

        List<Job> jobDataList = jobDataMapper.getJobsByComId(comId);
        PageInfo<Job> pageInfo = new PageInfo<>(jobDataList);

        return pageInfo;
    }

    /**
     * 获取职位列表（jobId） 使用redis缓存
     *
     * @return
     */
    @Override
    public Job getJobById(String jobId) {
        Job job = redisService.getJobRedisById(jobId);
        if (job == null) {
            // MySQL
            job = jobDataMapper.getJobById(jobId);
            // 数据存到缓存中
            redisService.saveJob(job);
        }
        return job;
    }

    /**
     * 职位点击，增加权值
     * 每次点赞增加1小时的毫秒数权值
     */
    @Override
    public void addJobScore(String jobId){
        redisService.incrementJobScore(jobId);
    }

    /**
     * 查询投递职位反馈
     *
     * @param userId
     * @param state
     * @return
     */
    public PageInfo<Job> getJobsFeedback(int pageNum, int pageSize, String userId, int state) {
        PageHelper.startPage(pageNum, pageSize);
        List<Job> jobsFeedback = jobDataMapper.getJobsFeedback(userId, state);
        PageInfo<Job> pageInfo = new PageInfo<>(jobsFeedback);
        return pageInfo;
    }

    /**
     * 职位定制
     *
     * @return
     */
    public List<Job> getJobsCustom(Job job) {
        List<Job> jobs = jobDataMapper.jobCustom(job);
        return jobs;
    }

    /**
     * 职位模糊匹配
     *
     * @param params
     * @param params
     * @return
     */
    @Override
    public PageInfo<Job> query(Params params) {
        // 这段代码表示，程序开始分页了，page默认值是1，pageSize默认是10，意思是从第1页开始，每页显示10条记录
        PageHelper.startPage(params.getPageNum(), params.getPageSize());

        List<Job> jobDataList = jobDataMapper.qSearch(params);
        PageInfo<Job> pageInfo = new PageInfo<>(jobDataList);

        return pageInfo;
    }

    /**
     * 将mysql数据导入es中
     */
    @Override
    public void jobToElasticSearch()  {
        Params params = new Params();
        params.setPageNum(1);
        params.setPageSize(1000);
        PageHelper.startPage(params.getPageNum(), params.getPageSize());

        // 本质是一个Page对象
        List<Job> jobDataList = jobDataMapper.getJobs(params);
        System.out.println("size:"+jobDataList.size());
        // 插入es中
        try {
            searchService.insert(jobDataList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PageInfo<Job> queryByEs(Params params){
        return searchService.search(params);
    }
}
