package com.lhf.dajiuye.bean.seckill;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sec_lesson")
public class SecLessonEntity implements Serializable {
    private static final long serialVersionUID=1L;
    private Long id;
    private Date startTime;
    private Date endTime;
    private Integer stock;
    private String img;
    private String name;
    @TableField(exist = false)
    // 秒杀token,秒杀开始才暴漏
    private String token;
}
