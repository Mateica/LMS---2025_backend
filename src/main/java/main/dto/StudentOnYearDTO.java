package main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.model.Examination;
import main.model.Student;
import main.model.YearOfStudy;

public class StudentOnYearDTO {

	private Long id;
	private LocalDateTime dateOfApplication;
	private Student student;
	private YearOfStudy yearOfStudy;
	private List<Examination> examinations = new ArrayList<Examination>();
	private Boolean active;

	public StudentOnYearDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentOnYearDTO(Long id, LocalDateTime dateOfApplication, Student student, YearOfStudy yearOfStudy,
			List<Examination> examinations, Boolean active) {
		super();
		this.id = id;
		this.dateOfApplication = dateOfApplication;
		this.student = student;
		this.yearOfStudy = yearOfStudy;
		this.examinations = examinations;
		this.active = active;
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

	public List<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}