package main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.model.Subject;

public class YearOfStudyDTO {

	private Long id;
	private LocalDateTime yearOfStudy;
	private List<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
	private Boolean active;

	public YearOfStudyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public YearOfStudyDTO(Long id, LocalDateTime yearOfStudy, List<SubjectDTO> subjects, Boolean active) {
		super();
		this.id = id;
		this.yearOfStudy = yearOfStudy;
		this.subjects = subjects;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(LocalDateTime yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public List<SubjectDTO> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectDTO> subjects) {
		this.subjects = subjects;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

}