package main.dto;

public class TitleTypeDTO {
	private Long id;
	private String name;
	private Boolean active;
	
	public TitleTypeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TitleTypeDTO(Long id, String name, Boolean active) {
		super();
		this.id = id;
		this.name = name;
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
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
