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
	private AddressDTO address;
	private TeacherDTO headmaster;
	private UniversityDTO university;
	private String contactDetails;
	private String description;
	private Set<DepartmentDTO> departments = new HashSet<DepartmentDTO>();
	private List<StudyProgrammeDTO> studyProgrammes = new ArrayList<StudyProgrammeDTO>();
	private StudentAffairsOfficeDTO studentAffairsOffice;
	private Boolean active;
	
	public FacultyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FacultyDTO(Long id, String name, AddressDTO address, TeacherDTO headmaster, UniversityDTO university,
			String contactDetails, String description, Set<DepartmentDTO> departments,
			List<StudyProgrammeDTO> studyProgrammes, StudentAffairsOfficeDTO studentAffairsOffice, Boolean active) {
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
		this.studentAffairsOffice = studentAffairsOffice;
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

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public TeacherDTO getHeadmaster() {
		return headmaster;
	}

	public void setHeadmaster(TeacherDTO headmaster) {
		this.headmaster = headmaster;
	}

	public UniversityDTO getUniversity() {
		return university;
	}

	public void setUniversity(UniversityDTO university) {
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

	public Set<DepartmentDTO> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<DepartmentDTO> departments) {
		this.departments = departments;
	}

	public List<StudyProgrammeDTO> getStudyProgrammes() {
		return studyProgrammes;
	}

	public void setStudyProgrammes(List<StudyProgrammeDTO> studyProgrammes) {
		this.studyProgrammes = studyProgrammes;
	}

	public StudentAffairsOfficeDTO getStudentAffairsOffice() {
		return studentAffairsOffice;
	}

	public void setStudentAffairsOffice(StudentAffairsOfficeDTO studentAffairsOffice) {
		this.studentAffairsOffice = studentAffairsOffice;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}