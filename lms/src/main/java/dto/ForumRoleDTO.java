package main.dto;

import main.model.ForumUser;

public class ForumRoleDTO {

	private Long id;
	// POSETILAC, MODERATOR ILI ADMINISTRATOR
	private String name;
	private ForumUser forumUser;
	private Boolean active;

	public ForumRoleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumRoleDTO(Long id, String name, ForumUser forumUser, Boolean active) {
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

	public ForumUser getForumUser() {
		return forumUser;
	}

	public void setForumUser(ForumUser forumUser) {
		this.forumUser = forumUser;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}