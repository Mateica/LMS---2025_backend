package main.dto;

public class PlaceDTO {
	private Long id;
	private String name;
	private CountryDTO country;
	private Boolean active;
	
	public PlaceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlaceDTO(Long id, String name, CountryDTO country, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
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

	public CountryDTO getCountry() {
		return country;
	}

	public void setCountry(CountryDTO country) {
		this.country = country;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
