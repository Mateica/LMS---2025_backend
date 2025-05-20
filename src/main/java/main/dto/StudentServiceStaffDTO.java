package main.dto;

public class StudentServiceStaffDTO {
	private Long id;

	private RegisteredUserDTO registeredUser;
	
	private String firstName;
		
	private String lastName;
	
	private StudentAffairsOfficeDTO studentAffairsOffice;
	
	private Boolean active;

	public StudentServiceStaffDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentServiceStaffDTO(Long id, RegisteredUserDTO registeredUser, String firstName, String lastName,
			StudentAffairsOfficeDTO studentAffairsOffice, Boolean active) {
		super();
		this.id = id;
		this.registeredUser = registeredUser;
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentAffairsOffice = studentAffairsOffice;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUserDTO getRegisteredUser() {
		return registeredUser;
	}

	public void setRegisteredUser(RegisteredUserDTO registeredUser) {
		this.registeredUser = registeredUser;
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

	public StudentAffairsOfficeDTO getStudentAffairsOffice() {
		return studentAffairsOffice;
	}

	public void setStudentAffairsOffice(StudentAffairsOfficeDTO studentAffairsOffice) {
		this.studentAffairsOffice = studentAffairsOffice;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}