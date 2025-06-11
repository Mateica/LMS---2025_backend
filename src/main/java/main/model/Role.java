package main.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// POSETILAC, MODERATOR ILI ADMINISTRATOR
	@Lob
	@Column(nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private List<RegisteredUser> registeredUsers;
	
	@Column(nullable = false)
	private Boolean active;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(Long id, String name, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
	}

	public Role(Long id, String name, List<RegisteredUser> registeredUsers, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.registeredUsers = registeredUsers;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<RegisteredUser> getRegisteredUsers() {
		return registeredUsers;
	}

	public void setRegisteredUsers(List<RegisteredUser> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}
	
	public void addRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUsers.add(registeredUser);
    }

}
