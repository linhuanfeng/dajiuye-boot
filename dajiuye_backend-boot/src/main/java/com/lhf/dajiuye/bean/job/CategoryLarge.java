package com.lhf.dajiuye.bean.job;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class CategoryLarge implements Serializable {
    Integer id;
    String lName;
    String lDesc;
    @TableField(exist = false)
    List<CategoryMiddle> children;
}
