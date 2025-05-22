package main.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class StudentAffairsOffice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "studentAffairsOffice")
	private List<StudentServiceStaff> staff = new ArrayList<StudentServiceStaff>();
	
	//@OneToMany(mappedBy = "studentAffairsOffice")
	//private List<StudentOnYear> studentsOnYear = new ArrayList<StudentOnYear>();
	
	@OneToOne
	private Faculty faculty;
	
	@Column(nullable = false)
	private Boolean active;

	public StudentAffairsOffice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentAffairsOffice(Long id, List<StudentServiceStaff> staff, Faculty faculty, Boolean active) {
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

	public List<StudentServiceStaff> getStaff() {
		return staff;
	}

	public void setStaff(List<StudentServiceStaff> staff) {
		this.staff = staff;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
