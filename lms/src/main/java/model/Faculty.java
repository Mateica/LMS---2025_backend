package model;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class Faculty {
	private Long id;
	private String name;
	private Address address;
	private Teacher headmaster;
	@ManyToOne
	@Column(nullable = false)
	private University university;
	
	@OneToMany
	@Column(nullable = false)
	private StudyProgramme studyProgramme;
}
