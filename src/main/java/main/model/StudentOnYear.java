package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "studentOnYear")
	private List<Examination> examinations = new ArrayList<Examination>();
	
	@Column(nullable = false)
	private Boolean active;
	
	
	public StudentOnYear(Long id, LocalDateTime dateOfApplication, Student student, YearOfStudy yearOfStudy,
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
	}
	
}
