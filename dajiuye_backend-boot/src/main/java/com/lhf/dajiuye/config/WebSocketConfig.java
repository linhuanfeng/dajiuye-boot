package com.lhf.dajiuye.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * stomp格式
 */
@Configuration
@EnableWebSocketMessageBroker // 让程序能够识别出 连接了暴露的stomp节点发送过来的请求，配合 @MessageMapping 是用
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * 使用默认消息代理（基于内存的broker）,可通过订阅该频道获取消息
         * 一般topic表示公用频道，user表示私有频道
         */
        registry.enableSimpleBroker("/topic","/user") // topic或user前缀的消息将会被代理，被@MessageMapping处理
                .setHeartbeatValue(new long[]{10000L, 10000L})
                .setTaskScheduler(new DefaultManagedTaskScheduler());

//        registry.enableStompBrokerRelay("/exchange","/topic","/queue","/amq/queue") // 所有消息发送给外部代理
//                .setVirtualHost("/")
//                .setRelayHost("192.168.42.100")
////                .setRelayPort(5672)
//                .setClientLogin("itcast")
//                .setClientPasscode("123")
//                .setSystemLogin("itcast")
//                .setSystemPasscode("123")
//                .setSystemHeartbeatSendInterval(5000)
//                .setSystemHeartbeatReceiveInterval(4000);

        /**
         * 客户端发送消息时都要带上该前缀
         * stompClient.subscribe('/user/openid/private', function(message, headers) {
         * stompClient.subscribe('/topic/public/' + roomId, function(message) {
         *
         * stompClient.send("/app/public/" + roomId, {}, JSON.stringify(message));
         * stompClient.send("/app/private", {}, JSON.stringify(msg));
         */
        registry.setApplicationDestinationPrefixes("/app"); // 客户端通过这个前缀发送消息
        // 使用SimpMessagingTemplate.convertAndSendToUser() 会自动拼上user前缀
        registry.setUserDestinationPrefix("/user"); // 客户端订阅/user前缀才会收到单发消息，服务端指定一对一推送信息，默认拼接上/user/
    }

    /**
     * 暴露端点，客户端要订阅得先连上这个端点
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOrigins("*");
    }
}
