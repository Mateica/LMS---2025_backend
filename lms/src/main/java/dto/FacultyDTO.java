package main.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.model.Address;
import main.model.Department;
import main.model.StudyProgramme;
import main.model.Teacher;
import main.model.University;

public class FacultyDTO {

	private Long id;
	private String name;
	private Address address;
	private Teacher headmaster;
	private University university;
	private String contactDetails;
	private String description;
	private Set<Department> departments = new HashSet<Department>();
	private List<StudyProgramme> studyProgrammes = new ArrayList<StudyProgramme>();
	private Boolean active;

	public FacultyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FacultyDTO(Long id, String name, Address address, Teacher headmaster, University university,
			String contactDetails, String description, Set<Department> departments,
			List<StudyProgramme> studyProgrammes, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.headmaster = headmaster;
		this.university = university;
		this.contactDetails = contactDetails;
		this.description = description;
		this.departments = departments;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Teacher getHeadmaster() {
		return headmaster;
	}

	public void setHeadmaster(Teacher headmaster) {
		this.headmaster = headmaster;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	public List<StudyProgramme> getStudyProgrammes() {
		return studyProgrammes;
	}

	public void setStudyProgrammes(List<StudyProgramme> studyProgrammes) {
		this.studyProgrammes = studyProgrammes;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}