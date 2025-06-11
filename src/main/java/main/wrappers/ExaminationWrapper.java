package main.wrappers;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import main.model.Examination;

@XmlRootElement(name = "examinations")
public class ExaminationWrapper {
	List<Examination> examsinations = new ArrayList<Examination>();
	
	@XmlElement(name = "examination")
	public List<Examination> getExamsinations() {
		return examsinations;
	}

	public void setExamsinations(List<Examination> examsinations) {
		this.examsinations = examsinations;
	}
	
	
}
