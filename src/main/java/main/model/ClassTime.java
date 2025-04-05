package main.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class ClassTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime startTime;
	
	@Column(nullable = false)
	private LocalDateTime endTime;
	
	@OneToOne
	@Column(nullable = false)
	private Outcome aftermath;
	
	@OneToOne
	@Column(nullable = false)
	private TeachingType teachingType;
	
	@OneToOne
	@Column(nullable = false)
	private SubjectRealization subjectRealization;
	
	@Column(nullable = false)
	private Boolean active;
	
	public ClassTime() {}

	public ClassTime(Long id, LocalDateTime startTime, LocalDateTime endTime, Outcome aftermath,
			TeachingType teachingType, SubjectRealization subjectRealization, Boolean active) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.aftermath = aftermath;
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

	public Outcome getAftermath() {
		return aftermath;
	}

	public void setAftermath(Outcome aftermath) {
		this.aftermath = aftermath;
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
