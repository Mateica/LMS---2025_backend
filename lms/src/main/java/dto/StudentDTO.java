package main.dto;

import main.model.Address;
import main.model.RegisteredUser;
import main.model.StudentOnYear;
import main.model.SubjectAttendance;

public class StudentDTO {

	private Long id;
	private RegisteredUser user;
	private String firstName;
	private String lastName;
	private String umcn;
	private String indexNumber;
	private Address address;
	private StudentOnYear studentOnYear;
	private SubjectAttendance subjectAttendance;
	private Boolean active;

	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentDTO(Long id, RegisteredUser user, String firstName, String lastName, String umcn, String indexNumber,
			Address address, StudentOnYear studentOnYear, SubjectAttendance subjectAttendance, Boolean active) {
		super();
		this.id = id;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.umcn = umcn;
		this.indexNumber = indexNumber;
		this.address = address;
		this.studentOnYear = studentOnYear;
		this.subjectAttendance = subjectAttendance;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
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

	public String getUmcn() {
		return umcn;
	}

	public void setUmcn(String umcn) {
		this.umcn = umcn;
	}

	public String getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(String indexNumber) {
		this.indexNumber = indexNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public StudentOnYear getStudentOnYear() {
		return studentOnYear;
	}

	public void setStudentOnYear(StudentOnYear studentOnYear) {
		this.studentOnYear = studentOnYear;
	}

	public SubjectAttendance getSubjectAttendance() {
		return subjectAttendance;
	}

	public void setSubjectAttendance(SubjectAttendance subjectAttendance) {
		this.subjectAttendance = subjectAttendance;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}