package main.dto;

public class StudyProgrammeDTO {
	private Long id;
	private String name;
	private FacultyDTO faculty;
	private YearOfStudyDTO yearOfStudy;
	private TeacherDTO teacher;
	private DepartmentDTO department;
	private Boolean active;

	public StudyProgrammeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudyProgrammeDTO(Long id, String name, FacultyDTO faculty, YearOfStudyDTO yearOfStudy, TeacherDTO teacher,
			DepartmentDTO department, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.faculty = faculty;
		this.yearOfStudy = yearOfStudy;
		this.teacher = teacher;
		this.department = department;
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

	public FacultyDTO getFaculty() {
		return faculty;
	}

	public void setFaculty(FacultyDTO faculty) {
		this.faculty = faculty;
	}

	public YearOfStudyDTO getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(YearOfStudyDTO yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
	}

	public DepartmentDTO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDTO department) {
		this.department = department;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}