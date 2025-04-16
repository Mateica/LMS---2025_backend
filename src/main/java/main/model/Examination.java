package main.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Examination {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private double numberOfPoints;
	
	@OneToMany(mappedBy = "examination")
	private List<Note> notes = new ArrayList<Note>();
	
	@ManyToOne
	private StudentOnYear studentOnYear;
	
	@Column(nullable = false)
	private Boolean active;

	public Examination() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Examination(Long id, double numberOfPoints, List<Note> notes, StudentOnYear studentOnYear, Boolean active) {
		super();
		this.id = id;
		this.numberOfPoints = numberOfPoints;
		this.notes = notes;
		this.studentOnYear = studentOnYear;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getNumberOfPoints() {
		return numberOfPoints;
	}

	public void setNumberOfPoints(double numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public StudentOnYear getStudentOnYear() {
		return studentOnYear;
	}

	public void setStudentOnYear(StudentOnYear studentOnYear) {
		this.studentOnYear = studentOnYear;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}

