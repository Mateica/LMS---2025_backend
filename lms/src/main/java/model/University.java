package model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class University {
	private Long id;
	private String name;
	private LocalDate dateEstablished;
	
	@OneToOne
	@Column(nullable = false)
	private Address address;
	
	@OneToOne
	@Column(nullable = false)
	private Teacher rector;
	
	@OneToMany
	@Column(nullable = false)
	private Faculty faculty;
	
	
}
