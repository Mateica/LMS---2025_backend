package main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.model.Examination;
import main.model.Student;
import main.model.YearOfStudy;

public class StudentOnYearDTO {

	private Long id;
	private LocalDateTime dateOfApplication;
	private StudentDTO student;
	private String indexNumber;
	private YearOfStudyDTO yearOfStudy;
	private List<ExaminationDTO> examinations = new ArrayList<ExaminationDTO>();
	private Boolean active;

	public StudentOnYearDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentOnYearDTO(Long id, LocalDateTime dateOfApplication, StudentDTO student, String indexNumber,
			YearOfStudyDTO yearOfStudy, List<ExaminationDTO> examinations, Boolean active) {
		super();
		this.id = id;
		this.dateOfApplication = dateOfApplication;
		this.student = student;
		this.indexNumber = indexNumber;
		this.yearOfStudy = yearOfStudy;
		this.examinations = examinations;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateOfApplication() {
		return dateOfApplication;
	}

	public void setDateOfApplication(LocalDateTime dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public String getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(String indexNumber) {
		this.indexNumber = indexNumber;
	}

	public YearOfStudyDTO getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(YearOfStudyDTO yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public List<ExaminationDTO> getExaminations() {
		return examinations;
	}

	public void setExaminations(List<ExaminationDTO> examinations) {
		this.examinations = examinations;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
	
}