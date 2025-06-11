package main.wrappers;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import main.model.Evaluation;

@XmlRootElement(name = "evaluations")
public class EvaluationWrapper {	
	List<Evaluation> evaluations = new ArrayList<Evaluation>();
	
	@XmlElement(name = "evaluation")
	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	
}
