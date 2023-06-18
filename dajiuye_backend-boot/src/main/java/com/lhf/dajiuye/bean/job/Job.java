package com.lhf.dajiuye.bean.job;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhf.dajiuye.bean.user.User;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@TableName("job")
public class Job implements Serializable {
    String jobId;
    String jobSalary;
    String jobName;
    String jobPlace;
    String jobEdu;
    String jobAge;
    String jobDayPerWeek;
    String jobComId;
    Integer jobSid;
    Integer jobMid;
    Date jobUpdateTime;
    Date jobReleaseTime;
    String email;
    Integer jobType;
    String jobAuthorId;
    String jobStat;
    String jobViewCnt;
    String jobPriority;
    String jobTimeSpan;
    String jobDeadLine;
    String jobResponsibilities;
    String jobRequirements;
    @TableField(exist = false)
    Company company;
    @TableField(exist = false)
    User user;
    @TableField(exist = false)
    String jobTypeName;
}
