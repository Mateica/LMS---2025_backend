package main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class ForumRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// POSETILAC, MODERATOR ILI ADMINISTRATOR
	@Lob
	@Column(nullable = false)
	private String name;
	
	@ManyToOne
	private ForumUser forumUser;
	
	@Column(nullable = false)
	private Boolean active;

	public ForumRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumRole(Long id, String name, ForumUser forumUser, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.forumUser = forumUser;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public ForumUser getForumUser() {
		return forumUser;
	}

	public void setForumUser(ForumUser forumUser) {
		this.forumUser = forumUser;
	}
}
