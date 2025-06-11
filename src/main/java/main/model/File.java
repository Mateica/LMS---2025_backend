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
	
	@Column(nullable = false)
	private String name;
	
	@Lob
	@Column(nullable = false)
	private String url;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private ForumPost post;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Message message;
	
	@ManyToOne
	private Announcement announcement;
	
	@ManyToOne
	private Student student;
	
	@Lob
	@Column(nullable = false)
	private byte[] document;
	
	@Column(nullable = false)
	private Boolean active;

	public File() {
		super();
		// TODO Auto-generated constructor stub
	}

	public File(Long id, String name, String url, String description, ForumPost post, Message message,
			Announcement announcement, Student student, byte[] document, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.description = description;
		this.post = post;
		this.message = message;
		this.announcement = announcement;
		this.student = student;
		this.document = document;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public ForumPost getPost() {
		return post;
	}

	public void setPost(ForumPost post) {
		this.post = post;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public byte[] getDocument() {
		return document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	

}
