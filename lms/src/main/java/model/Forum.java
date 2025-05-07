package model;

import javax.persistence.Column;
import javax.persistence.OneToMany;

public class Forum {
	private Long id;
	private boolean visible;
	
	@OneToMany
	@Column(nullable = false)
	private Topic topic;
	
	@OneToMany
	@Column(nullable = false)
	private ForumUser forumUser;
	
	
	
}
