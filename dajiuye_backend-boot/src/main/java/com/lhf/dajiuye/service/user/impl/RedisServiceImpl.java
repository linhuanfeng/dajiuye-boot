package com.lhf.dajiuye.service.user.impl;

import com.alibaba.fastjson.JSON;
//import com.lhf.dajiuye.api.bean.Params;
//import com.lhf.dajiuye.api.bean.job.Job;
//import com.lhf.dajiuye.user.service.constants.JobConstants;
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

//import com.lhf.dajiuye.constants.JobConstants;
//import com.lhf.dajiuye.domain.Job;
//import com.lhf.dajiuye.domain.Params;

//@Service
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
     * @return
     * @return
     */
    public void incrementJobScore(String jobId){
        // 每次点赞增加1小时的毫秒数权值
        BoundZSetOperations boundZSetOps = redisTemplate.boundZSetOps(JobConstants.JOB_HOT);
        boundZSetOps.incrementScore(jobId, 1.0 *1000*60* 60 * 1);
        boundZSetOps.expire(3,TimeUnit.HOURS);

    }

    /**
     * 根据id从redis中获取数据
     * @return
     * @return
     */
    public List<Job> getHotJobs(Params params){
        // 获取当前权值最大的几个
        Set<String> set = redisTemplate.boundZSetOps(JobConstants.JOB_HOT).reverseRange(0,3);

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

    public void saveJob(Job job) {
        /**
         * id和时间存到zset
         * id和对象存到hset
          */

        // 职位发布时间排行榜：职位id和时间存到zset
        redisTemplate.opsForZSet().add(JobConstants.JOB_HOT,job.getJobId(), System.currentTimeMillis());
        redisTemplate.expire(JobConstants.JOB_HOT,3,TimeUnit.DAYS);

        addJobToHash(job);
    }
    public void addJobToHash(Job job){
        // id和对象存到hset
        BoundHashOperations<String, String, String> boundHashOps = redisTemplate.boundHashOps(JobConstants.JOB_ENTITY);
        boundHashOps.put(job.getJobId(), JSON.toJSONString(job));
        boundHashOps.expire(3,TimeUnit.DAYS);
    }
}
