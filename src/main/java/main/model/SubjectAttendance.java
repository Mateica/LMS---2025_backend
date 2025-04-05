package main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class SubjectAttendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int finalGrade;
	
	@Column(nullable = false)
	@OneToOne
	private SubjectRealization subjectRealization;
	
	@Column(nullable = false)
	private Boolean active;

	public SubjectAttendance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectAttendance(Long id, int finalGrade, SubjectRealization subjectRealization, Boolean active) {
		super();
		this.id = id;
		this.finalGrade = finalGrade;
		this.subjectRealization = subjectRealization;
		this.active = active;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
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
