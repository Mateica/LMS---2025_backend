package main.dto;

import main.model.File;

public class EvaluationInstrumentDTO {

	private Long id;
	private File file;
	private Boolean active;

	public EvaluationInstrumentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EvaluationInstrumentDTO(Long id, File file, Boolean active) {
		super();
		this.id = id;
		this.file = file;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}