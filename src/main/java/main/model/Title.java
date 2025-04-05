package main.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Title {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Date dateElected;
	
	@Column(nullable = false)
	private Date dateAbolished;
	
	@OneToOne
	private ScientificField scientificField;
	
	@OneToOne
	private TitleType titleType;
	
	@Column(nullable = false)
	private Boolean active;

	public Title() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Title(Long id, Date dateElected, Date dateAbolished, ScientificField scientificField, TitleType titleType,
			Boolean active) {
		super();
		this.id = id;
		this.dateElected = dateElected;
		this.dateAbolished = dateAbolished;
		this.scientificField = scientificField;
		this.titleType = titleType;
		this.active = active;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateElected() {
		return dateElected;
	}

	public void setDateElected(Date dateElected) {
		this.dateElected = dateElected;
	}

	public Date getDateAbolished() {
		return dateAbolished;
	}

	public void setDateAbolished(Date dateAbolished) {
		this.dateAbolished = dateAbolished;
	}

	public ScientificField getScientificField() {
		return scientificField;
	}

	public void setScientificField(ScientificField scientificField) {
		this.scientificField = scientificField;
	}

	public TitleType getTitleType() {
		return titleType;
	}

	public void setTitleType(TitleType titleType) {
		this.titleType = titleType;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	
}
