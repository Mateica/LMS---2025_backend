package main.dto;

import main.model.Examination;

public class NoteDTO {

	private Long id;
	private String content;
	private ExaminationDTO examination;
	private Boolean active;

	public NoteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoteDTO(Long id, String content, ExaminationDTO examination, Boolean active) {
		super();
		this.id = id;
		this.content = content;
		this.examination = examination;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ExaminationDTO getExamination() {
		return examination;
	}

	public void setExamination(ExaminationDTO examination) {
		this.examination = examination;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

}