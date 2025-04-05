package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Announcement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime datePublished;
	
	@Column(nullable = false)
	private String content;
	
	@ManyToOne
	private ForumUser author;
	
	@OneToMany(mappedBy = "announcement")
	@Column(nullable = true)
	private List<File> attachments = new ArrayList<File>();
	
	@ManyToOne
	private Topic topic;
	
	@Column(nullable = false)
	private Boolean active;

	public Announcement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Announcement(Long id, LocalDateTime datePublished, String content, ForumUser author, List<File> attachments,
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
