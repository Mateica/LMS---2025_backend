package main.dto;

import main.model.Evaluation;
import main.model.Subject;
import main.model.TeacherOnRealization;

public class SubjectRealizationDTO {

	private Long id;
	private EvaluationDTO evaluation;
	private TeacherOnRealizationDTO teacherOnRealization;
	private SubjectDTO subject;
	private Boolean active;

	public SubjectRealizationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectRealizationDTO(Long id, EvaluationDTO evaluation, TeacherOnRealizationDTO teacherOnRealization,
			SubjectDTO subject, Boolean active) {
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

	public EvaluationDTO getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(EvaluationDTO evaluation) {
		this.evaluation = evaluation;
	}

	public TeacherOnRealizationDTO getTeacherOnRealization() {
		return teacherOnRealization;
	}

	public void setTeacherOnRealization(TeacherOnRealizationDTO teacherOnRealization) {
		this.teacherOnRealization = teacherOnRealization;
	}

	public SubjectDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}