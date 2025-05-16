package main.dto;

import java.util.ArrayList;
import java.util.List;

import main.model.Outcome;
import main.model.Subject;
import main.model.SubjectRealization;
import main.model.YearOfStudy;

public class SubjectDTO {

	private Long id;
	private String name;
	private Integer ects;
	private boolean compulsory;
	private Integer numberOfClasses;
	private Integer numberOfPractices;
	private Integer otherTypesOfClasses;
	private Integer researchWork;
	private Integer classesLeft;
	private Integer numberOfSemesters;
	private YearOfStudyDTO yearOfStudy;
	private List<OutcomeDTO> syllabi = new ArrayList<OutcomeDTO>();
	private List<SubjectRealizationDTO> subjectRealizations = new ArrayList<SubjectRealizationDTO>();
	private SubjectDTO prerequisite;
	private Boolean active;

	public SubjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectDTO(Long id, String name, Integer ects, boolean compulsory, Integer numberOfClasses,
			Integer numberOfPractices, Integer otherTypesOfClasses, Integer researchWork, Integer classesLeft,
			Integer numberOfSemesters, YearOfStudyDTO yearOfStudy, List<OutcomeDTO> syllabi,
			List<SubjectRealizationDTO> subjectRealizations, SubjectDTO prerequisite, Boolean active) {
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
		this.syllabi = syllabi;
		this.subjectRealizations = subjectRealizations;
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

	public Integer getEcts() {
		return ects;
	}

	public void setEcts(Integer ects) {
		this.ects = ects;
	}

	public boolean isCompulsory() {
		return compulsory;
	}

	public void setCompulsory(boolean compulsory) {
		this.compulsory = compulsory;
	}

	public Integer getNumberOfClasses() {
		return numberOfClasses;
	}

	public void setNumberOfClasses(Integer numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}

	public Integer getNumberOfPractices() {
		return numberOfPractices;
	}

	public void setNumberOfPractices(Integer numberOfPractices) {
		this.numberOfPractices = numberOfPractices;
	}

	public Integer getOtherTypesOfClasses() {
		return otherTypesOfClasses;
	}

	public void setOtherTypesOfClasses(Integer otherTypesOfClasses) {
		this.otherTypesOfClasses = otherTypesOfClasses;
	}

	public Integer getResearchWork() {
		return researchWork;
	}

	public void setResearchWork(Integer researchWork) {
		this.researchWork = researchWork;
	}

	public Integer getClassesLeft() {
		return classesLeft;
	}

	public void setClassesLeft(Integer classesLeft) {
		this.classesLeft = classesLeft;
	}

	public Integer getNumberOfSemesters() {
		return numberOfSemesters;
	}

	public void setNumberOfSemesters(Integer numberOfSemesters) {
		this.numberOfSemesters = numberOfSemesters;
	}

	public YearOfStudyDTO getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(YearOfStudyDTO yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public List<OutcomeDTO> getSyllabi() {
		return syllabi;
	}

	public void setSyllabi(List<OutcomeDTO> syllabi) {
		this.syllabi = syllabi;
	}

	public List<SubjectRealizationDTO> getSubjectRealizations() {
		return subjectRealizations;
	}

	public void setSubjectRealizations(List<SubjectRealizationDTO> subjectRealizations) {
		this.subjectRealizations = subjectRealizations;
	}

	public SubjectDTO getPrerequisite() {
		return prerequisite;
	}

	public void setPrerequisite(SubjectDTO prerequisite) {
		this.prerequisite = prerequisite;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
	
}