package com.lhf.dajiuye.service.swipper;



//import com.lhf.dajiuye.api.bean.swipper.City;
//import com.lhf.dajiuye.api.bean.swipper.District;
//import com.lhf.dajiuye.api.bean.swipper.Province;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhf.dajiuye.bean.Cascade;
import com.lhf.dajiuye.bean.job.Job;
import com.lhf.dajiuye.bean.swipper.Area;
import com.lhf.dajiuye.bean.swipper.City;
import com.lhf.dajiuye.bean.swipper.District;
import com.lhf.dajiuye.bean.swipper.Province;

import java.util.List;

public interface AreaService extends IService<Area> {
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
    /**
     * 获取省市县信息列表--封装级联选择器对象
     * @return
     */
    List<Cascade> getFullPlaceDataListCascade();
}
