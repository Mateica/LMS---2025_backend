package main.dto;

import java.time.LocalDateTime;
import main.model.Outcome;
import main.model.SubjectRealization;
import main.model.TeachingType;

public class ClassTermDTO {

	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Outcome outcome;
	private TeachingType teachingType;
	private SubjectRealization subjectRealization;
	private Boolean active;

	public ClassTermDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassTermDTO(Long id, LocalDateTime startTime, LocalDateTime endTime, Outcome outcome,
			TeachingType teachingType, SubjectRealization subjectRealization, Boolean active) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.outcome = outcome;
		this.teachingType = teachingType;
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

	public Outcome getOutcome() {
		return outcome;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}

	public TeachingType getTeachingType() {
		return teachingType;
	}

	public void setTeachingType(TeachingType teachingType) {
		this.teachingType = teachingType;
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