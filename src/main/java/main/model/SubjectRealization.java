package main.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public class SubjectRealization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "subjectRealization")
	private List<Evaluation> evaluations = new ArrayList<Evaluation>();
	
	@OneToMany(mappedBy = "subjectRealization")
	private Set<TeacherOnRealization> teachersOnRealization = new HashSet<TeacherOnRealization>();
	
	@OneToMany(mappedBy = "announcement")
	private List<Announcement> announcements = new ArrayList<Announcement>();
	
	@ManyToOne
	private Subject subject;
	
	@Column(nullable = false)
	private Boolean active;

	public SubjectRealization() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectRealization(Long id, List<Evaluation> evaluations, Set<TeacherOnRealization> teachersOnRealization,
			List<Announcement> announcements, Subject subject, Boolean active) {
		super();
		this.id = id;
		this.evaluations = evaluations;
		this.teachersOnRealization = teachersOnRealization;
		this.announcements = announcements;
		this.subject = subject;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Set<TeacherOnRealization> getTeachersOnRealization() {
		return teachersOnRealization;
	}

	public void setTeachersOnRealization(Set<TeacherOnRealization> teachersOnRealization) {
		this.teachersOnRealization = teachersOnRealization;
	}

	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
