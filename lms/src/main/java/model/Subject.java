package model;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class Subject {
	private Long id;
	private String name;
	private int ects;
	private boolean compulsory;
	private int numberOfClasses;
	private int numberOfPractices;
	private int otherTypesOfClasses;
	private int researchWork;
	private int classesLeft;
	private int numberOfSemesters; // Da li ovo ima smisla?
	@ManyToOne
	@Column(nullable = false)
	private YearOfStudy yearOfStudy;
	
	@OneToOne
	@Column(nullable = false)
	private Aftermath aftermath;
	
	@OneToOne
	@Column(nullable = true)
	private Subject prerequisite;
	
	
	
}
