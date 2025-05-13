package main.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import main.model.EvaluationInstrument;
import main.model.EvaluationType;
import main.model.Examination;
import main.model.SubjectRealization;

public class EvaluationDTO {
	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int numberOfPoints;
	
	private EvaluationType evaluationType;
	private EvaluationInstrument evaluationInstrument;
	private Examination examination;
	
	private SubjectRealization subjectRealization;
	private Boolean active;

	public EvaluationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EvaluationDTO(Long id, LocalDateTime startTime, LocalDateTime endTime, int numberOfPoints,
			EvaluationType evaluationType, EvaluationInstrument evaluationInstrument, Examination examination,
			SubjectRealization subjectRealization, Boolean active) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.numberOfPoints = numberOfPoints;
		this.evaluationType = evaluationType;
		this.evaluationInstrument = evaluationInstrument;
		this.examination = examination;
		this.subjectRealization = subjectRealization;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public int getNumberOfPoints() {
		return numberOfPoints;
	}

	public void setNumberOfPoints(int numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}

	public EvaluationType getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(EvaluationType evaluationType) {
		this.evaluationType = evaluationType;
	}

	public EvaluationInstrument getEvaluationInstrument() {
		return evaluationInstrument;
	}

	public void setEvaluationInstrument(EvaluationInstrument evaluationInstrument) {
		this.evaluationInstrument = evaluationInstrument;
	}

	public Examination getExamination() {
		return examination;
	}

	public void setExamination(Examination examination) {
		this.examination = examination;
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