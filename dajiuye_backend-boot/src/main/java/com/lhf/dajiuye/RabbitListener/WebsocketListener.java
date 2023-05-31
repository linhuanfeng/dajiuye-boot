package com.lhf.dajiuye.RabbitListener;

//import com.hu.health.common.to.MessageDto;
//import com.lhf.dajiuye.api.bean.chat.MessageDto;
//import com.lhf.dajiuye.api.service.message.MessageService;
import com.lhf.dajiuye.bean.chat.MessageDto;
import com.lhf.dajiuye.service.message.MessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
@Slf4j
//@RabbitListener(queues = {"websocket.queue"})
public class WebsocketListener {

    @Autowired
    private MessageService messageService;

    @RabbitHandler
    public void listenMessageQueue(MessageDto messageDto, Message message, Channel channel){
        log.info("接收到消息：{}",messageDto);
        try {
//            messageService.saveMessage(new com.lhf.dajiuye.api.bean.chat.Message(messageDto));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
