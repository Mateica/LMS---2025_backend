package main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Outcome {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@OneToOne
	private EducationGoal educationGoal;
	
	@OneToOne
	private TeachingMaterial teachingMaterial;
	
	@ManyToOne
	private Subject subject;
	
	@Column(nullable = false)
	private Boolean active;
	
	public Outcome() {}

	public Outcome(Long id, String description, EducationGoal educationGoal, TeachingMaterial teachingMaterial,
			Subject subject, Boolean active) {
		super();
		this.id = id;
		this.description = description;
		this.educationGoal = educationGoal;
		this.teachingMaterial = teachingMaterial;
		this.subject = subject;
		this.active = active;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EducationGoal getEducationGoal() {
		return educationGoal;
	}

	public void setEducationGoal(EducationGoal educationGoal) {
		this.educationGoal = educationGoal;
	}

	public TeachingMaterial getTeachingMaterial() {
		return teachingMaterial;
	}

	public void setTeachingMaterial(TeachingMaterial teachingMaterial) {
		this.teachingMaterial = teachingMaterial;
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
