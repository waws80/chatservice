package com.thanatos.chatservice.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * des: webSocket配置类
 * author: thanatos
 * data: 2018/7/16
 */
@Configuration
public class WebSocketConfig  {

    @Bean
    public ServerEndpointExporter messageHandler(){
        return new ServerEndpointExporter();
    }
}
