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
	
	private EvaluationTypeDTO evaluationType;
	private EvaluationInstrumentDTO evaluationInstrument;
	private ExaminationDTO examination;
	
	private SubjectRealizationDTO subjectRealization;
	private Boolean active;

	public EvaluationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EvaluationDTO(Long id, LocalDateTime startTime, LocalDateTime endTime, int numberOfPoints,
			EvaluationTypeDTO evaluationType, EvaluationInstrumentDTO evaluationInstrument, ExaminationDTO examination,
			SubjectRealizationDTO subjectRealization, Boolean active) {
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

	public EvaluationTypeDTO getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(EvaluationTypeDTO evaluationType) {
		this.evaluationType = evaluationType;
	}

	public EvaluationInstrumentDTO getEvaluationInstrument() {
		return evaluationInstrument;
	}

	public void setEvaluationInstrument(EvaluationInstrumentDTO evaluationInstrument) {
		this.evaluationInstrument = evaluationInstrument;
	}

	public ExaminationDTO getExamination() {
		return examination;
	}

	public void setExamination(ExaminationDTO examination) {
		this.examination = examination;
	}

	public SubjectRealizationDTO getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(SubjectRealizationDTO subjectRealization) {
		this.subjectRealization = subjectRealization;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}