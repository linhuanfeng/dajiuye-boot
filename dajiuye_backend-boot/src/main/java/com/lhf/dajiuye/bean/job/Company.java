package com.lhf.dajiuye.bean.job;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Company implements Serializable {
    String comId;
    String comFullName;
    String comMinName;
    String comAuthCapital;
    String comScale;
    String comClass;
    String comMail;
    String comIndustry;
    String comLogo;
    String comAddr;
    String comWebsite;
    String comIntro;
    String comLicense;
    String incorporationDate;
    String comWelfare;
    String comLinks;
}
