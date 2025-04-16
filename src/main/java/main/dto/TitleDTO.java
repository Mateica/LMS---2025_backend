package main.dto;

import java.util.Date;

public class TitleDTO {
	private Long id;
	private Date dateElected;
	private Date dateAbolished;
	private ScientificFieldDTO scientificField;
	private TitleTypeDTO titleType;
	private Boolean active;
	
	public TitleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TitleDTO(Long id, Date dateElected, Date dateAbolished, ScientificFieldDTO scientificField,
			TitleTypeDTO titleType, Boolean active) {
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
	public ScientificFieldDTO getScientificField() {
		return scientificField;
	}
	public void setScientificField(ScientificFieldDTO scientificField) {
		this.scientificField = scientificField;
	}
	public TitleTypeDTO getTitleType() {
		return titleType;
	}
	public void setTitleType(TitleTypeDTO titleType) {
		this.titleType = titleType;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	
	
}
