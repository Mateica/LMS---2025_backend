package main.dto;

import java.time.LocalDateTime;

public class EvaluationGradeDTO {
	private Long id;

	private EvaluationDTO evaluation;
	
	private TeacherDTO teacher;
	
	private LocalDateTime dateTimeEvaluated;

	private Boolean active;

	public EvaluationGradeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EvaluationGradeDTO(Long id, EvaluationDTO evaluation, TeacherDTO teacher, LocalDateTime dateTimeEvaluated,
			Boolean active) {
		super();
		this.id = id;
		this.evaluation = evaluation;
		this.teacher = teacher;
		this.dateTimeEvaluated = dateTimeEvaluated;
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

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
	}

	public LocalDateTime getDateTimeEvaluated() {
		return dateTimeEvaluated;
	}

	public void setDateTimeEvaluated(LocalDateTime dateTimeEvaluated) {
		this.dateTimeEvaluated = dateTimeEvaluated;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
