package main.wrappers;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import main.model.Teacher;

@XmlRootElement(name = "teachers")
public class TeacherWrapper {
	List<Teacher> teachers = new ArrayList<Teacher>();
	
	@XmlElement(name = "teacher")
	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	
	
}
