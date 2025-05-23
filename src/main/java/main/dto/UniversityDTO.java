package main.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import main.model.Address;
import main.model.Faculty;
import main.model.Teacher;

public class UniversityDTO {

	private Long id;
	private String name;
	private LocalDate dateEstablished;
	private AddressDTO address;
	private TeacherDTO rector;
	private String contactDetails;
	private String description;
	private List<FacultyDTO> faculties = new ArrayList<FacultyDTO>();
	private Boolean active;

	public UniversityDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UniversityDTO(Long id, String name, LocalDate dateEstablished, AddressDTO address, TeacherDTO rector,
			String contactDetails, String description, List<FacultyDTO> faculties, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.dateEstablished = dateEstablished;
		this.address = address;
		this.rector = rector;
		this.contactDetails = contactDetails;
		this.description = description;
		this.faculties = faculties;
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

	public LocalDate getDateEstablished() {
		return dateEstablished;
	}

	public void setDateEstablished(LocalDate dateEstablished) {
		this.dateEstablished = dateEstablished;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public TeacherDTO getRector() {
		return rector;
	}

	public void setRector(TeacherDTO rector) {
		this.rector = rector;
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

	public List<FacultyDTO> getFaculties() {
		return faculties;
	}

	public void setFaculties(List<FacultyDTO> faculties) {
		this.faculties = faculties;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}