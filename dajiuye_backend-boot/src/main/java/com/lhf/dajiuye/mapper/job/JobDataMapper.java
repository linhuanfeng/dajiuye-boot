package com.lhf.dajiuye.mapper.job;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhf.dajiuye.bean.Params;
import com.lhf.dajiuye.bean.job.Job;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface JobDataMapper extends BaseMapper<Job> {

    /**
     * 职位保存
     * @param job
     */
    @Insert("insert into job(jobId,jobName,jobIndustry,jobPlace,jobEdu,jobDetails,jobSalary,email,jobType,jobComId,jobReleaseTime,jobAuthorId) values("+
        "#{jobId},#{jobName},#{jobIndustry},#{jobPlace},#{jobEdu},#{jobDetails},#{jobSalary},#{email},#{jobType},#{jobComId},#{jobReleaseTime},#{jobAuthorId})")
    void saveJob(Job job);

    /**
     * 获取职位列表（jobType）
     * jobType:1实习 2校招 3社招
     *
     * @param
     * @return
     */
    @Select("<script>select * from job j,company c where j.jobComId=c.comId " +
//            "<if test='\"\"!=jobId'>and jobId!=#{jobId} </if>" +
//            "<if test='\"\"!=#{jobId} and \"\"==#{isSimilar}'>and jobId=#{jobId} </if>" +
            "<if test=\"-1!=jobType\">and jobType=#{jobType} </if> " +
            "order by jobReleaseTime desc </script>")
    @ResultMap("com.lhf.dajiuye.mapper.job.JobDataMapper.jobMap") // 引用映射
    List<Job> getJobs(  Params params);

    /**
     * 获取职位列表（cid,jobType）
     * @param params
     * @return
     */
    @Select("<script>select * from job j,company c " +
            "where j.jobComId=c.comId and jobSid=#{cid} <if test=\"-1!=jobType\">and jobType=#{jobType} </if> " +
            "order by jobReleaseTime desc limit #{pageNum},#{pageSize}</script>")
    @ResultMap("com.lhf.dajiuye.mapper.job.JobDataMapper.jobMap") // 引用映射
    List<Job> getJobsByCid(Params params);
//    @Select("<script>select * from job j,company c " +
//            "where j.jobComId=c.comId and jobSid=#{cid} " +
//            "order by jobReleaseTime</script>")
//    @ResultMap("com.lhf.dajiuye.job.service.mapper.JobDataMapper.jobMap") // 引用映射
//    List<Job> getJobDataList2(Params params);

    /**
     * 获取职位列表（comId）
     * @param comId
     * @return
     */
    @Select("<script>select * from job j " +
            "where j.jobComId=#{comId} " +
            "order by jobReleaseTime desc</script>")
    @ResultMap("com.lhf.dajiuye.mapper.job.JobDataMapper.jobMap") // 引用映射
    List<Job> getJobsByComId(String comId);

    /**
     * 获取职位（jobId）
     * @param jobId
     * @return
     */
    @Select("select * from job j,company c where j.jobComId=c.comId and jobId=#{jobId} limit 1")
    @ResultMap("com.lhf.dajiuye.mapper.job.JobDataMapper.jobMap") // 引用映射
    Job getJobById(String jobId);

    /**
     * 查询用户投递反馈
     * @return
     * @param userId
     * @param state
     */
    @Select("select d.time jobReleaseTime,j.jobName,j.jobPlace,j.jobDayPerWeek,j.jobEdu,j.jobSalary,j.jobImg,c.comMinName,c.comIndustry " +
            "from user_deliver d,job j,company c " +
            "where d.job_id=j.jobId and j.jobComId=c.comId and d.from_user_id=#{userId} and d.state=#{state}")
    @ResultMap("com.lhf.dajiuye.mapper.job.JobDataMapper.jobMap") // 引用映射
    List<Job> getJobsFeedback(@Param("userId") String userId, @Param("state") int state);

    /**
     * 职位搜索（jobComId,jobName）
     * @param params
     * @return
     */
    @Select("Select * from job j,company c where j.jobComId=c.comId and jobName like concat('%',#{query},'%') order by jobReleaseTime desc")
    @ResultMap("com.lhf.dajiuye.mapper.job.JobDataMapper.jobMap") // 引用映射
    List<Job> qSearch(Params params);

    /**
     * 职位定制查询（jobType，jobPlace，jobName）
     * @param job
     * @return
     */
    @Select("<script>Select * from job j,company c where j.jobComId=c.comId " +
            "<if test='null!=jobType'>and jobType=#{jobType} </if>  " +
            "<if test='\"\"!=jobPlace'>and jobPlace=#{jobPlace} </if> " +
            "<if test='\"\"!=jobName'>and jobName like concat('%',#{jobName},'%')</if> limit 15</script>")
    @ResultMap("com.lhf.dajiuye.mapper.job.JobDataMapper.jobMap") // 引用映射
    List<Job> jobCustom(Job job);
}
