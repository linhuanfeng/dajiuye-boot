package com.lhf.dajiuye.bean.job;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class CategoryMiddle implements Serializable {
    Integer id;
    String mName;
    String mDesc;
    Integer lId;
    @TableField(exist = false)
    List<CategorySmall> children;
}
