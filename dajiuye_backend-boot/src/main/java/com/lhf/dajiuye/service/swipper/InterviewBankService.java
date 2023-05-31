package com.lhf.dajiuye.service.swipper;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhf.dajiuye.bean.swipper.InterviewBank;
import com.lhf.dajiuye.util.PageUtils;
//import com.hu.health.common.utils.PageUtils;
//import com.lhf.dajiuye.api.bean.swipper.InterviewBank;
//import com.lhf.dajiuye.api.util.PageUtils;

import java.util.Map;

public interface InterviewBankService extends IService<InterviewBank> {
    PageUtils queryPage(Map<String, Object> params);
}
