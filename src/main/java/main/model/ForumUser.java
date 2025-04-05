package main.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


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
	
	@OneToOne
	private Role role;
	
	@Column(nullable = true)
	@OneToMany(mappedBy = "author")
	private List<Announcement> announcements = new ArrayList<Announcement>();
	
	@Column(nullable = false)
	private Boolean active;

	public ForumUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumUser(Long id, RegisteredUser registeredUser, List<Topic> topics, Forum forum, Role role,
			List<Announcement> announcements, Boolean active) {
		super();
		this.id = id;
		this.registeredUser = registeredUser;
		this.topics = topics;
		this.forum = forum;
		this.role = role;
		this.announcements = announcements;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
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
	
	
}
