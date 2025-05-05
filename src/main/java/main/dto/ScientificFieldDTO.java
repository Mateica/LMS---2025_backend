package main.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import main.model.Title;

public class ScientificFieldDTO {
	private Long id;
	
	private String name;
	
	private List<TitleDTO> titles = new ArrayList<TitleDTO>();
	
	private Boolean active;

	public ScientificFieldDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScientificFieldDTO(Long id, String name, List<TitleDTO> titles, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.titles = titles;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TitleDTO> getTitles() {
		return titles;
	}

	public void setTitles(List<TitleDTO> titles) {
		this.titles = titles;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
