package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.swing.border.TitledBorder;

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
	@Column
	private Title title;
	
	@OneToMany
	@Column(nullable = false)
	private ScientificField scientificField; // Jedan nastavnik moze imati samo jedno zvanjeu jednoj oblasti, ali moze biti biran u vise oblasti
	
	public Teacher() {
		super();
	}

	public Teacher(RegisteredUser user, String firstName, String lastName, String umcn) {
		super();
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.umcn = umcn;
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
	
	
}

