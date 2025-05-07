package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	
	@Column(nullable = false)
	@OneToOne
	private Address address;
	
	@Column(nullable = false)
	@OneToOne
	private StudentOnYear studentOnYear;
	
	@Column(nullable = false)
	@OneToMany(mappedBy = "student")
	private SubjectAttendance subjectAttendance;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(RegisteredUser user, String firstName, String lastName, String umcn) {
		super();
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.umcn = umcn;
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
	
	
	
	
	
}
