package com.lhf.dajiuye.RabbitListener;

//import com.hu.health.common.to.MessageDto;
//import com.lhf.dajiuye.api.constant.JobMqConstants;
//import com.lhf.dajiuye.api.service.message.MessageService;
import com.lhf.dajiuye.constant.JobMqConstants;
import com.lhf.dajiuye.service.message.MessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
@Slf4j
public class JobListener {

    @Autowired
    private MessageService messageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = JobMqConstants.JOB_VIEW_QUEUE, durable = "true"),
            exchange = @Exchange(name = JobMqConstants.JOB_EXCHANGE,type = "topic"),
            key = JobMqConstants.JOB_VIEW_KEY
    ))
    public void listenJobViewQueue(String jobId, Message message, Channel channel) {
        log.info("listenJobViewQueue:接收到消息：{}", jobId);
        try {
            // 增加职位热度
//            messageService.saveMessage(new com.lhf.dajiuye.api.bean.chat.Message(messageDto));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
