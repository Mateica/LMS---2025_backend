package main.model;

import java.util.ArrayList;
import java.util.List;

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
	
	@Lob
	@Column(nullable = false)
	private String firstName;
	
	@Lob
	@Column(nullable = false)
	private String lastName;
	
	@Lob
	@Column(nullable = false)
	private String umcn;
	
	@Lob
	@Column(nullable = false)
	private String indexNumber;
	
	@OneToOne
	private Address address;
	
	
	@OneToOne
	private StudentOnYear studentOnYear;
	
	@OneToMany(mappedBy = "student")
	private List<SubjectAttendance> subjectAttendances = new ArrayList<SubjectAttendance>();
	
	@Column(nullable = false)
	private Boolean active;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(Long id, RegisteredUser user, String firstName, String lastName, String umcn, String indexNumber,
			Address address, StudentOnYear studentOnYear, List<SubjectAttendance> subjectAttendances, Boolean active) {
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

	public List<SubjectAttendance> getSubjectAttendances() {
		return subjectAttendances;
	}

	public void setSubjectAttendances(List<SubjectAttendance> subjectAttendances) {
		this.subjectAttendances = subjectAttendances;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}
