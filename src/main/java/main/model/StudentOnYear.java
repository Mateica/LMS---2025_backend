package main.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class StudentOnYear {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime dateOfApplication;
	
	@OneToOne
	private Student student;
	
	@OneToOne
	private YearOfStudy yearOfStudy;
	
	@Column(nullable = false)
	private Boolean active;
	
	public StudentOnYear(Long id, LocalDateTime dateOfApplication, Student student) {
		super();
		this.id = id;
		this.dateOfApplication = dateOfApplication;
		this.student = student;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateOfApplication() {
		return dateOfApplication;
	}

	public void setDateOfApplication(LocalDateTime dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public YearOfStudy getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(YearOfStudy yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
