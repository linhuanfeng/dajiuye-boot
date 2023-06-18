package com.lhf.dajiuye.bean.message;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class MessageDto implements Serializable {

    /**
     * 来自
     */
    private String fromOpenId;
    private String toOpenId;
    private String content;
    /**
     * 消息类型
     */
    private String type;
    private Date sendAt = new Date();
}
