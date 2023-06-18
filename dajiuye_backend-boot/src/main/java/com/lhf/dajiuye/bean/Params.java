package com.lhf.dajiuye.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * 查询参数
 */
public class Params implements Serializable {
    private int pageSize = 10;
    private int pageNum = 1;
    // 查询的内容
    private String query = "";
    // 分类id
    private String cid = "";
    // 职位类型，1实习，2校招，3社招
    public Integer jobType = -1;
    // 学历
    private String jobEdu = "";
    private String jobAge = "";
    // 薪资
    private String jobSalary = "";
    // 城市
    private String city="";
    // 公司规模
    private String com_scale="";
    public void adjust(){
        city=city.replaceAll("市","");
        if(jobType==null){
            jobType=-1;
        }
    }
}
