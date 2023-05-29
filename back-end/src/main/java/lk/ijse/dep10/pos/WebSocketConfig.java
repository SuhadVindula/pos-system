package lk.ijse.dep10.pos;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.dep10.pos.api.CustomerWSHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customerWSHandler(), "customers-ws");
    }

    @Bean
    public CustomerWSHandler customerWSHandler(){
        return new CustomerWSHandler();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}