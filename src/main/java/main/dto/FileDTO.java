package main.dto;

public class FileDTO {
	private Long id;
	private String url;
	private String description;
	private ForumPostDTO post;
	private MessageDTO message;
	private AnnouncementDTO announcement;
	private Boolean active;
	
	public FileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileDTO(Long id, String url, String description, ForumPost post, Message message, Announcement announcement,
			Boolean active) {
		super();
		this.id = id;
		this.url = url;
		this.description = description;
		this.post = post;
		this.message = message;
		this.announcement = announcement;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
