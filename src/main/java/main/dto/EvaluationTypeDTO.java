package main.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.el.lang.EvaluationContext;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import main.model.Evaluation;

public class EvaluationTypeDTO {
	private Long id;
	private String name;
	private List<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
	private Boolean active;

	public EvaluationTypeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EvaluationTypeDTO(Long id, String name, List<EvaluationDTO> evaluations, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.evaluations = evaluations;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EvaluationDTO> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<EvaluationDTO> evaluations) {
		this.evaluations = evaluations;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}
