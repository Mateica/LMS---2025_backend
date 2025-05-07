package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.OneToOne;

public class Title {
	private Long id;
	private Date dateElected;
	private Date dateAbolished;
	private ScientificField scientificField;
	private TitleType titleType; // Moze li ovde stajati samo string?
}
