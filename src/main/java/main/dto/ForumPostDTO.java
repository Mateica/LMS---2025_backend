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
	private ForumUser author;
	private List<File> attachments = new ArrayList<File>();
	private Topic topic;
	private Boolean active;

	public ForumPostDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumPostDTO(Long id, LocalDateTime datePublished, String content, ForumUser author, List<File> attachments,
			Topic topic, Boolean active) {
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

	public ForumUser getAuthor() {
		return author;
	}

	public void setAuthor(ForumUser author) {
		this.author = author;
	}

	public List<File> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}