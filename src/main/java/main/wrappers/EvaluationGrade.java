package main.wrappers;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "evaluationGrades")
public class EvaluationGrade {
	List<EvaluationGrade> evaluationGrades = new ArrayList<EvaluationGrade>();
	
	@XmlElement(name = "evaluationGrade")
	public List<EvaluationGrade> getEvaluationGrades() {
		return evaluationGrades;
	}

	public void setEvaluationGrades(List<EvaluationGrade> evaluationGrades) {
		this.evaluationGrades = evaluationGrades;
	}
	
}
