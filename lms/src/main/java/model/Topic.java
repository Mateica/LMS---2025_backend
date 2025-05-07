package model;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class Topic {
	private Long id;
	private String name;
	
	@Column(nullable = false)
	@OneToOne
	private ForumUser author;
	
	@OneToMany
	@Column(nullable = false)
	private Announcement announcement;
}
