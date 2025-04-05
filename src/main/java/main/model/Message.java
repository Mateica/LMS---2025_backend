package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime timePublished;
	
	@OneToOne
	private RegisteredUser sender;
	
	@OneToOne
	private RegisteredUser receiver;
	
	@OneToMany(mappedBy = "message")
	private List<File> attachments = new ArrayList<File>();
	
	@Column(nullable = false)
	private Boolean active;

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Message(Long id, LocalDateTime timePublished, RegisteredUser sender, RegisteredUser receiver,
			List<File> attachments, Boolean active) {
		super();
		this.id = id;
		this.timePublished = timePublished;
		this.sender = sender;
		this.receiver = receiver;
		this.attachments = attachments;
		this.active = active;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTimePublished() {
		return timePublished;
	}

	public void setTimePublished(LocalDateTime timePublished) {
		this.timePublished = timePublished;
	}

	public RegisteredUser getSender() {
		return sender;
	}

	public void setSender(RegisteredUser sender) {
		this.sender = sender;
	}

	public RegisteredUser getReceiver() {
		return receiver;
	}

	public void setReceiver(RegisteredUser receiver) {
		this.receiver = receiver;
	}

	public List<File> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
