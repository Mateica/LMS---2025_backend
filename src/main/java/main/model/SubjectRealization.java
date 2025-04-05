package main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class SubjectRealization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Evaluation evaluation;
	
	@OneToOne
	private TeacherOnRealization teacherOnRealization;
	
	@OneToOne
	private Subject subject;
	
	@Column(nullable = false)
	private Boolean active;

	public SubjectRealization() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectRealization(Long id, Evaluation evaluation, TeacherOnRealization teacherOnRealization,
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
