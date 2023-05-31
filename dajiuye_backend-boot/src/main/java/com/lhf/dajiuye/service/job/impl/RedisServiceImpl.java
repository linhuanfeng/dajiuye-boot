package com.lhf.dajiuye.service.job.impl;

import com.alibaba.fastjson.JSON;
//import com.lhf.dajiuye.api.bean.Params;
//import com.lhf.dajiuye.api.bean.job.Job;
//import com.lhf.dajiuye.job.service.constants.JobConstants;
import com.lhf.dajiuye.bean.Params;
import com.lhf.dajiuye.bean.job.Job;
import com.lhf.dajiuye.constants.JobConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RedisServiceImpl {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据id从redis中获取数据
     * @param jobId
     * @return
     */
    public Job getJobRedisById(String jobId){
        BoundHashOperations<String,String,String> boundHashOps = redisTemplate.boundHashOps(JobConstants.JOB_ENTITY);
        String job = boundHashOps.get(jobId);
        if(job!=null){
            return JSON.parseObject(job,Job.class);
        }
        return null;
    }

    /**
     * 根据id从redis中获取数据
     * 每次点赞增加1小时的毫秒数权值
     * @return
     */
    public void incrementJobScore(String jobId){
        BoundZSetOperations zSetOps = redisTemplate.boundZSetOps(JobConstants.JOB_HOT);
        Long size = zSetOps.size();
        zSetOps.incrementScore(jobId, 10); // zset权值增加
        zSetOps.expire(zSetOps.getExpire()+15,TimeUnit.HOURS); // 过期时间增加15小时
        // 判断是否zset的大小，移除冷数据
        // 判断是否需要减少缓存内容
        if(size>30){
            // 批量删除排名30之后的评论
            zSetOps.removeRange(0,size-30);
        }
    }

    /**
     * 根据id从redis中获取数据
     * 如何保存热点职位：
     * 使用zset记录，权值为职位点击+时间戳
     * @return
     * @return
     */
    public List<Job> getHotJobs(Params params){
        // 获取当前权值最大的几个
        Set<String> set = redisTemplate.boundZSetOps(JobConstants.JOB_HOT).reverseRange(params.getPageNum(),params.getPageSize()/2);

        BoundHashOperations<String, String, String> boundHashOps = redisTemplate.boundHashOps(JobConstants.JOB_ENTITY);

        List<Job> collect = set.stream().map((jobId) -> JSON.parseObject(boundHashOps.get(jobId),Job.class)).collect(Collectors.toList());
        return collect;
    }

    /**
     * 根据id从redis中获取数据
     * @return
     * @return
     */
    public List<Job> getJobsLatest(){
        // 获取当前权值最小的几个
        Set<String> set = redisTemplate.boundZSetOps("job:time").reverseRange(0,3);

        BoundHashOperations<String, String, String> boundHashOps = redisTemplate.boundHashOps(JobConstants.JOB_ENTITY);

        List<Job> collect = set.stream().map((jobId) -> JSON.parseObject(boundHashOps.get(jobId),Job.class)).collect(Collectors.toList());
        return collect;
    }

    /**
     * 保存职位到redis中
     * ZSet: job:hot score(时间戳) 过期时间三天
     * Hash: key(job:entity) field(jobId) value(jobEntity)
     * @param job
     */
    public void saveJob(Job job) {
        // ZSet 权值为时间戳
        redisTemplate.opsForZSet().add(JobConstants.JOB_HOT,job.getJobId(), System.currentTimeMillis());
        redisTemplate.expire(JobConstants.JOB_HOT,3,TimeUnit.DAYS);

        // Hash id和对象
        BoundHashOperations<String, String, String> boundHashOps = redisTemplate.boundHashOps(JobConstants.JOB_ENTITY);
        boundHashOps.put(job.getJobId(), JSON.toJSONString(job));
        boundHashOps.expire(3,TimeUnit.DAYS);
    }
}
