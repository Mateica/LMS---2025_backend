package main.dto;

import main.model.Announcement;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeachingType;

public class TeacherOnRealizationDTO {

	private Long id;
	private int numberOfClasses;
	private TeacherDTO teacher;
	private SubjectRealizationDTO subjectRealization;
	private AnnouncementDTO announcement;
	private TeachingTypeDTO teachingType;
	private Boolean active;

	public TeacherOnRealizationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public TeacherOnRealizationDTO(Long id, int numberOfClasses, TeacherDTO teacher,
			SubjectRealizationDTO subjectRealization, AnnouncementDTO announcement, TeachingTypeDTO teachingType,
			Boolean active) {
		super();
		this.id = id;
		this.numberOfClasses = numberOfClasses;
		this.teacher = teacher;
		this.subjectRealization = subjectRealization;
		this.announcement = announcement;
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



	public AnnouncementDTO getAnnouncement() {
		return announcement;
	}



	public void setAnnouncement(AnnouncementDTO announcement) {
		this.announcement = announcement;
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