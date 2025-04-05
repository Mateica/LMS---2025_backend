package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime timePublished;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@Lob
	@Column(nullable = false)
	private String title;
	
	@OneToMany(mappedBy = "notification")
	private List<File> attachments = new ArrayList<File>();
	
	@Column(nullable = false)
	private Boolean active;

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Notification(Long id, LocalDateTime timePublished, String content, String title, List<File> attachments,
			Boolean active) {
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
