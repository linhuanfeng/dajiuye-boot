package com.lhf.dajiuye.mapper.user;

//import com.lhf.dajiuye.api.bean.user.User;
import com.lhf.dajiuye.bean.user.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDataMapper {
    @Select("select * from user where open_id=#{openId}")
    User getUserByOpenId(@Param("openId") String openId);

    @Select("select * from user where id=#{id}")
    User getUserById(@Param("id") String id);

    /**
     *
     * 获取所有投递者
     * @param id hr的id
     * @param state 简历的状态
     * @return
     */
    @Select("<script> select d.id tempDeliverId,j.jobName temp_job_name,date(d.time) temp_deliver_time,u.resume,u.mobile,u.university ,u.city,u.qualification,u.graduation_year,u.avatar,u.nick_name,u.honor,d.from_user_id,d.to_hr_id,d.state,d.time,d.resume_url,d.job_id from user_deliver d" +
            " inner join user u on d.from_user_id=u.id" +
            " inner join job j on d.job_id=j.jobId and d.to_hr_id=#{id} <if test=\"''!=state\"> and d.state=#{state}</if> </script>")
    List<User> getUsers(@Param("id") String id, @Param("state") String state);

    @Insert("insert into user(" +
            "id,user_name,user_password,gender,birthday,temp_deliver_time,last_login_ip,com_id,nick_name," +
            "mobile,avatar,open_id,session_key,user_status,isHr,resume,city,province,university,qualification,graduation_year,honor,temp_job_name) values(" +
            "#{id},#{userName},#{userPassword},#{gender},#{birthday},#{tempDeliverTime},#{lastLoginIp},#{comId},#{nickName}," +
            "#{mobile},#{avatar},#{openId},#{sessionKey},#{userStatus},#{isHr},#{resume},#{city},#{province},#{university},#{qualification},#{graduationYear},#{honor},#{tempJobName})")
    @Options(useGeneratedKeys = true,keyProperty ="id",keyColumn = "id")
    int insertUser(User user);

    @Update("update user set user_name=#{userName},gender=#{gender},birthday=#{birthday},temp_deliver_time=#{tempDeliverTime}," +
            "last_login_ip=#{lastLoginIp},nick_name=#{nickName},"+
            "mobile=#{mobile},avatar=#{avatar},session_key=#{sessionKey},user_status=#{userStatus},is_hr=#{isHr}" +
            " where open_id=#{openId}")
    int updateUser(User user);

    @Update("update user set resume=#{resumeUrl} where open_id=#{openId}")
    int updateUserResume(@Param("resumeUrl") String resumeUrl,@Param("openId") String openId);
}
