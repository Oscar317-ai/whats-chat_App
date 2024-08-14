package com.example.e_chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration  // Marks this class as a source of bean definitions for the application context
@EnableWebSocketMessageBroker  // Enables WebSocket message handling, backed by a message broker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// Register the /ws endpoint, enabling SockJS fallback options so that alternate transports can be used if WebSocket is not available
		registry.addEndpoint("/ws")
				.setAllowedOrigins("https://5efa-102-210-221-6.ngrok-free.app")  // Allow cross-origin requests from this URL
				.withSockJS();  // Enable SockJS fallback
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// Set prefix for messages bound for methods annotated with @MessageMapping
		registry.setApplicationDestinationPrefixes("/app");
		// Enable a simple memory-based message broker to carry the messages back to the client on destinations prefixed with /topic
		registry.enableSimpleBroker("/topic");
	}
}
