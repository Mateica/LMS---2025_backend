package main.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private RegisteredUser user;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false, length = 13)
	private String umcn;
	
	@OneToOne
	private Address address;
	
	
	@OneToMany(mappedBy = "student")
	private Set<StudentOnYear> studentsOnYear = new HashSet<StudentOnYear>();
	
	@OneToMany(mappedBy = "student")
	private List<SubjectAttendance> subjectAttendances = new ArrayList<SubjectAttendance>();
	
	@OneToOne
	private Faculty faculty;
	
	@Column(nullable = false)
	private Boolean active;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(Long id, RegisteredUser user, String firstName, String lastName, String umcn, Address address,
			Set<StudentOnYear> studentsOnYear, List<SubjectAttendance> subjectAttendances, Faculty faculty,
			Boolean active) {
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<StudentOnYear> getStudentsOnYear() {
		return studentsOnYear;
	}

	public void setStudentsOnYear(Set<StudentOnYear> studentsOnYear) {
		this.studentsOnYear = studentsOnYear;
	}

	public List<SubjectAttendance> getSubjectAttendances() {
		return subjectAttendances;
	}

	public void setSubjectAttendances(List<SubjectAttendance> subjectAttendances) {
		this.subjectAttendances = subjectAttendances;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
