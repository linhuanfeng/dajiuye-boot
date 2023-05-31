package com.lhf.dajiuye.service.swipper.impl;

//import com.lhf.dajiuye.api.bean.swipper.City;
//import com.lhf.dajiuye.api.bean.swipper.District;
//import com.lhf.dajiuye.api.bean.swipper.Province;
//import com.lhf.dajiuye.api.service.swipper.AreaService;
//import com.lhf.dajiuye.swipper.service.mapper.AreaDataMapper;
//import org.apache.dubbo.config.annotation.DubboService;
import com.lhf.dajiuye.bean.swipper.City;
import com.lhf.dajiuye.bean.swipper.District;
import com.lhf.dajiuye.bean.swipper.Province;
import com.lhf.dajiuye.mapper.swipper.AreaDataMapper;
import com.lhf.dajiuye.service.swipper.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDataMapper mapper;
    /**
     * 获取省信息列表
     * @return
     */
    @Override
    public List<Province> getProvinceDataList() {
        List<Province> provinceDataList = mapper.getProvinceDataList();
        return provinceDataList;
    }

    /**
     * 获取市信息列表
     * @return
     */
    @Override
    public List<City> getCityDataList() {
        List<City> cityDataList = mapper.getCityDataList();
        return cityDataList;
    }

    /**
     * 获取县信息列表
     * @return
     */
    @Override
    public List<District> getDistrictDataList() {
        List<District> districtDataList = mapper.getDistrictDataList();
        return districtDataList;
    }

    /**
     * 获取省市县信息列表
     * @return
     */
    @Override
    public List<Province> getFullPlaceDataList() {
        List<Province> provinceList=mapper.getProvinceDataList();
        List<City> cityDataList = mapper.getCityDataList();
//        List<District> districtDataList=schoolDataMapper.getDistrictDataList();

//        for (District district : districtDataList) {
//            // 因为默认升序排列
//            int n=Integer.parseInt(district.getCity_id())-1;
//            if(cityDataList.get(n).getChildren()==null){
//                cityDataList.get(n).setChildren(new ArrayList<District>());
//            }
//            cityDataList.get(n).getChildren().add(district);
//        }

        for (City city : cityDataList) {
            int n=Integer.parseInt(city.getProvinceId())-1;
            if(provinceList.get(n).getChildren()==null){
                provinceList.get(n).setChildren(new ArrayList<City>());
            }
            provinceList.get(n).getChildren().add(city);
        }

        return provinceList;
    }
}
