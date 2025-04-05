package main.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Forum {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private boolean visible;
	
	@OneToMany(mappedBy = "forum")
	@Column(nullable = false)
	private List<Topic> topics = new ArrayList<Topic>();
	
	@OneToMany(mappedBy = "forum")
	@Column(nullable = false)
	private List<ForumUser> forumUsers = new ArrayList<ForumUser>();
	
	@Column(nullable = false)
	private Boolean active;

	public Forum() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Forum(Long id, boolean visible, List<Topic> topics, List<ForumUser> forumUsers, Boolean active) {
		super();
		this.id = id;
		this.visible = visible;
		this.topics = topics;
		this.forumUsers = forumUsers;
		this.active = active;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopic(List<Topic> topics) {
		this.topics = topics;
	}

	public List<ForumUser> getForumUsers() {
		return forumUsers;
	}

	public void setForumUsers(List<ForumUser> forumUsers) {
		this.forumUsers = forumUsers;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	
	
	
}
