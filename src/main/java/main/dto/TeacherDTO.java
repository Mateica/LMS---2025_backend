package main.dto;

import java.util.ArrayList;
import java.util.List;

import javax.swing.border.TitledBorder;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import main.model.Department;
import main.model.RegisteredUser;
import main.model.TeachingMaterial;
import main.model.Title;

public class TeacherDTO {
	private Long id;

	private RegisteredUserDTO user;
	
	private String firstName;
	
	private String lastName;
	
	private String umcn;
	
	private String biography;
	
	private List<TitleDTO> titles = new ArrayList<TitleDTO>();
	
	private TeachingMaterialDTO teachingMaterial;

	private DepartmentDTO department;
	
	private Boolean active;
	
	public TeacherDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherDTO(Long id, RegisteredUserDTO user, String firstName, String lastName, String umcn, String biography,
			List<TitleDTO> titles, TeachingMaterialDTO teachingMaterial, DepartmentDTO department, Boolean active) {
		super();
		this.id = id;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.umcn = umcn;
		this.biography = biography;
		this.titles = titles;
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

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public List<TitleDTO> getTitles() {
		return titles;
	}

	public void setTitles(List<TitleDTO> titles) {
		this.titles = titles;
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