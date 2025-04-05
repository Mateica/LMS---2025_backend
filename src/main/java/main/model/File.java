package main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class File {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String url;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Announcement announcement;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Message message;
	
	@ManyToOne
	private Notification notification;
	
	@Column(nullable = false)
	private Boolean active;
	
	public File() {
		super();
	}

	
	public File(Long id, String url, String description, Announcement announcement, Message message,
			Notification notification, Boolean active) {
		super();
		this.id = id;
		this.url = url;
		this.description = description;
		this.announcement = announcement;
		this.message = message;
		this.notification = notification;
		this.active = active;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	
}
