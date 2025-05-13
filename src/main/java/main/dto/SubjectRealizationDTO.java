package main.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubjectRealizationDTO {

	private Long id;
	private List<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
	private Set<TeacherOnRealizationDTO> teacherOnRealization = new HashSet<TeacherOnRealizationDTO>();
	private SubjectDTO subject;
	private Boolean active;

	public SubjectRealizationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectRealizationDTO(Long id, List<EvaluationDTO> evaluations,
			Set<TeacherOnRealizationDTO> teacherOnRealization, SubjectDTO subject, Boolean active) {
		super();
		this.id = id;
		this.evaluations = evaluations;
		this.teacherOnRealization = teacherOnRealization;
		this.subject = subject;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<EvaluationDTO> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<EvaluationDTO> evaluations) {
		this.evaluations = evaluations;
	}

	public Set<TeacherOnRealizationDTO> getTeacherOnRealization() {
		return teacherOnRealization;
	}

	public void setTeacherOnRealization(Set<TeacherOnRealizationDTO> teacherOnRealization) {
		this.teacherOnRealization = teacherOnRealization;
	}

	public SubjectDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

	
	

}