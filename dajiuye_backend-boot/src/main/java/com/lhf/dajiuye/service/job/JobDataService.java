package com.lhf.dajiuye.service.job;



import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lhf.dajiuye.bean.Params;
import com.lhf.dajiuye.bean.job.Job;
//import com.lhf.dajiuye.api.bean.job.Job;
//import com.lhf.dajiuye.api.bean.Params;

import java.util.List;

public interface JobDataService extends IService<Job> {

//    CheckTokenFeignService getfeign();
//    void addJobScore(String message);
    void jobDelivery(String message);

    /**
     * 新增职位
     * @param job
     */
    void saveJob(Job job);

    /**
     * 获取就业列表信息-mysql+redis
     * @param params
     * @return
     */
    PageInfo<Job> getJobs(Params params);
    /**
     * 获取就业列表信息-mysql
     * @param params
     * @return
     */
    PageInfo<Job> getJobsManage(Params params);

    PageInfo<Job> getJobsByCid(Params params);

    /**
     * 获取职位信息2
     *
     * @return
     */
    PageInfo<Job> getJobsByComId(Params params, String comId) ;

    /**
     * 获取就业列表信息
     * @param jobId
     * @return
     */
    Job getJobById(String jobId);

    /**
     * 增加职位权值
     * @param jobId
     */
    void addJobScore(String jobId);
    /**
     * 查询投递职位反馈
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param state
     * @return
     */
    PageInfo<Job> getJobsFeedback(int pageNum, int pageSize, String userId, int state);

    /**
     * 职位定制
     * @param job
     * @return
     */
    List<Job> getJobsCustom(Job job);

    /**
     * 搜索职位
     * @param params
     * @return
     */
    PageInfo<Job> query(Params params);
}
