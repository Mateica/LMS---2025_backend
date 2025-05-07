package model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class Announcement {
	private Long id;
	private LocalDateTime datePublished;
	private String content;
	
	@OneToOne
	@Column(nullable = true)
	private ForumUser author;
	
	@OneToMany
	@Column(nullable = false)
	private File attachments;
}
