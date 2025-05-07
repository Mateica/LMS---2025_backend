package main.dto;

import main.model.Evaluation;
import main.model.Subject;
import main.model.TeacherOnRealization;

public class SubjectRealizationDTO {

	private Long id;
	private Evaluation evaluation;
	private TeacherOnRealization teacherOnRealization;
	private Subject subject;
	private Boolean active;

	public SubjectRealizationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectRealizationDTO(Long id, Evaluation evaluation, TeacherOnRealization teacherOnRealization,
			Subject subject, Boolean active) {
		super();
		this.id = id;
		this.evaluation = evaluation;
		this.teacherOnRealization = teacherOnRealization;
		this.subject = subject;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public TeacherOnRealization getTeacherOnRealization() {
		return teacherOnRealization;
	}

	public void setTeacherOnRealization(TeacherOnRealization teacherOnRealization) {
		this.teacherOnRealization = teacherOnRealization;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}