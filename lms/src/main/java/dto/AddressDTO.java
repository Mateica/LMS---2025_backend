package main.dto;

import main.model.Place;

public class AddressDTO {
	
	private Long id;
	private String street;
	private int houseNumber;
	private Place place;
	private Boolean active;
	
	public AddressDTO() {
		super();		
	}
	
	public AddressDTO(Long id, String street, int houseNumber, Place place, Boolean active) {
		super();
		this.id = id;
		this.street = street;
		this.houseNumber = houseNumber;
		this.place = place;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}