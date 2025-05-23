package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Evaluation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime startTime;
	
	@Column(nullable = false)
	private LocalDateTime endTime;
	
	@Column(nullable = false)
	private int numberOfPoints;
	
	@ManyToOne
	private EvaluationType evaluationType;
	
	@ManyToOne
	private EvaluationInstrument evaluationInstrument;
	
	@ManyToOne
	private Examination examination;
	
	@ManyToOne
	private SubjectRealization subjectRealization;
	
	@OneToMany(mappedBy = "evaluation")
	private List<EvaluationGrade> evaluationGrades = new ArrayList<EvaluationGrade>();
	
	@Column(nullable = false)
	private Boolean active;

	public Evaluation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Evaluation(Long id, LocalDateTime startTime, LocalDateTime endTime, int numberOfPoints,
			EvaluationType evaluationType, EvaluationInstrument evaluationInstrument, Examination examination,
			SubjectRealization subjectRealization, List<EvaluationGrade> evaluationGrades, Boolean active) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.numberOfPoints = numberOfPoints;
		this.evaluationType = evaluationType;
		this.evaluationInstrument = evaluationInstrument;
		this.examination = examination;
		this.subjectRealization = subjectRealization;
		this.evaluationGrades = evaluationGrades;
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

	public List<EvaluationGrade> getEvaluationGrades() {
		return evaluationGrades;
	}

	public void setEvaluationGrades(List<EvaluationGrade> evaluationGrades) {
		this.evaluationGrades = evaluationGrades;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

		
}
