package model;

import javax.persistence.Column;
import javax.persistence.OneToOne;

public class SubjectAttendance {
	private Long id;
	private int finalGrade;
	@Column
	@OneToOne
	private SubjectRealization subjectRealization;
}
