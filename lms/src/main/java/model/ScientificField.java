package model;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

public class ScientificField {
	private Long id;
	private String name;
	
	@ManyToOne
	@Column(nullable = false)
	private Teacher teacher; // Jedan nastavnik moze imati samo jedno zvanje u jednoj oblasti, ali moze biti biran u vise oblasti
}	
