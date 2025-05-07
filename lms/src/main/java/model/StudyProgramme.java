package model;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class StudyProgramme {
	private Long id;
	private String name;
	
	@ManyToOne
	@Column(nullable = false)
	private Faculty faculty;
	
	@OneToOne
	@Column(nullable = false)
	private YearOfStudy yearOfStudy;
	
	@OneToOne
	@Column(nullable = false)
	private Teacher teacher;
	
	
	
}
