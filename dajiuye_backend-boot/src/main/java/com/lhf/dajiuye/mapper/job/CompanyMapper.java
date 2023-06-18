package com.lhf.dajiuye.mapper.job;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhf.dajiuye.bean.job.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
    /**
     * 获取公司信息列表数据(有Id则查找单个)
     * @param comId
     * @return
     */
    @Select("<script> select * from company\n" +
            "        <where>\n" +
            "            <if test=\"comId!=''\">\n" +
            "                and comId=#{comId}\n" +
            "            </if>\n" +
            "        </where></script>")
    List<Company> getComDataList(String comId);
}
