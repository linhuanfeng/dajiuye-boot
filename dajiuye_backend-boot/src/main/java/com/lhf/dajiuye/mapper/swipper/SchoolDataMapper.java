package com.lhf.dajiuye.mapper.swipper;

//import com.lhf.dajiuye.api.bean.swipper.School;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhf.dajiuye.bean.acl.UserRole;
import com.lhf.dajiuye.bean.swipper.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SchoolDataMapper extends BaseMapper<School> {

    /**
     * 获取校招实体信息
     * @param schId
     * @return
     */
    @Select("<script>select * from school\n" +
            "        <where> schId=#{schId}"+
                    "</where></script>")
    List<School> getSchoolDataList(int schId);
}
