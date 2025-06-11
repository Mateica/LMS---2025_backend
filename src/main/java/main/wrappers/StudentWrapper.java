package main.wrappers;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import main.model.Student;

@XmlRootElement(name = "students")
public class StudentWrapper {
	private List<Student> students = new ArrayList<Student>();

	@XmlElement(name = "student")
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
}
