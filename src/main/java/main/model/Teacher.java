package main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private RegisteredUser user;
	
	@Lob
	@Column(nullable = false)
	private String firstName;
	
	@Lob
	@Column(nullable = false)
	private String lastName;
	
	@Lob
	@Column(nullable = false)
	private String umcn;
	
	@OneToOne
	@Column(nullable = false)
	private Title title;
	
	@OneToMany(mappedBy = "teacher")
	private ScientificField scientificField; // Jedan nastavnik moze imati samo jedno zvanjeu jednoj oblasti, ali moze biti biran u vise oblasti
	
	@Column(nullable = false)
	private Boolean active;
	
	public Teacher() {
		super();
	}

	public Teacher(Long id, RegisteredUser user, String firstName, String lastName, String umcn, Title title,
			ScientificField scientificField, Boolean active) {
		super();
		this.id = id;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.umcn = umcn;
		this.title = title;
		this.scientificField = scientificField;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
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

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public ScientificField getScientificField() {
		return scientificField;
	}

	public void setScientificField(ScientificField scientificField) {
		this.scientificField = scientificField;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}

