package main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class StudentServiceStaff {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private RegisteredUser registeredUser;
	
	@ManyToOne
	private StudentAffairsOffice studentAffairsOffice;
	
	@Column(nullable = false)
	private Boolean active;

	public StudentServiceStaff() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentServiceStaff(Long id, RegisteredUser registeredUser, StudentAffairsOffice studentAffairsOffice,
			Boolean active) {
		super();
		this.id = id;
		this.registeredUser = registeredUser;
		this.studentAffairsOffice = studentAffairsOffice;
		this.active = active;
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

	public StudentAffairsOffice getStudentAffairsOffice() {
		return studentAffairsOffice;
	}

	public void setStudentAffairsOffice(StudentAffairsOffice studentAffairsOffice) {
		this.studentAffairsOffice = studentAffairsOffice;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
	
}
