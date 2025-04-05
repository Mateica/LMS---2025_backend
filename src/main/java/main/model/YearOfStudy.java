package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class YearOfStudy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime yearOfStudy;
	
	@OneToMany(mappedBy = "yearOfStudy")
	@Column(nullable = false)
	private List<Subject> subjects = new ArrayList<Subject>();
	
	@Column(nullable = false)
	private Boolean active;

	public YearOfStudy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public YearOfStudy(Long id, LocalDateTime yearOfStudy, List<Subject> subjects, Boolean active) {
		super();
		this.id = id;
		this.yearOfStudy = yearOfStudy;
		this.subjects = subjects;
		this.active = active;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(LocalDateTime yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
