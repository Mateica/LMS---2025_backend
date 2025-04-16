package main.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class ForumUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(nullable = false)
	@ManyToOne
	private RegisteredUser registeredUser;
	
	@Column(nullable = true)
	@OneToMany(mappedBy = "author")
	private List<Topic> topics = new ArrayList<Topic>();
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Forum forum;
	
	@OneToMany(mappedBy = "forumUser")
	private Set<ForumRole> forumRoles = new HashSet<ForumRole>();
	
	@Column(nullable = true)
	@OneToMany(mappedBy = "author")
	private List<ForumPost> posts = new ArrayList<ForumPost>();
	
	@Column(nullable = false)
	private Boolean active;

	public ForumUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ForumUser(Long id, RegisteredUser registeredUser, List<Topic> topics, Forum forum, Set<ForumRole> forumRoles,
			List<ForumPost> posts, Boolean active) {
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

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}


	public List<ForumPost> getAnnouncements() {
		return posts;
	}

	public void setAnnouncements(List<ForumPost> posts) {
		this.posts = posts;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<ForumRole> getForumRoles() {
		return forumRoles;
	}

	public void setForumRoles(Set<ForumRole> forumRoles) {
		this.forumRoles = forumRoles;
	}
	
	
}
