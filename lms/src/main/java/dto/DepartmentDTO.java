package main.dto;

import java.util.HashSet;
import java.util.Set;
import main.model.Faculty;
import main.model.StudyProgramme;
import main.model.Teacher;

public class DepartmentDTO {

	private Long id;
	private String name;
	private String description;
	private Faculty faculty;
	private Set<Teacher> staff = new HashSet<Teacher>();
	private Teacher chief;
	private Set<StudyProgramme> studyProgrammes = new HashSet<StudyProgramme>();
	private Boolean active;

	public DepartmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepartmentDTO(Long id, String name, String description, Faculty faculty, Set<Teacher> staff, Teacher chief,
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

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}