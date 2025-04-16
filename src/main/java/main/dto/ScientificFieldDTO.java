package main.dto;

public class ScientificFieldDTO {
	private Long id;
	private String name;
	private TeacherDTO teacher;
	private Boolean active;
	
	public ScientificFieldDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScientificFieldDTO(Long id, String name, TeacherDTO teacher, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.teacher = teacher;
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

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
