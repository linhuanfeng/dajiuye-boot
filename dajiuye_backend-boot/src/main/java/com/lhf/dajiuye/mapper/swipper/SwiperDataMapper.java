package com.lhf.dajiuye.mapper.swipper;

import com.lhf.dajiuye.bean.swipper.CatItems;
import com.lhf.dajiuye.bean.swipper.SwiperData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SwiperDataMapper {

    /**
     * 获取轮播图数据
     */
    @Select("select * from swiper")
    List<SwiperData> getSwiperDataList();

    /**
     * 获取分类导航栏数据
     * @return
     */
    @Select("select * from index_cate")
    List<CatItems> getcatItemsDataList();
}
