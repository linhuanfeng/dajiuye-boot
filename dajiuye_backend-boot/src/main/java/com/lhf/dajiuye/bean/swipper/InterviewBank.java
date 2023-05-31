package com.lhf.dajiuye.bean.swipper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Primary;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@TableName("interview_bank")
public class InterviewBank implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Date createTime;
    private Date updateTime;
    private String tag;
    private String title;
    private String content;
}
