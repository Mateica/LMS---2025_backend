package main.dto;

import main.model.RegisteredUser;

public class AdministratorDTO {

	private Long id;
	private RegisteredUserDTO user;
	private Boolean active;

	public AdministratorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdministratorDTO(Long id, RegisteredUserDTO user, Boolean active) {
		super();
		this.id = id;
		this.user = user;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUserDTO getUser() {
		return user;
	}

	public void setUser(RegisteredUserDTO user) {
		this.user = user;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}