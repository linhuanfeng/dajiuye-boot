package com.lhf.dajiuye.bean.message;

//import com.hu.health.common.to.MessageDto;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Message implements Serializable {
    @TableId
    int id;
    String fromOpenId;
    String toOpenId;
    Date createTime=new Date();
    String news;

    public Message() {
    }

    public Message(MessageDto messageDto) {
        this.fromOpenId = messageDto.getFromOpenId();
        this.toOpenId = messageDto.getToOpenId();
        this.createTime = messageDto.getSendAt();
        this.news = messageDto.getContent();
    }

    public static Message getMessage(MessageDto messageDto){
        return new Message(messageDto);
    }
}
