package com.example.e_chat.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller  // Marks this class as a Spring MVC controller
@RequiredArgsConstructor  // Generates a constructor with required fields (final)
public class ChatController {

	private final ChatMessageRepository messageRepository;  // Inject the message repository

	@MessageMapping("/chat.sendMessage")  // Maps incoming messages to this method
	@SendTo("/topic/public")  // Sends the return value of this method to the specified topic
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		// Save message to MongoDB
		ChatMessage messageEntity = new ChatMessage(chatMessage.getContent(), chatMessage.getSender(), chatMessage.getType());
		messageRepository.save(messageEntity);

		return chatMessage;
	}

	@MessageMapping("/chat.addUser")  // Maps incoming messages to this method
	@SendTo("/topic/public")  // Sends the return value of this method to the specified topic
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// Add username to WebSocket session attributes
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}
}
