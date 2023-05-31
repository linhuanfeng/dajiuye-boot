package com.lhf.dajiuye.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

//@Configuration
@Slf4j
public class RabbitConfig {
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void setConfirmCallBack(){
        // 通过ack判断是否到达broker
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause)->{
            if(ack){
                log.info("ConfirmCallBack:消息投递broker成功");
            }else {
                log.error("ConfirmCallBack：消息投递broker失败");
            }
        });
        // 到达队列失败才返回
        // ReturnedMessage
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey)->{
            log.info("setReturnCallback:{}",message);
        });
    }
}
