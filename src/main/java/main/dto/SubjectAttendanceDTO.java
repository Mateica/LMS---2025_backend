package main.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import main.model.Student;
import main.model.SubjectRealization;

public class SubjectAttendanceDTO {
	private Long id;
	private int finalGrade;
	private SubjectRealizationDTO subjectRealization;
	private StudentDTO student; 
	private Boolean active;

	public SubjectAttendanceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectAttendanceDTO(Long id, int finalGrade, SubjectRealizationDTO subjectRealization, StudentDTO student,
			Boolean active) {
		super();
		this.id = id;
		this.finalGrade = finalGrade;
		this.subjectRealization = subjectRealization;
		this.student = student;
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

	public SubjectRealizationDTO getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(SubjectRealizationDTO subjectRealization) {
		this.subjectRealization = subjectRealization;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

}