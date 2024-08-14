package com.example.e_chat.chat;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "messages")
public class ChatMessage {


	@Id
	private String id;
	private String content;
	private String sender;
	private MessageType type;

	public ChatMessage(String content, String sender, MessageType type) {
		this.content = content;
		this.sender = sender;
		this.type = type;
	}
}
