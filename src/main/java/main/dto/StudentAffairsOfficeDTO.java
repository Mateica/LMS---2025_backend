package main.dto;

import java.util.ArrayList;
import java.util.List;

public class StudentAffairsOfficeDTO {
	private Long id;

	private List<StudentServiceStaffDTO> staff = new ArrayList<StudentServiceStaffDTO>();

//	private List<StudentOnYearDTO> studentsOnYear = new ArrayList<StudentOnYearDTO>();
	
	private FacultyDTO faculty;
	
	private Boolean active;
	
	public StudentAffairsOfficeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentAffairsOfficeDTO(Long id, List<StudentServiceStaffDTO> staff, FacultyDTO faculty, Boolean active) {
		super();
		this.id = id;
		this.staff = staff;
		this.faculty = faculty;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<StudentServiceStaffDTO> getStaff() {
		return staff;
	}

	public void setStaff(List<StudentServiceStaffDTO> staff) {
		this.staff = staff;
	}

	public FacultyDTO getFaculty() {
		return faculty;
	}

	public void setFaculty(FacultyDTO faculty) {
		this.faculty = faculty;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
