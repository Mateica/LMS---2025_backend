package main.dto;

public class TeacherDTO {
	private Long id;
	private RegisteredUserDTO user;
	private String firstName;
	private String lastName;
	private String umcn;
	private TitleDTO title;
	private ScientificFieldDTO scientificField;
	private TeachingMaterialDTO teachingMaterial;
	private DepartmentDTO department;
	private Boolean active;
	
	public TeacherDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherDTO(Long id, RegisteredUserDTO user, String firstName, String lastName, String umcn, TitleDTO title,
			ScientificFieldDTO scientificField, TeachingMaterialDTO teachingMaterial, DepartmentDTO department,
			Boolean active) {
		super();
		this.id = id;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.umcn = umcn;
		this.title = title;
		this.scientificField = scientificField;
		this.teachingMaterial = teachingMaterial;
		this.department = department;
		this.active = active;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUserDTO getUser() {
		return user;
	}

	public void setUser(RegisteredUserDTO user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUmcn() {
		return umcn;
	}

	public void setUmcn(String umcn) {
		this.umcn = umcn;
	}

	public TitleDTO getTitle() {
		return title;
	}

	public void setTitle(TitleDTO title) {
		this.title = title;
	}

	public ScientificFieldDTO getScientificField() {
		return scientificField;
	}

	public void setScientificField(ScientificFieldDTO scientificField) {
		this.scientificField = scientificField;
	}

	public TeachingMaterialDTO getTeachingMaterial() {
		return teachingMaterial;
	}

	public void setTeachingMaterial(TeachingMaterialDTO teachingMaterial) {
		this.teachingMaterial = teachingMaterial;
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
