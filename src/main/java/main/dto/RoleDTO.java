package main.dto;

import java.util.List;

import jakarta.persistence.Column;
import main.model.RegisteredUser;

public class RoleDTO {
	private Long id;
	
	// POSETILAC, MODERATOR ILI ADMINISTRATOR
	private String name;
	private RegisteredUser registeredUser;
	private Boolean active;
	
	public RoleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoleDTO(Long id, String name, Boolean active) {
		super();
		this.id = id;
		this.name = name;
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

	public RegisteredUser getRegisteredUser() {
		return registeredUser;
	}

	public void setRegisteredUser(RegisteredUser registeredUser) {
		this.registeredUser = registeredUser;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
	
	
	
}	
