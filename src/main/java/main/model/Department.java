package main.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@ManyToOne
	private Faculty faculty;
	
	@OneToMany(mappedBy = "department")
	private Set<Teacher> staff = new HashSet<Teacher>();
	
	@OneToOne
	private Teacher chief;
	
	@OneToMany(mappedBy = "department")
	private Set<StudyProgramme> studyProgrammes = new HashSet<StudyProgramme>();
	
	@Column(nullable = false)
	private Boolean active;

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(Long id, String name, String description, Faculty faculty, Set<Teacher> staff, Teacher chief,
			Set<StudyProgramme> studyProgrammes, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.faculty = faculty;
		this.staff = staff;
		this.chief = chief;
		this.studyProgrammes = studyProgrammes;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Teacher> getStaff() {
		return staff;
	}

	public void setStaff(Set<Teacher> staff) {
		this.staff = staff;
	}

	public Teacher getChief() {
		return chief;
	}

	public void setChief(Teacher chief) {
		this.chief = chief;
	}

	public Set<StudyProgramme> getStudyProgrammes() {
		return studyProgrammes;
	}

	public void setStudyProgrammes(Set<StudyProgramme> studyProgrammes) {
		this.studyProgrammes = studyProgrammes;
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
