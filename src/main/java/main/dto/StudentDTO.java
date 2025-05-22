package main.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private AddressDTO address;
	private Set<StudentOnYearDTO> studentsOnYear = new HashSet<StudentOnYearDTO>();
	private List<SubjectAttendanceDTO> subjectAttendances = new ArrayList<SubjectAttendanceDTO>();
	private FacultyDTO faculty;
	private Boolean active;
	
	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public StudentDTO(Long id, RegisteredUserDTO user, String firstName, String lastName, String umcn,
			AddressDTO address, Set<StudentOnYearDTO> studentsOnYear, List<SubjectAttendanceDTO> subjectAttendances,
			FacultyDTO faculty, Boolean active) {
		super();
		this.id = id;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.umcn = umcn;
		this.address = address;
		this.studentsOnYear = studentsOnYear;
		this.subjectAttendances = subjectAttendances;
		this.faculty = faculty;
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


	public AddressDTO getAddress() {
		return address;
	}


	public void setAddress(AddressDTO address) {
		this.address = address;
	}


	public Set<StudentOnYearDTO> getStudentsOnYear() {
		return studentsOnYear;
	}


	public void setStudentsOnYear(Set<StudentOnYearDTO> studentsOnYear) {
		this.studentsOnYear = studentsOnYear;
	}


	public List<SubjectAttendanceDTO> getSubjectAttendances() {
		return subjectAttendances;
	}


	public void setSubjectAttendances(List<SubjectAttendanceDTO> subjectAttendances) {
		this.subjectAttendances = subjectAttendances;
	}


	public FacultyDTO getFaculty() {
		return faculty;
	}


	public void setFaculty(FacultyDTO faculty) {
		this.faculty = faculty;
	}


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}
}