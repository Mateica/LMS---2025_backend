package main.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@JoinColumn(nullable = true)
	@ManyToOne
	private ForumUser author;
	
	@OneToMany(mappedBy = "topic")
	@Column(nullable = false)
	private List<Announcement> announcements = new ArrayList<Announcement>();
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Forum forum;
	
	@Column(nullable = false)
	private Boolean active;

	public Topic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Topic(Long id, String name, ForumUser author, List<Announcement> announcements, Forum forum,
			Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.announcements = announcements;
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

	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
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
