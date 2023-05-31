package com.lhf.dajiuye.service.message.impl;

//import com.lhf.dajiuye.api.bean.chat.Message;
//import com.lhf.dajiuye.api.bean.chat.MessageDetail;
//import com.lhf.dajiuye.api.service.message.MessageService;
//import com.lhf.dajiuye.message.service.mapper.MessageMapper;
//import org.apache.dubbo.config.annotation.DubboService;
import com.lhf.dajiuye.bean.chat.Message;
import com.lhf.dajiuye.bean.chat.MessageDetail;
import com.lhf.dajiuye.mapper.message.MessageMapper;
import com.lhf.dajiuye.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper mapper;

    @Override
    public void saveMessage(Message msg) {
        mapper.saveMessage(msg);
    }

    @Override
    public List<Message> getAllMessage(String openId) {
        List<Message> allMessage = mapper.getAllMessage(openId);
        return allMessage;
    }

    @Override
    public List<MessageDetail> getAllMessageDetail(String openId) {
        List<MessageDetail> messageDetail = mapper.getAllMessageDetail(openId);
        return messageDetail;
    }
}
