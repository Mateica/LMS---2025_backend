package main.dto;

import java.time.LocalDateTime;

public class MessageDTO {
	private LocalDateTime timePublished;
	private RegisteredUserDTO sender;
	private RegisteredUserDTO receiver;
	private String content;
	
	public MessageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageDTO(LocalDateTime timePublished, RegisteredUserDTO sender, RegisteredUserDTO receiver,
			String content) {
		super();
		this.timePublished = timePublished;
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
	}

	public LocalDateTime getTimePublished() {
		return timePublished;
	}

	public void setTimePublished(LocalDateTime timePublished) {
		this.timePublished = timePublished;
	}

	public RegisteredUserDTO getSender() {
		return sender;
	}

	public void setSender(RegisteredUserDTO sender) {
		this.sender = sender;
	}

	public RegisteredUserDTO getReceiver() {
		return receiver;
	}

	public void setReceiver(RegisteredUserDTO receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
