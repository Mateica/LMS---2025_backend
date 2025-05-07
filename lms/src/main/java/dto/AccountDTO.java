package main.dto;

public class AccountDTO {
	private Long id;
	private String username;
	private String password;
	private String email;
	private RegisteredUserDTO registeredUser;
	private Boolean active;
	
	public AccountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountDTO(Long id, String username, String password, String email, RegisteredUserDTO registeredUser,
			Boolean active) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.registeredUser = registeredUser;
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

	public RegisteredUserDTO getRegisteredUser() {
		return registeredUser;
	}

	public void setRegisteredUser(RegisteredUserDTO registeredUser) {
		this.registeredUser = registeredUser;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
