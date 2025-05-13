package main.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import main.model.Address;
import main.model.RegisteredUser;
import main.model.StudentOnYear;
import main.model.SubjectAttendance;

public class StudentDTO {
	private Long id;
	private RegisteredUserDTO user;
	private String firstName;
	private String lastName;
	private String umcn;
	private String indexNumber;
	private AddressDTO address;
	private StudentOnYearDTO studentOnYear;
	private List<SubjectAttendanceDTO> subjectAttendances = new ArrayList<SubjectAttendance>();
	private Boolean active;
	
	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentDTO(Long id, RegisteredUserDTO user, String firstName, String lastName, String umcn,
			String indexNumber, AddressDTO address, StudentOnYearDTO studentOnYear,
			List<SubjectAttendanceDTO> subjectAttendances, Boolean active) {
		super();
		this.id = id;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.umcn = umcn;
		this.indexNumber = indexNumber;
		this.address = address;
		this.studentOnYear = studentOnYear;
		this.subjectAttendances = subjectAttendances;
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

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public StudentOnYearDTO getStudentOnYear() {
		return studentOnYear;
	}

	public void setStudentOnYear(StudentOnYearDTO studentOnYear) {
		this.studentOnYear = studentOnYear;
	}

	public List<SubjectAttendanceDTO> getSubjectAttendances() {
		return subjectAttendances;
	}

	public void setSubjectAttendances(List<SubjectAttendanceDTO> subjectAttendances) {
		this.subjectAttendances = subjectAttendances;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

}