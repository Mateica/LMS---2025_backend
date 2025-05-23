package main.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class RegisteredUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	@OneToMany(mappedBy = "registeredUser")
	private List<ForumUser> forumUser = new ArrayList<ForumUser>();
	
	@OneToMany(mappedBy = "registeredUser")
	private List<Account> accounts = new ArrayList<Account>(); 
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	
	@Column(nullable = false)
	private Boolean active;
	
	public RegisteredUser() {}

	

	public RegisteredUser(Long id, String username, String password, String email, List<ForumUser> forumUser,
			List<Account> accounts, Set<Role> roles, Boolean active) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.forumUser = forumUser;
		this.accounts = accounts;
		this.roles = roles;
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


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}



	public List<Account> getAccounts() {
		return accounts;
	}



	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
}
