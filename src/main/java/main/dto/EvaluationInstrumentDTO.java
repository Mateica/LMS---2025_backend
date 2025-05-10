package main.dto;

import main.model.File;

public class EvaluationInstrumentDTO {

	private Long id;
	private String name;
	private FileDTO file;
	private Boolean active;

	public EvaluationInstrumentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EvaluationInstrumentDTO(Long id, String name, FileDTO file, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.file = file;
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

	public FileDTO getFile() {
		return file;
	}

	public void setFile(FileDTO file) {
		this.file = file;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}