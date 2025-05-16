package main.dto;

import main.model.Outcome;

public class EducationGoalDTO {

	private Long id;
	private String description;
	private OutcomeDTO outcome;
	private Boolean active;

	public EducationGoalDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EducationGoalDTO(Long id, String description, OutcomeDTO outcome, Boolean active) {
		super();
		this.id = id;
		this.description = description;
		this.outcome = outcome;
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

	public OutcomeDTO getOutcome() {
		return outcome;
	}

	public void setOutcome(OutcomeDTO outcome) {
		this.outcome = outcome;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}