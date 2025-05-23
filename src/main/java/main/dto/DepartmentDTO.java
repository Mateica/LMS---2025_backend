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
	private FacultyDTO faculty;
	private Set<TeacherDTO> staff = new HashSet<TeacherDTO>();
	private TeacherDTO chief;
	private Set<StudyProgrammeDTO> studyProgrammes = new HashSet<StudyProgrammeDTO>();
	private Boolean active;

	public DepartmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepartmentDTO(Long id, String name, String description, FacultyDTO faculty, Set<TeacherDTO> staff,
			TeacherDTO chief, Set<StudyProgrammeDTO> studyProgrammes, Boolean active) {
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

	public FacultyDTO getFaculty() {
		return faculty;
	}

	public void setFaculty(FacultyDTO faculty) {
		this.faculty = faculty;
	}

	public Set<TeacherDTO> getStaff() {
		return staff;
	}

	public void setStaff(Set<TeacherDTO> staff) {
		this.staff = staff;
	}

	public TeacherDTO getChief() {
		return chief;
	}

	public void setChief(TeacherDTO chief) {
		this.chief = chief;
	}

	public Set<StudyProgrammeDTO> getStudyProgrammes() {
		return studyProgrammes;
	}

	public void setStudyProgrammes(Set<StudyProgrammeDTO> studyProgrammes) {
		this.studyProgrammes = studyProgrammes;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	

	}