package main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Examination {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private double numberOfPoints;
	
	@Lob
	@Column(nullable = false)
	private String note;
	
	@Column(nullable = false)
	private Boolean active;

	public Examination() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Examination(Long id, double numberOfPoints, String note, Boolean active) {
		super();
		this.id = id;
		this.numberOfPoints = numberOfPoints;
		this.note = note;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}

