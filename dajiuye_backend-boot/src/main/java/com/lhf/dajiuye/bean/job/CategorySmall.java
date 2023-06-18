package com.lhf.dajiuye.bean.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CategorySmall implements Serializable {
    Integer id;
    String sName;
    String sDesc;
    @JsonProperty("mId")
    Integer mId;
}
