package main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

@Entity
public class EducationGoal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@OneToOne
	private Outcome outcome;
	
	@Column(nullable = false)
	private Boolean active;

	public EducationGoal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EducationGoal(Long id, String description, Outcome outcome, Boolean active) {
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

	public Outcome getOutcome() {
		return outcome;
	}

	public void setAftermath(Outcome outcome) {
		this.outcome = outcome;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}
	
	
	
	
}
