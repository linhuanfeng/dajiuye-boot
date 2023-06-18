package com.lhf.dajiuye.mapper.message;

//import com.lhf.dajiuye.api.bean.chat.Message;
//import com.lhf.dajiuye.api.bean.chat.MessageDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhf.dajiuye.bean.message.Message;
import com.lhf.dajiuye.bean.message.MessageDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 获取两个openId对应用户的所有消息
     * @param msg
     * @return
     */
    @Select("insert into message(from_open_id,to_open_id,news,create_time) values(#{fromOpenId},#{toOpenId},#{news},#{createTime})")
    void saveMessage(Message msg);

    @Deprecated
    @Select("SELECT `news` FROM message WHERE from_open_id=#{openId} UNION SELECT `news` FROM message WHERE to_open_id=#{openId}")
    List<Message> getAllMessage(String openId);

    /**
     * 切记拼接字符串时空格别漏了
     * 找到消息的发送方或接受方是openId的消息
     * @param openId
     * @return
     */
    @Select("SELECT m.`from_open_id`,u1.`avatar` from_avatar,u1.`nick_name` from_name,m.`to_open_id` to_open_id,u2.`avatar` to_avatar,u2.`nick_name` to_name,m.`news`,m.`create_time` FROM user u1 INNER JOIN message m ON u1.`open_id`=m.`from_open_id` AND m.`from_open_id`=#{openId} INNER JOIN user u2 ON u2.`open_id`=m.`to_open_id`"+
            "UNION ALL "+
            "SELECT m.`from_open_id`,u1.`avatar` from_avatar,u1.`nick_name` from_name,m.`to_open_id` to_open_id,u2.`avatar` to_avatar,u2.`nick_name` to_name,m.`news`,m.`create_time` FROM user u1 INNER JOIN message m ON u1.`open_id`=m.`from_open_id` INNER JOIN user u2 ON u2.`open_id`=m.`to_open_id` AND m.`to_open_id`=#{openId}")
    List<MessageDetail> getAllMessageDetail(String openId);
}
