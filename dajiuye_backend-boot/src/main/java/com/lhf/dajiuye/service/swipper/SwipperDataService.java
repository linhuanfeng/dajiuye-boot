package com.lhf.dajiuye.service.swipper;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lhf.dajiuye.bean.PageParam;
import com.lhf.dajiuye.bean.swipper.Swiper;


public interface SwipperDataService extends IService<Swiper> {
    PageInfo<Swiper> getSwiperDataList(PageParam param);
}
