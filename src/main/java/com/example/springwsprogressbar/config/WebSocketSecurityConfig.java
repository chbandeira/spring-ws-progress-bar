package com.example.springwsprogressbar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
 
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
 
}

