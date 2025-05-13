package main.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class EvaluationInstrument {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "evaluationInstrument")
	private List<Evaluation> evaluations = new ArrayList<Evaluation>();
	
	@OneToOne
	private File file;
	
	@Column(nullable = false)
	private Boolean active;

	public EvaluationInstrument() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EvaluationInstrument(Long id, String name, List<Evaluation> evaluations, File file, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.evaluations = evaluations;
		this.file = file;
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

	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
