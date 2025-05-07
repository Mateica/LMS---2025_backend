package model;

import java.time.LocalDateTime;

public class StudentOnYear {
	private Long id;
	private LocalDateTime dateOfApplication;
	private Student student;
	private YearOfStudy yearOfStudy;
	
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
	
	
	
	
}
