package main.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class EvaluationGrade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Evaluation evaluation;
	
	@ManyToOne
	private Teacher teacher;
	
	@Column(nullable = false)
	private LocalDateTime dateTimeEvaluated;
	
	@Column(nullable = false)
	private Integer mark;

	@Column(nullable = false)
	private Boolean active;

	public EvaluationGrade() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EvaluationGrade(Long id, Evaluation evaluation, Teacher teacher, LocalDateTime dateTimeEvaluated,
			Integer mark, Boolean active) {
		super();
		this.id = id;
		this.evaluation = evaluation;
		this.teacher = teacher;
		this.dateTimeEvaluated = dateTimeEvaluated;
		this.mark = mark;
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

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public LocalDateTime getDateTimeEvaluated() {
		return dateTimeEvaluated;
	}

	public void setDateTimeEvaluated(LocalDateTime dateTimeEvaluated) {
		this.dateTimeEvaluated = dateTimeEvaluated;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
