package main.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public class Faculty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String name;

	@OneToOne
	private Address address;
	

	@OneToOne
	private Teacher headmaster;
		
	@ManyToOne
	private University university;
	
	@Lob
	@Column(nullable = false)
	private String contactDetails;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@OneToMany(mappedBy = "faculty")
	private Set<Department> departments = new HashSet<Department>();
	
	@OneToMany(mappedBy = "faculty")
	@Column(nullable = false)
	private List<StudyProgramme> studyProgrammes = new ArrayList<StudyProgramme>();
	
	@Column(nullable = false)
	private Boolean active;

	public Faculty() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Faculty(Long id, String name, Address address, Teacher headmaster, University university,
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
}
