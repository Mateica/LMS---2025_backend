package main.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.model.Forum;
import main.model.ForumPost;
import main.model.ForumRole;
import main.model.RegisteredUser;
import main.model.Topic;

public class ForumUserDTO {

	private Long id;
	private RegisteredUserDTO registeredUser;
	private List<TopicDTO> topics = new ArrayList<TopicDTO>();
	private ForumDTO forum;
	private Set<ForumRoleDTO> forumRoles = new HashSet<ForumRoleDTO>();
	private List<ForumPostDTO> posts = new ArrayList<ForumPostDTO>();
	private Boolean active;

	public ForumUserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumUserDTO(Long id, RegisteredUserDTO registeredUser, List<TopicDTO> topics, ForumDTO forum,
			Set<ForumRoleDTO> forumRoles, List<ForumPostDTO> posts, Boolean active) {
		super();
		this.id = id;
		this.registeredUser = registeredUser;
		this.topics = topics;
		this.forum = forum;
		this.forumRoles = forumRoles;
		this.posts = posts;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUserDTO getRegisteredUser() {
		return registeredUser;
	}

	public void setRegisteredUser(RegisteredUserDTO registeredUser) {
		this.registeredUser = registeredUser;
	}

	public List<TopicDTO> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicDTO> topics) {
		this.topics = topics;
	}

	public ForumDTO getForum() {
		return forum;
	}

	public void setForum(ForumDTO forum) {
		this.forum = forum;
	}

	public Set<ForumRoleDTO> getForumRoles() {
		return forumRoles;
	}

	public void setForumRoles(Set<ForumRoleDTO> forumRoles) {
		this.forumRoles = forumRoles;
	}

	public List<ForumPostDTO> getPosts() {
		return posts;
	}

	public void setPosts(List<ForumPostDTO> posts) {
		this.posts = posts;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}