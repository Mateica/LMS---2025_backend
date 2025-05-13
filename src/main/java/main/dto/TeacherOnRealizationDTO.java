package main.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import main.model.Announcement;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeachingType;

public class TeacherOnRealizationDTO {
	private Long id;
	private Integer numberOfClasses;
	private TeacherDTO teacher;
	private SubjectRealizationDTO subjectRealization;
	private TeachingTypeDTO teachingType;
	private Boolean active;

	public TeacherOnRealizationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherOnRealizationDTO(Long id, Integer numberOfClasses, TeacherDTO teacher,
			SubjectRealizationDTO subjectRealization, TeachingTypeDTO teachingType, Boolean active) {
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

	public Integer getNumberOfClasses() {
		return numberOfClasses;
	}

	public void setNumberOfClasses(Integer numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
	}

	public SubjectRealizationDTO getSubjectRealization() {
		return subjectRealization;
	}

	public void setSubjectRealization(SubjectRealizationDTO subjectRealization) {
		this.subjectRealization = subjectRealization;
	}

	public TeachingTypeDTO getTeachingType() {
		return teachingType;
	}

	public void setTeachingType(TeachingTypeDTO teachingType) {
		this.teachingType = teachingType;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

	
}