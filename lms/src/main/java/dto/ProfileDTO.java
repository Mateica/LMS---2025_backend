package main.dto;

public class ProfileDTO {
	private String username;
	private String firstName;
	private String lastName;
	private String biography;
	private String umcn;
	
	public ProfileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ProfileDTO(String username, String firstName, String lastName, String biography, String umcn) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.biography = biography;
		this.umcn = umcn;
	}

	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public String getUmcn() {
		return umcn;
	}
	public void setUmcn(String umcn) {
		this.umcn = umcn;
	}
	
	
}
