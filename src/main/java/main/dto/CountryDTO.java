package main.dto;

import java.util.ArrayList;
import java.util.List;

public class CountryDTO {
	private Long id;
	private String name;
	private List<PlaceDTO> places = new ArrayList<PlaceDTO>();
	private Boolean active;
	
	
	public CountryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CountryDTO(Long id, String name, List<PlaceDTO> places, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.places = places;
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


	public List<PlaceDTO> getPlaces() {
		return places;
	}


	public void setPlaces(List<PlaceDTO> places) {
		this.places = places;
	}


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
