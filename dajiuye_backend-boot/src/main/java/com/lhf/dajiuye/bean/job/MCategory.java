package com.lhf.dajiuye.bean.job;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class MCategory implements Serializable {
    Integer id;
    String mName;
    String mDesc;
    Integer lId;
    List<SCategory> children;
}
