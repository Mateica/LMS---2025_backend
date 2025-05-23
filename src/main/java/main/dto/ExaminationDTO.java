package main.dto;

import java.util.ArrayList;
import java.util.List;
import main.model.Note;
import main.model.StudentOnYear;

public class ExaminationDTO {

	private Long id;
	private Double numberOfPoints;
	private List<NoteDTO> notes = new ArrayList<NoteDTO>();
	private StudentOnYearDTO studentOnYear;
	private Boolean active;

	public ExaminationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExaminationDTO(Long id, Double numberOfPoints, List<NoteDTO> notes, StudentOnYearDTO studentOnYear,
			Boolean active) {
		super();
		this.id = id;
		this.numberOfPoints = numberOfPoints;
		this.notes = notes;
		this.studentOnYear = studentOnYear;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getNumberOfPoints() {
		return numberOfPoints;
	}

	public void setNumberOfPoints(Double numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}

	public List<NoteDTO> getNotes() {
		return notes;
	}

	public void setNotes(List<NoteDTO> notes) {
		this.notes = notes;
	}

	public StudentOnYearDTO getStudentOnYear() {
		return studentOnYear;
	}

	public void setStudentOnYear(StudentOnYearDTO studentOnYear) {
		this.studentOnYear = studentOnYear;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

}