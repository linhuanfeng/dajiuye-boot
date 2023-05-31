package com.lhf.dajiuye.bean.chat;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageDetail implements Serializable {
    String fromOpenId;
    String fromAvatar;
    String fromName;
    String toOpenId;
    String toAvatar;
    String toName;
    String news;
    Date createTime;
}
