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
	private RegisteredUser registeredUser;
	private List<Topic> topics = new ArrayList<Topic>();
	private Forum forum;
	private Set<ForumRole> forumRoles = new HashSet<ForumRole>();
	private List<ForumPost> posts = new ArrayList<ForumPost>();
	private Boolean active;

	public ForumUserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumUserDTO(Long id, RegisteredUser registeredUser, List<Topic> topics, Forum forum,
			Set<ForumRole> forumRoles, List<ForumPost> posts, Boolean active) {
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

	public RegisteredUser getRegisteredUser() {
		return registeredUser;
	}

	public void setRegisteredUser(RegisteredUser registeredUser) {
		this.registeredUser = registeredUser;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public Set<ForumRole> getForumRoles() {
		return forumRoles;
	}

	public void setForumRoles(Set<ForumRole> forumRoles) {
		this.forumRoles = forumRoles;
	}

	public List<ForumPost> getPosts() {
		return posts;
	}

	public void setPosts(List<ForumPost> posts) {
		this.posts = posts;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}