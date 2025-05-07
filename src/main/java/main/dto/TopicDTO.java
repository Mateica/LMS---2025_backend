package main.dto;

import java.util.ArrayList;
import java.util.List;
import main.model.Forum;
import main.model.ForumPost;
import main.model.ForumUser;

public class TopicDTO {

	private Long id;
	private String name;
	private ForumUser author;
	private List<ForumPost> posts = new ArrayList<ForumPost>();
	private Forum forum;
	private Boolean active;

	public TopicDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TopicDTO(Long id, String name, ForumUser author, List<ForumPost> posts, Forum forum, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.posts = posts;
		this.forum = forum;
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

	public ForumUser getAuthor() {
		return author;
	}

	public void setAuthor(ForumUser author) {
		this.author = author;
	}

	public List<ForumPost> getPosts() {
		return posts;
	}

	public void setPosts(List<ForumPost> posts) {
		this.posts = posts;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}