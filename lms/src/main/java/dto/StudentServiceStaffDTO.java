package main.dto;

import main.model.RegisteredUser;

public class StudentServiceStaffDTO {

	private Long id;
	private RegisteredUser registeredUser;

	public StudentServiceStaffDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentServiceStaffDTO(Long id, RegisteredUser registeredUser) {
		super();
		this.id = id;
		this.registeredUser = registeredUser;
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

}