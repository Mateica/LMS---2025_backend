package model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class ForumUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@ManyToOne
	private RegisteredUser registeredUser;
	
	@Column(nullable = true)
	@OneToOne
	private Topic topic;
	
	@ManyToOne
	private Forum forum;
	
	private Role role;
	
	@Column(nullable = true)
	@OneToMany
	private Announcement announcement;
}
