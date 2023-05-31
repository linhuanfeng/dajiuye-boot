package com.lhf.dajiuye.service.swipper;



//import com.lhf.dajiuye.api.bean.swipper.CatItems;
//import com.lhf.dajiuye.api.bean.swipper.SwiperData;

import com.lhf.dajiuye.bean.swipper.CatItems;
import com.lhf.dajiuye.bean.swipper.SwiperData;

import java.util.List;

public interface SwipperDataService {
    List<CatItems> getcatItemsList() ;
    List<SwiperData> getswiperDataList() ;
}
