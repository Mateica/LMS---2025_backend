package main.dto;

import main.model.SubjectRealization;

public class SubjectAttendanceDTO {

	private Long id;
	private int finalGrade;
	private SubjectRealization subjectRealization;
	private Boolean active;

	public SubjectAttendanceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectAttendanceDTO(Long id, int finalGrade, SubjectRealization subjectRealization, Boolean active) {
		super();
		this.id = id;
		this.finalGrade = finalGrade;
		this.subjectRealization = subjectRealization;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
	}

	public SubjectRealization getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(SubjectRealization subjectRealization) {
		this.subjectRealization = subjectRealization;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}