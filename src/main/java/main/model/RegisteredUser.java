package main.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class RegisteredUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String username;
	
	@Lob
	@Column(nullable = false)
	private String password;
	
	@Lob
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	@OneToMany(mappedBy = "registeredUser")
	private List<ForumUser> forumUser = new ArrayList<ForumUser>();
	
	@Column(nullable = false)
	private Boolean active;
	
	public RegisteredUser() {}
	
	

	public RegisteredUser(Long id, String username, String password, String email, List<ForumUser> forumUser,
			Boolean active) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.forumUser = forumUser;
		this.active = active;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ForumUser> getForumUser() {
		return forumUser;
	}

	public void setForumUser(List<ForumUser> forumUser) {
		this.forumUser = forumUser;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
