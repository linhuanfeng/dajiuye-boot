package com.lhf.dajiuye.bean.chat;

//import com.hu.health.common.to.MessageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Message implements Serializable {
    int id;
    String fromOpenId;
    String toOpenId;
    Date createTime;
    String news;

    public Message() {
    }

    public Message(MessageDto messageDto) {
        this.fromOpenId = messageDto.getFromOpenId();
        this.toOpenId = messageDto.getToOpenId();
        this.createTime = messageDto.getSendAt();
        this.news = messageDto.getContent();
    }
}
