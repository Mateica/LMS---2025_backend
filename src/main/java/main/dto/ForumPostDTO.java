package main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.model.File;
import main.model.ForumUser;
import main.model.Topic;

public class ForumPostDTO {

	private Long id;
	private LocalDateTime datePublished;
	private String content;
	private ForumUserDTO author;
	private List<FileDTO> attachments = new ArrayList<FileDTO>();
	private TopicDTO topic;
	private Boolean active;

	public ForumPostDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumPostDTO(Long id, LocalDateTime datePublished, String content, ForumUserDTO author,
			List<FileDTO> attachments, TopicDTO topic, Boolean active) {
		super();
		this.id = id;
		this.datePublished = datePublished;
		this.content = content;
		this.author = author;
		this.attachments = attachments;
		this.topic = topic;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(LocalDateTime datePublished) {
		this.datePublished = datePublished;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ForumUserDTO getAuthor() {
		return author;
	}

	public void setAuthor(ForumUserDTO author) {
		this.author = author;
	}

	public List<FileDTO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<FileDTO> attachments) {
		this.attachments = attachments;
	}

	public TopicDTO getTopic() {
		return topic;
	}

	public void setTopic(TopicDTO topic) {
		this.topic = topic;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}