package main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TeachingMaterialDTO {
	private Long id;
	private String name;
	private List<TeacherDTO> authors = new ArrayList<TeacherDTO>();
	private LocalDateTime yearOfPublication;
	private FileDTO file;
	private Boolean active;
	
	public TeachingMaterialDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeachingMaterialDTO(Long id, String name, List<TeacherDTO> authors, LocalDateTime yearOfPublication,
			FileDTO file, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.authors = authors;
		this.yearOfPublication = yearOfPublication;
		this.file = file;
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

	public List<TeacherDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<TeacherDTO> authors) {
		this.authors = authors;
	}

	public LocalDateTime getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(LocalDateTime yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

	public FileDTO getFile() {
		return file;
	}

	public void setFile(FileDTO file) {
		this.file = file;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
