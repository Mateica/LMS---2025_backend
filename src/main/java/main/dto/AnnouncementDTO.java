package main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDTO {
	private Long id;
	private LocalDateTime timePublished;
	private String content;
	private String title;
	private List<FileDTO> attachments = new ArrayList<FileDTO>();
	private Boolean active;
	
	
	public AnnouncementDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AnnouncementDTO(Long id, LocalDateTime timePublished, String content, String title,
			List<FileDTO> attachments, Boolean active) {
		super();
		this.id = id;
		this.timePublished = timePublished;
		this.content = content;
		this.title = title;
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


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public List<FileDTO> getAttachments() {
		return attachments;
	}


	public void setAttachments(List<FileDTO> attachments) {
		this.attachments = attachments;
	}


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}
}
