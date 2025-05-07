package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TeachingMaterial {
	private Long id;
	private String naziv;
	private ArrayList<String> authors; // Da li umesto stringova mogu stajati nastavnici?
	private LocalDateTime yearOfPublication;
	private File file;
}
