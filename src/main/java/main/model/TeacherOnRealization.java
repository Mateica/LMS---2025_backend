package main.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class TeacherOnRealization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int numberOfClasses;
	
	@ManyToOne
	private Teacher teacher;
	
	@ManyToOne
	private SubjectRealization subjectRealization;
	
	@ManyToOne
	private TeachingType teachingType;
	
	@Column(nullable = false)
	private Boolean active;

	public TeacherOnRealization() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherOnRealization(Long id, int numberOfClasses, Teacher teacher, SubjectRealization subjectRealization,
			TeachingType teachingType, Boolean active) {
		super();
		this.id = id;
		this.numberOfClasses = numberOfClasses;
		this.teacher = teacher;
		this.subjectRealization = subjectRealization;
		this.teachingType = teachingType;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumberOfClasses() {
		return numberOfClasses;
	}

	public void setNumberOfClasses(int numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public SubjectRealization getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(SubjectRealization subjectRealization) {
		this.subjectRealization = subjectRealization;
	}

	public TeachingType getTeachingType() {
		return teachingType;
	}

	public void setTeachingType(TeachingType teachingType) {
		this.teachingType = teachingType;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}
