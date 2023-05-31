package com.lhf.dajiuye.bean.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户投递简历实体类
 */
@Data
public class Userdeliver implements Serializable {
    int id;
    String fromUserId;
    String toHrId;
    String resumeUrl;
    Date time;
    String jobId;
    int state;
    String message;
}
