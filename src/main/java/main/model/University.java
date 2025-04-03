package main.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class University {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private LocalDate dateEstablished;
	
	@OneToOne
	@Column(nullable = false)
	private Address address;
	
	@OneToOne
	@Column(nullable = false)
	private Teacher rector;
	
	@Lob
	@Column(nullable = false)
	private String contactDetails;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@OneToMany(mappedBy = "university")
	@Column(nullable = false)
	private List<Faculty> faculties = new ArrayList<Faculty>();
	
	@Column(nullable = false)
	private Boolean active;

	public University() {
		super();
		// TODO Auto-generated constructor stub
	}

	public University(Long id, String name, LocalDate dateEstablished, Address address, Teacher rector,
			String contactDetails, String description, List<Faculty> faculties, Boolean active) {
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Teacher getRector() {
		return rector;
	}

	public void setRector(Teacher rector) {
		this.rector = rector;
	}

	public List<Faculty> getFaculties() {
		return faculties;
	}

	public void setFaculties(List<Faculty> faculties) {
		this.faculties = faculties;
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
	
	
}
