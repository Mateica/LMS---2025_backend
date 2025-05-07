package main.dto;

import main.model.Announcement;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeachingType;

public class TeacherOnRealizationDTO {

	private Long id;
	private int numberOfClasses;
	private Teacher teacher;
	private SubjectRealization subjectRealization;
	private Announcement announcement;
	private TeachingType teachingType;
	private Boolean active;

	public TeacherOnRealizationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherOnRealizationDTO(Long id, int numberOfClasses, Teacher teacher, SubjectRealization subjectRealization,
			Announcement announcement, TeachingType teachingType, Boolean active) {
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

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
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