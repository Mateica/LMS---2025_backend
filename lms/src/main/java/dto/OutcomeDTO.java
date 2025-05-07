package main.dto;

import main.model.EducationGoal;
import main.model.Subject;
import main.model.TeachingMaterial;

public class OutcomeDTO {

	private Long id;
	private String description;
	private EducationGoal educationGoal;
	private TeachingMaterial teachingMaterial;
	private Subject subject;
	private Boolean active;

	public OutcomeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OutcomeDTO(Long id, String description, EducationGoal educationGoal, TeachingMaterial teachingMaterial,
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