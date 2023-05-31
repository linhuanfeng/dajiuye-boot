package com.lhf.dajiuye.service.swipper;



//import com.lhf.dajiuye.api.bean.swipper.City;
//import com.lhf.dajiuye.api.bean.swipper.District;
//import com.lhf.dajiuye.api.bean.swipper.Province;

import com.lhf.dajiuye.bean.swipper.City;
import com.lhf.dajiuye.bean.swipper.District;
import com.lhf.dajiuye.bean.swipper.Province;

import java.util.List;

public interface AreaService {
    /**
     * 获取省信息列表
     * @return
     */
    List<Province> getProvinceDataList();

    /**
     * 获取市列表
     * @return
     */
    List<City> getCityDataList();

    /**
     * 获取县信息列表
     * @return
     */
    List<District> getDistrictDataList();

    /**
     * 获取省市县信息列表
     * @return
     */
    List<Province> getFullPlaceDataList();
}
