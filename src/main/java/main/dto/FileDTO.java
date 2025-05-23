package main.dto;

import main.model.Announcement;
import main.model.Evaluation;
import main.model.ForumPost;
import main.model.Message;
import main.model.Student;

public class FileDTO {
	private Long id;
	
	private String name;
	
	private String url;
	
	private String description;
	
	private ForumPostDTO post;
	
	private MessageDTO message;
	
	private AnnouncementDTO announcement;
	
	private EvaluationDTO evaluation;

	private StudentDTO student;
	
	private byte[] document;

	private Boolean active;

	public FileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileDTO(Long id, String name, String url, String description, ForumPostDTO post, MessageDTO message,
			AnnouncementDTO announcement, EvaluationDTO evaluation, StudentDTO student, byte[] document,
			Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.description = description;
		this.post = post;
		this.message = message;
		this.announcement = announcement;
		this.evaluation = evaluation;
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

	public ForumPostDTO getPost() {
		return post;
	}

	public void setPost(ForumPostDTO post) {
		this.post = post;
	}

	public MessageDTO getMessage() {
		return message;
	}

	public void setMessage(MessageDTO message) {
		this.message = message;
	}

	public AnnouncementDTO getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(AnnouncementDTO announcement) {
		this.announcement = announcement;
	}

	public EvaluationDTO getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(EvaluationDTO evaluation) {
		this.evaluation = evaluation;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
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
