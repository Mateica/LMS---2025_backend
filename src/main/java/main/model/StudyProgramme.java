package main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class StudyProgramme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String name;
	
	@ManyToOne
	private Faculty faculty;
	
	@OneToOne
	private YearOfStudy yearOfStudy;
	
	@OneToOne
	private Teacher teacher;
	
	@Column(nullable = false)
	private Boolean active;

	public StudyProgramme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudyProgramme(Long id, String name, Faculty faculty, YearOfStudy yearOfStudy, Teacher teacher,
			Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.faculty = faculty;
		this.yearOfStudy = yearOfStudy;
		this.teacher = teacher;
		this.active = active;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public YearOfStudy getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(YearOfStudy yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
