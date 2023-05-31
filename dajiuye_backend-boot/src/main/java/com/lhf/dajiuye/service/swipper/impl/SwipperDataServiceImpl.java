package com.lhf.dajiuye.service.swipper.impl;

//import com.lhf.dajiuye.api.bean.swipper.CatItems;
//import com.lhf.dajiuye.api.bean.swipper.SwiperData;
//import com.lhf.dajiuye.api.service.swipper.SwipperDataService;
//import com.lhf.dajiuye.swipper.service.mapper.SwiperDataMapper;
//import org.apache.dubbo.config.annotation.DubboService;
import com.lhf.dajiuye.bean.swipper.CatItems;
import com.lhf.dajiuye.bean.swipper.SwiperData;
import com.lhf.dajiuye.mapper.swipper.SwiperDataMapper;
import com.lhf.dajiuye.service.swipper.SwipperDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SwipperDataServiceImpl implements SwipperDataService {

    @Autowired
    private SwiperDataMapper mapper;

    /**
     * 获取轮播图数据
     *
     * @return
     * @throws IOException
     */
    @Override
    public List<SwiperData> getswiperDataList() {
        System.out.println("service22222222");
        List<SwiperData> swiperDataList = mapper.getSwiperDataList();
        return swiperDataList;
    }

    /**
     * 获取分类导航栏数据
     *
     * @return
     * @throws IOException
     */
    @Override
    public List<CatItems> getcatItemsList() {
        List<CatItems> catItemsList = mapper.getcatItemsDataList();
        return catItemsList;
    }
}
