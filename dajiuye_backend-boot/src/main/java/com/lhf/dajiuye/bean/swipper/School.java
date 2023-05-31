package com.lhf.dajiuye.bean.swipper;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class School implements Serializable {

    public int schId;
    public String schAuthor;
    public String schImg1;
    public String schImg2;
    public String schImg3;
    public String schImg4;
    public String schImg5;
    public String schImg6;
    public String schImg7;
    public String schImg8;
    public String schCreateTime;
    public String schUpdateTime;
    public String schComId;
    public String schStat;
    public String schViewCnt;
    public String schName;
}
