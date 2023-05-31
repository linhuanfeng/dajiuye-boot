package com.lhf.dajiuye.bean.job;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Company implements Serializable {
    String comId;
    String comFullName;
    String comAuthCapital;
    String comIndustry;
    String comMail;
    String comAddr;
    String comWebsite;
    String comIntro;
    String comMinName;
    String comScale;
    String comLicense;
    String comLogo;
    String comClass;
    String IncorporationDate;
    String comWelfare;
    String comLinks;
}
