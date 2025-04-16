package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class TeachingMaterial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "teachingMaterial")
	@Column(nullable = false)
	private List<Teacher> authors = new ArrayList<Teacher>();
	
	@Column(nullable = false)
	private LocalDateTime yearOfPublication;
	
	@OneToOne
	private File file;
	
	@Column(nullable = false)
	private Boolean active;

	public TeachingMaterial() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeachingMaterial(Long id, String name, List<Teacher> authors, LocalDateTime yearOfPublication, File file,
			Boolean active) {
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

	public void setNaziv(String name) {
		this.name = name;
	}

	public List<Teacher> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Teacher> authors) {
		this.authors = authors;
	}

	public LocalDateTime getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(LocalDateTime yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
