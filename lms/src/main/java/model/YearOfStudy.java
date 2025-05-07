package model;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.OneToMany;


public class YearOfStudy {
	private Long id;
	private LocalDateTime yearOfStudy;
	@OneToMany
	@Column(nullable = false)
	private Subject subject;
}
