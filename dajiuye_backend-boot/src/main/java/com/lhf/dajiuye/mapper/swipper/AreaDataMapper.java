package com.lhf.dajiuye.mapper.swipper;

//import com.lhf.dajiuye.api.bean.swipper.City;
//import com.lhf.dajiuye.api.bean.swipper.District;
//import com.lhf.dajiuye.api.bean.swipper.Province;
import com.lhf.dajiuye.bean.swipper.City;
import com.lhf.dajiuye.bean.swipper.District;
import com.lhf.dajiuye.bean.swipper.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AreaDataMapper {
    /**
     * 获取省份信息
     * @return
     */

    @Select("select * from area_province")
    List<Province> getProvinceDataList();

    /**
     * 获取市信息
     * @return
     */
    @Select("select * from area_city")
    List<City> getCityDataList();

    /**
     * 获取县信息
     * @return
     */
    @Select("select * from area_district")
    List<District> getDistrictDataList();
}
