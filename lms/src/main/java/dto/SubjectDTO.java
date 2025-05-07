package main.dto;

import main.model.Outcome;
import main.model.Subject;
import main.model.YearOfStudy;

public class SubjectDTO {

	private Long id;
	private String name;
	private int ects;
	private boolean compulsory;
	private int numberOfClasses;
	private int numberOfPractices;
	private int otherTypesOfClasses;
	private int researchWork;
	private int classesLeft;
	private int numberOfSemesters;
	private YearOfStudy yearOfStudy;
	private Outcome outcome;
	private Subject prerequisite;
	private Boolean active;

	public SubjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectDTO(Long id, String name, int ects, boolean compulsory, int numberOfClasses, int numberOfPractices,
			int otherTypesOfClasses, int researchWork, int classesLeft, int numberOfSemesters, YearOfStudy yearOfStudy,
			Outcome outcome, Subject prerequisite, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.ects = ects;
		this.compulsory = compulsory;
		this.numberOfClasses = numberOfClasses;
		this.numberOfPractices = numberOfPractices;
		this.otherTypesOfClasses = otherTypesOfClasses;
		this.researchWork = researchWork;
		this.classesLeft = classesLeft;
		this.numberOfSemesters = numberOfSemesters;
		this.yearOfStudy = yearOfStudy;
		this.outcome = outcome;
		this.prerequisite = prerequisite;
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

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		this.ects = ects;
	}

	public boolean isCompulsory() {
		return compulsory;
	}

	public void setCompulsory(boolean compulsory) {
		this.compulsory = compulsory;
	}

	public int getNumberOfClasses() {
		return numberOfClasses;
	}

	public void setNumberOfClasses(int numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}

	public int getNumberOfPractices() {
		return numberOfPractices;
	}

	public void setNumberOfPractices(int numberOfPractices) {
		this.numberOfPractices = numberOfPractices;
	}

	public int getOtherTypesOfClasses() {
		return otherTypesOfClasses;
	}

	public void setOtherTypesOfClasses(int otherTypesOfClasses) {
		this.otherTypesOfClasses = otherTypesOfClasses;
	}

	public int getResearchWork() {
		return researchWork;
	}

	public void setResearchWork(int researchWork) {
		this.researchWork = researchWork;
	}

	public int getClassesLeft() {
		return classesLeft;
	}

	public void setClassesLeft(int classesLeft) {
		this.classesLeft = classesLeft;
	}

	public int getNumberOfSemesters() {
		return numberOfSemesters;
	}

	public void setNumberOfSemesters(int numberOfSemesters) {
		this.numberOfSemesters = numberOfSemesters;
	}

	public YearOfStudy getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(YearOfStudy yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public Outcome getOutcome() {
		return outcome;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}

	public Subject getPrerequisite() {
		return prerequisite;
	}

	public void setPrerequisite(Subject prerequisite) {
		this.prerequisite = prerequisite;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}