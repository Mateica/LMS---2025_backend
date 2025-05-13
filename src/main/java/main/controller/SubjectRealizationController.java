package main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.DepartmentDTO;
import main.dto.EducationGoalDTO;
import main.dto.EvaluationDTO;
import main.dto.EvaluationInstrumentDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.ExaminationDTO;
import main.dto.OutcomeDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentDTO;
import main.dto.StudentOnYearDTO;
import main.dto.SubjectDTO;
import main.dto.SubjectRealizationDTO;
import main.dto.TeacherDTO;
import main.dto.TeacherOnRealizationDTO;
import main.dto.YearOfStudyDTO;
import main.model.Department;
import main.model.EducationGoal;
import main.model.Evaluation;
import main.model.EvaluationInstrument;
import main.model.EvaluationType;
import main.model.Examination;
import main.model.Outcome;
import main.model.RegisteredUser;
import main.model.Student;
import main.model.StudentOnYear;
import main.model.Subject;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.TeachingMaterial;
import main.model.YearOfStudy;
import main.service.SubjectRealizationService;
import main.service.SubjectService;

@RestController
@RequestMapping("/api/subjectRealizations")
public class SubjectRealizationController implements ControllerInterface<SubjectRealizationDTO> {
	@Autowired
	private SubjectRealizationService service;

	@Override
	@GetMapping("")
	@Secured({"ADMIN"})
	public ResponseEntity<Iterable<SubjectRealizationDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<SubjectRealizationDTO> subjectRealizations = new ArrayList<SubjectRealizationDTO>();
		
		for(SubjectRealization s : service.findAll()) {
			if(s.getSubject().getPrerequisite() != null) {
				subjectRealizations.add(new SubjectRealizationDTO(s.getId(),
						new EvaluationDTO(s.getEvaluation().getId(), 
			    				s.getEvaluation().getStartTime(), s.getEvaluation().getEndTime(),
			    				s.getEvaluation().getNumberOfPoints(),
			    				new EvaluationTypeDTO(s.getEvaluation().getEvaluationType().getId(),
			    						s.getEvaluation().getEvaluationType().getName(), s.getEvaluation().getEvaluationType().getActive()),
			    				new EvaluationInstrumentDTO(s.getEvaluation().getEvaluationInstrument().getId(),
			    						s.getEvaluation().getEvaluationInstrument().getName(),null, null),
			    				new ExaminationDTO(s.getEvaluation().getExamination().getId(),
			    						s.getEvaluation().getExamination().getNumberOfPoints(),
			    						null, new StudentOnYearDTO(s.getEvaluation().getExamination().getStudentOnYear().getId(),
			    								s.getEvaluation().getExamination().getStudentOnYear().getDateOfApplication(),
			    								new StudentDTO(s.getEvaluation().getExamination().getStudentOnYear().getStudent().getId(),
			    										null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getFirstName(),
			    										s.getEvaluation().getExamination().getStudentOnYear().getStudent().getLastName(),
			    										null, null, null, null, null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getActive()),
			    								null, null, s.getEvaluation().getExamination().getStudentOnYear().getActive()), 
			    						s.getEvaluation().getExamination().getActive()),
			    				s.getActive()),
						new TeacherOnRealizationDTO(s.getTeacherOnRealization().getId(), 
								s.getTeacherOnRealization().getNumberOfClasses(), 
								new TeacherDTO(s.getTeacherOnRealization().getTeacher().getId(),
										new RegisteredUserDTO(s.getTeacherOnRealization().getTeacher().getUser().getUsername(),
												null, s.getTeacherOnRealization().getTeacher().getUser().getEmail()),
										s.getTeacherOnRealization().getTeacher().getFirstName(),
										s.getTeacherOnRealization().getTeacher().getLastName(),
										s.getTeacherOnRealization().getTeacher().getUmcn(),
										s.getTeacherOnRealization().getTeacher().getBiography(), null, null,
										new DepartmentDTO(s.getTeacherOnRealization().getTeacher().getDepartment().getId(),
												s.getTeacherOnRealization().getTeacher().getDepartment().getName(),
												s.getTeacherOnRealization().getTeacher().getDepartment().getDescription(), null, null, null, null,
												s.getTeacherOnRealization().getTeacher().getDepartment().getActive()),
										s.getTeacherOnRealization().getTeacher().getActive()), null, null, null,
								s.getTeacherOnRealization().getActive()),
						new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(),s.getSubject().getEcts(), 
						s.getSubject().isCompulsory(), s.getSubject().getNumberOfClasses(), s.getSubject().getNumberOfPractices(),
						s.getSubject().getOtherTypesOfClasses(), s.getSubject().getResearchWork(),
						s.getSubject().getClassesLeft(), 
						s.getSubject().getNumberOfSemesters(),
						new YearOfStudyDTO(s.getSubject().getYearOfStudy().getId(),
								s.getSubject().getYearOfStudy().getYearOfStudy(),
								new ArrayList<SubjectDTO>(), s.getSubject().getYearOfStudy().getActive()),
						new OutcomeDTO(s.getSubject().getOutcome().getId(),
								s.getSubject().getOutcome().getDescription(), 
								new EducationGoalDTO(s.getSubject().getOutcome().getEducationGoal().getId(),
										s.getSubject().getOutcome().getEducationGoal().getDescription(), null,
										s.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
								s.getSubject().getOutcome().getActive()),
						new SubjectDTO(s.getSubject().getPrerequisite().getId(), 
								s.getSubject().getPrerequisite().getName(),
								s.getSubject().getPrerequisite().getEcts(), 
								s.getSubject().getPrerequisite().isCompulsory(),
								s.getSubject().getPrerequisite().getNumberOfClasses(), 
								s.getSubject().getPrerequisite().getNumberOfPractices(),
								s.getSubject().getPrerequisite().getOtherTypesOfClasses(),
								s.getSubject().getPrerequisite().getResearchWork(),
								s.getSubject().getPrerequisite().getClassesLeft(), 
								s.getSubject().getPrerequisite().getNumberOfSemesters(),
								new YearOfStudyDTO(s.getSubject().getYearOfStudy().getId(),
										s.getSubject().getYearOfStudy().getYearOfStudy(),
										new ArrayList<SubjectDTO>(), s.getSubject().getYearOfStudy().getActive()),
								new OutcomeDTO(s.getSubject().getOutcome().getId(),
										s.getSubject().getOutcome().getDescription(), 
										new EducationGoalDTO(s.getSubject().getOutcome().getEducationGoal().getId(),
												s.getSubject().getOutcome().getEducationGoal().getDescription(), null,
												s.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
										s.getSubject().getOutcome().getActive()), null, s.getSubject().getActive()), s.getActive()), s.getActive()));
			}else {
				subjectRealizations.add(new SubjectRealizationDTO(s.getId(),
						new EvaluationDTO(s.getEvaluation().getId(), 
			    				s.getEvaluation().getStartTime(), s.getEvaluation().getEndTime(),
			    				s.getEvaluation().getNumberOfPoints(),
			    				new EvaluationTypeDTO(s.getEvaluation().getEvaluationType().getId(),
			    						s.getEvaluation().getEvaluationType().getName(), s.getEvaluation().getEvaluationType().getActive()),
			    				new EvaluationInstrumentDTO(s.getEvaluation().getEvaluationInstrument().getId(),
			    						s.getEvaluation().getEvaluationInstrument().getName(),null, null),
			    				new ExaminationDTO(s.getEvaluation().getExamination().getId(),
			    						s.getEvaluation().getExamination().getNumberOfPoints(),
			    						null, new StudentOnYearDTO(s.getEvaluation().getExamination().getStudentOnYear().getId(),
			    								s.getEvaluation().getExamination().getStudentOnYear().getDateOfApplication(),
			    								new StudentDTO(s.getEvaluation().getExamination().getStudentOnYear().getStudent().getId(),
			    										null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getFirstName(),
			    										s.getEvaluation().getExamination().getStudentOnYear().getStudent().getLastName(),
			    										null, null, null, null, null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getActive()),
			    								null, null, s.getEvaluation().getExamination().getStudentOnYear().getActive()), 
			    						s.getEvaluation().getExamination().getActive()),
			    				s.getActive()),
						new TeacherOnRealizationDTO(s.getTeacherOnRealization().getId(), 
								s.getTeacherOnRealization().getNumberOfClasses(), 
								new TeacherDTO(s.getTeacherOnRealization().getTeacher().getId(),
										new RegisteredUserDTO(s.getTeacherOnRealization().getTeacher().getUser().getUsername(),
												null, s.getTeacherOnRealization().getTeacher().getUser().getEmail()),
										s.getTeacherOnRealization().getTeacher().getFirstName(),
										s.getTeacherOnRealization().getTeacher().getLastName(),
										s.getTeacherOnRealization().getTeacher().getUmcn(),
										s.getTeacherOnRealization().getTeacher().getBiography(), null, null,
										new DepartmentDTO(s.getTeacherOnRealization().getTeacher().getDepartment().getId(),
												s.getTeacherOnRealization().getTeacher().getDepartment().getName(),
												s.getTeacherOnRealization().getTeacher().getDepartment().getDescription(), null, null, null, null,
												s.getTeacherOnRealization().getTeacher().getDepartment().getActive()),
										s.getTeacherOnRealization().getTeacher().getActive()), null, null, null,
								s.getTeacherOnRealization().getActive()),
						new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(),s.getSubject().getEcts(), 
						s.getSubject().isCompulsory(), s.getSubject().getNumberOfClasses(), s.getSubject().getNumberOfPractices(),
						s.getSubject().getOtherTypesOfClasses(), s.getSubject().getResearchWork(),
						s.getSubject().getClassesLeft(), 
						s.getSubject().getNumberOfSemesters(),
						new YearOfStudyDTO(s.getSubject().getYearOfStudy().getId(),
								s.getSubject().getYearOfStudy().getYearOfStudy(),
								new ArrayList<SubjectDTO>(), s.getSubject().getYearOfStudy().getActive()),
						new OutcomeDTO(s.getSubject().getOutcome().getId(),
								s.getSubject().getOutcome().getDescription(), 
								new EducationGoalDTO(s.getSubject().getOutcome().getEducationGoal().getId(),
										s.getSubject().getOutcome().getEducationGoal().getDescription(), null,
										s.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
								s.getSubject().getOutcome().getActive()), null, s.getSubject().getActive()),
				s.getActive()));
			}
		}
		
		return new ResponseEntity<Iterable<SubjectRealizationDTO>>(subjectRealizations, HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/params")
	@Secured({"ADMIN"})
	public ResponseEntity<Page<SubjectRealizationDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<SubjectRealization> subjectRealizationPage = service.findAll(pageable);

	    List<SubjectRealizationDTO> subjectRealizationDTOs = subjectRealizationPage.stream().map(s -> 
	    new SubjectRealizationDTO(
	    		s.getId(), 
	    		new EvaluationDTO(s.getEvaluation().getId(), 
	    				s.getEvaluation().getStartTime(), s.getEvaluation().getEndTime(),
	    				s.getEvaluation().getNumberOfPoints(),
	    				new EvaluationTypeDTO(s.getEvaluation().getEvaluationType().getId(),
	    						s.getEvaluation().getEvaluationType().getName(), s.getEvaluation().getEvaluationType().getActive()),
	    				new EvaluationInstrumentDTO(s.getEvaluation().getEvaluationInstrument().getId(),
	    						s.getEvaluation().getEvaluationInstrument().getName(),null, null),
	    				new ExaminationDTO(s.getEvaluation().getExamination().getId(),
	    						s.getEvaluation().getExamination().getNumberOfPoints(),
	    						null, new StudentOnYearDTO(s.getEvaluation().getExamination().getStudentOnYear().getId(),
	    								s.getEvaluation().getExamination().getStudentOnYear().getDateOfApplication(),
	    								new StudentDTO(s.getEvaluation().getExamination().getStudentOnYear().getStudent().getId(),
	    										null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getFirstName(),
	    										s.getEvaluation().getExamination().getStudentOnYear().getStudent().getLastName(),
	    										null, null, null, null, null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getActive()),
	    								null, null, s.getEvaluation().getExamination().getStudentOnYear().getActive()), 
	    						s.getEvaluation().getExamination().getActive()),
	    				s.getActive()),
				new TeacherOnRealizationDTO(s.getTeacherOnRealization().getId(), 
						s.getTeacherOnRealization().getNumberOfClasses(), 
						new TeacherDTO(s.getTeacherOnRealization().getTeacher().getId(),
								new RegisteredUserDTO(s.getTeacherOnRealization().getTeacher().getUser().getUsername(),
										null, s.getTeacherOnRealization().getTeacher().getUser().getEmail()),
								s.getTeacherOnRealization().getTeacher().getFirstName(),
								s.getTeacherOnRealization().getTeacher().getLastName(),
								s.getTeacherOnRealization().getTeacher().getUmcn(),
								s.getTeacherOnRealization().getTeacher().getBiography(), null, null,
								new DepartmentDTO(s.getTeacherOnRealization().getTeacher().getDepartment().getId(),
										s.getTeacherOnRealization().getTeacher().getDepartment().getName(),
										s.getTeacherOnRealization().getTeacher().getDepartment().getDescription(), null, null, null, null,
										s.getTeacherOnRealization().getTeacher().getDepartment().getActive()),
								s.getTeacherOnRealization().getTeacher().getActive()), null, null, null,
						s.getTeacherOnRealization().getActive()),
				new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(),s.getSubject().getEcts(), 
				s.getSubject().isCompulsory(), s.getSubject().getNumberOfClasses(), s.getSubject().getNumberOfPractices(),
				s.getSubject().getOtherTypesOfClasses(), s.getSubject().getResearchWork(),
				s.getSubject().getClassesLeft(), 
				s.getSubject().getNumberOfSemesters(),
				new YearOfStudyDTO(s.getSubject().getYearOfStudy().getId(),
						s.getSubject().getYearOfStudy().getYearOfStudy(),
						new ArrayList<SubjectDTO>(), s.getSubject().getYearOfStudy().getActive()),
				new OutcomeDTO(s.getSubject().getOutcome().getId(),
						s.getSubject().getOutcome().getDescription(), 
						new EducationGoalDTO(s.getSubject().getOutcome().getEducationGoal().getId(),
								s.getSubject().getOutcome().getEducationGoal().getDescription(), null,
								s.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
						s.getSubject().getOutcome().getActive()),
				new SubjectDTO(s.getSubject().getPrerequisite().getId(), 
						s.getSubject().getPrerequisite().getName(),
						s.getSubject().getPrerequisite().getEcts(), 
						s.getSubject().getPrerequisite().isCompulsory(),
						s.getSubject().getPrerequisite().getNumberOfClasses(), 
						s.getSubject().getPrerequisite().getNumberOfPractices(),
						s.getSubject().getPrerequisite().getOtherTypesOfClasses(),
						s.getSubject().getPrerequisite().getResearchWork(),
						s.getSubject().getPrerequisite().getClassesLeft(), 
						s.getSubject().getPrerequisite().getNumberOfSemesters(),
						new YearOfStudyDTO(s.getSubject().getYearOfStudy().getId(),
								s.getSubject().getYearOfStudy().getYearOfStudy(),
								new ArrayList<SubjectDTO>(), s.getSubject().getYearOfStudy().getActive()),
						new OutcomeDTO(s.getSubject().getOutcome().getId(),
								s.getSubject().getOutcome().getDescription(), 
								new EducationGoalDTO(s.getSubject().getOutcome().getEducationGoal().getId(),
										s.getSubject().getOutcome().getEducationGoal().getDescription(), null,
										s.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
								s.getSubject().getOutcome().getActive()), null, s.getSubject().getActive()),
				s.getActive()), s.getActive()
	        )
	    ).collect(Collectors.toList());

	    Page<SubjectRealizationDTO> resultPage = new PageImpl<SubjectRealizationDTO>(subjectRealizationDTOs, pageable, subjectPage.getTotalElements());

	    return new ResponseEntity<Page<SubjectRealizationDTO>>(resultPage, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectRealizationDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		SubjectRealization s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<SubjectRealizationDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<SubjectRealizationDTO>(new SubjectRealizationDTO(s.getId(),
				new EvaluationDTO(s.getEvaluation().getId(), 
	    				s.getEvaluation().getStartTime(), s.getEvaluation().getEndTime(),
	    				s.getEvaluation().getNumberOfPoints(),
	    				new EvaluationTypeDTO(s.getEvaluation().getEvaluationType().getId(),
	    						s.getEvaluation().getEvaluationType().getName(), s.getEvaluation().getEvaluationType().getActive()),
	    				new EvaluationInstrumentDTO(s.getEvaluation().getEvaluationInstrument().getId(),
	    						s.getEvaluation().getEvaluationInstrument().getName(),null, null),
	    				new ExaminationDTO(s.getEvaluation().getExamination().getId(),
	    						s.getEvaluation().getExamination().getNumberOfPoints(),
	    						null, new StudentOnYearDTO(s.getEvaluation().getExamination().getStudentOnYear().getId(),
	    								s.getEvaluation().getExamination().getStudentOnYear().getDateOfApplication(),
	    								new StudentDTO(s.getEvaluation().getExamination().getStudentOnYear().getStudent().getId(),
	    										null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getFirstName(),
	    										s.getEvaluation().getExamination().getStudentOnYear().getStudent().getLastName(),
	    										null, null, null, null, null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getActive()),
	    								null, null, s.getEvaluation().getExamination().getStudentOnYear().getActive()), 
	    						s.getEvaluation().getExamination().getActive()),
	    				s.getActive()),
				new TeacherOnRealizationDTO(s.getTeacherOnRealization().getId(), 
						s.getTeacherOnRealization().getNumberOfClasses(), 
						new TeacherDTO(s.getTeacherOnRealization().getTeacher().getId(),
								new RegisteredUserDTO(s.getTeacherOnRealization().getTeacher().getUser().getUsername(),
										null, s.getTeacherOnRealization().getTeacher().getUser().getEmail()),
								s.getTeacherOnRealization().getTeacher().getFirstName(),
								s.getTeacherOnRealization().getTeacher().getLastName(),
								s.getTeacherOnRealization().getTeacher().getUmcn(),
								s.getTeacherOnRealization().getTeacher().getBiography(), null, null,
								new DepartmentDTO(s.getTeacherOnRealization().getTeacher().getDepartment().getId(),
										s.getTeacherOnRealization().getTeacher().getDepartment().getName(),
										s.getTeacherOnRealization().getTeacher().getDepartment().getDescription(), null, null, null, null,
										s.getTeacherOnRealization().getTeacher().getDepartment().getActive()),
								s.getTeacherOnRealization().getTeacher().getActive()), null, null, null,
						s.getTeacherOnRealization().getActive()),
				new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(),s.getSubject().getEcts(), 
				s.getSubject().isCompulsory(), s.getSubject().getNumberOfClasses(), s.getSubject().getNumberOfPractices(),
				s.getSubject().getOtherTypesOfClasses(), s.getSubject().getResearchWork(),
				s.getSubject().getClassesLeft(), 
				s.getSubject().getNumberOfSemesters(),
				new YearOfStudyDTO(s.getSubject().getYearOfStudy().getId(),
						s.getSubject().getYearOfStudy().getYearOfStudy(),
						new ArrayList<SubjectDTO>(), s.getSubject().getYearOfStudy().getActive()),
				new OutcomeDTO(s.getSubject().getOutcome().getId(),
						s.getSubject().getOutcome().getDescription(), 
						new EducationGoalDTO(s.getSubject().getOutcome().getEducationGoal().getId(),
								s.getSubject().getOutcome().getEducationGoal().getDescription(), null,
								s.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
						s.getSubject().getOutcome().getActive()), null, s.getSubject().getActive()),
		s.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping("")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectRealizationDTO> create(@RequestBody SubjectRealizationDTO t) {
		// TODO Auto-generated method stub
		SubjectRealization s = service.create(new SubjectRealization(null,
				new Evaluation(t.getEvaluation().getId(), 
	    				t.getEvaluation().getStartTime(), t.getEvaluation().getEndTime(),
	    				t.getEvaluation().getNumberOfPoints(),
	    				new EvaluationType(t.getEvaluation().getEvaluationType().getId(),
	    						t.getEvaluation().getEvaluationType().getName(), 
	    						t.getEvaluation().getEvaluationType().getActive()),
	    				new EvaluationInstrument(t.getEvaluation().getEvaluationInstrument().getId(),
	    						t.getEvaluation().getEvaluationInstrument().getName(),null, null),
	    				new Examination(t.getEvaluation().getExamination().getId(),
	    						t.getEvaluation().getExamination().getNumberOfPoints(),
	    						null, new StudentOnYear(t.getEvaluation().getExamination().getStudentOnYear().getId(),
	    								t.getEvaluation().getExamination().getStudentOnYear().getDateOfApplication(),
	    								new Student(t.getEvaluation().getExamination().getStudentOnYear().getStudent().getId(),
	    										null, t.getEvaluation().getExamination().getStudentOnYear().getStudent().getFirstName(),
	    										t.getEvaluation().getExamination().getStudentOnYear().getStudent().getLastName(),
	    										t.getEvaluation().getExamination().getStudentOnYear().getStudent().getUmcn(),
	    										t.getEvaluation().getExamination().getStudentOnYear().getStudent().getIndexNumber(),
	    										null, null, null, t.getEvaluation().getExamination().getStudentOnYear().getStudent().getActive()),
	    								null, null, t.getEvaluation().getExamination().getStudentOnYear().getActive()), 
	    						t.getEvaluation().getExamination().getActive())),
				new TeacherOnRealization(t.getTeacherOnRealization().getId(), 
						t.getTeacherOnRealization().getNumberOfClasses(), 
						new Teacher(t.getTeacherOnRealization().getTeacher().getId(),
								new RegisteredUser(t.getTeacherOnRealization().getTeacher().getUser().getId(),
										t.getTeacherOnRealization().getTeacher().getUser().getUsername(),
										t.getTeacherOnRealization().getTeacher().getUser().getPassword(),
										t.getTeacherOnRealization().getTeacher().getUser().getEmail(), null, null, null, null),
								t.getTeacherOnRealization().getTeacher().getFirstName(),
								t.getTeacherOnRealization().getTeacher().getLastName(),
								t.getTeacherOnRealization().getTeacher().getUmcn(),
								t.getTeacherOnRealization().getTeacher().getBiography(), null, null,
								new Department(t.getTeacherOnRealization().getTeacher().getDepartment().getId(),
										t.getTeacherOnRealization().getTeacher().getDepartment().getName(),
										t.getTeacherOnRealization().getTeacher().getDepartment().getDescription(), null, null, null, null,
										t.getTeacherOnRealization().getTeacher().getDepartment().getActive()),
								t.getTeacherOnRealization().getTeacher().getActive()), null, null, null,
						t.getTeacherOnRealization().getActive()),
				new Subject(t.getSubject().getId(), t.getSubject().getName(),t.getSubject().getEcts(), 
				t.getSubject().isCompulsory(), t.getSubject().getNumberOfClasses(), s.getSubject().getNumberOfPractices(),
				t.getSubject().getOtherTypesOfClasses(), t.getSubject().getResearchWork(),
				t.getSubject().getClassesLeft(), 
				t.getSubject().getNumberOfSemesters(),
				new YearOfStudy(t.getSubject().getYearOfStudy().getId(),
						t.getSubject().getYearOfStudy().getYearOfStudy(),
						new ArrayList<Subject>(), t.getSubject().getYearOfStudy().getActive()),
				new Outcome(t.getSubject().getOutcome().getId(),
						t.getSubject().getOutcome().getDescription(), 
						new EducationGoal(t.getSubject().getOutcome().getEducationGoal().getId(),
								t.getSubject().getOutcome().getEducationGoal().getDescription(), null,
								t.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
						t.getSubject().getOutcome().getActive()), null, t.getSubject().getActive()),
		t.getActive()));
		
		if(s == null) {
			return new ResponseEntity<SubjectRealizationDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<SubjectRealizationDTO>(new SubjectRealizationDTO(s.getId(),
				new EvaluationDTO(s.getEvaluation().getId(), 
	    				s.getEvaluation().getStartTime(), s.getEvaluation().getEndTime(),
	    				s.getEvaluation().getNumberOfPoints(),
	    				new EvaluationTypeDTO(s.getEvaluation().getEvaluationType().getId(),
	    						s.getEvaluation().getEvaluationType().getName(), s.getEvaluation().getEvaluationType().getActive()),
	    				new EvaluationInstrumentDTO(s.getEvaluation().getEvaluationInstrument().getId(),
	    						s.getEvaluation().getEvaluationInstrument().getName(),null, null),
	    				new ExaminationDTO(s.getEvaluation().getExamination().getId(),
	    						s.getEvaluation().getExamination().getNumberOfPoints(),
	    						null, new StudentOnYearDTO(s.getEvaluation().getExamination().getStudentOnYear().getId(),
	    								s.getEvaluation().getExamination().getStudentOnYear().getDateOfApplication(),
	    								new StudentDTO(s.getEvaluation().getExamination().getStudentOnYear().getStudent().getId(),
	    										null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getFirstName(),
	    										s.getEvaluation().getExamination().getStudentOnYear().getStudent().getLastName(),
	    										null, null, null, null, null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getActive()),
	    								null, null, s.getEvaluation().getExamination().getStudentOnYear().getActive()), 
	    						s.getEvaluation().getExamination().getActive()),
	    				s.getActive()),
				new TeacherOnRealizationDTO(s.getTeacherOnRealization().getId(), 
						s.getTeacherOnRealization().getNumberOfClasses(), 
						new TeacherDTO(s.getTeacherOnRealization().getTeacher().getId(),
								new RegisteredUserDTO(s.getTeacherOnRealization().getTeacher().getUser().getUsername(),
										null, s.getTeacherOnRealization().getTeacher().getUser().getEmail()),
								s.getTeacherOnRealization().getTeacher().getFirstName(),
								s.getTeacherOnRealization().getTeacher().getLastName(),
								s.getTeacherOnRealization().getTeacher().getUmcn(),
								s.getTeacherOnRealization().getTeacher().getBiography(), null, null,
								new DepartmentDTO(s.getTeacherOnRealization().getTeacher().getDepartment().getId(),
										s.getTeacherOnRealization().getTeacher().getDepartment().getName(),
										s.getTeacherOnRealization().getTeacher().getDepartment().getDescription(), null, null, null, null,
										s.getTeacherOnRealization().getTeacher().getDepartment().getActive()),
								s.getTeacherOnRealization().getTeacher().getActive()), null, null, null,
						s.getTeacherOnRealization().getActive()),
				new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(),s.getSubject().getEcts(), 
				s.getSubject().isCompulsory(), s.getSubject().getNumberOfClasses(), s.getSubject().getNumberOfPractices(),
				s.getSubject().getOtherTypesOfClasses(), s.getSubject().getResearchWork(),
				s.getSubject().getClassesLeft(), 
				s.getSubject().getNumberOfSemesters(),
				new YearOfStudyDTO(s.getSubject().getYearOfStudy().getId(),
						s.getSubject().getYearOfStudy().getYearOfStudy(),
						new ArrayList<SubjectDTO>(), s.getSubject().getYearOfStudy().getActive()),
				new OutcomeDTO(s.getSubject().getOutcome().getId(),
						s.getSubject().getOutcome().getDescription(), 
						new EducationGoalDTO(s.getSubject().getOutcome().getEducationGoal().getId(),
								s.getSubject().getOutcome().getEducationGoal().getDescription(), null,
								s.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
						s.getSubject().getOutcome().getActive()), null, s.getSubject().getActive()),
		s.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectRealizationDTO> update(@RequestBody SubjectRealizationDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		SubjectRealization s = service.findById(id).orElse(null);
		
		ArrayList<Subject> subjectsOnYear = new ArrayList<Subject>();
		ArrayList<SubjectDTO> subjectsOnYearDTO= new ArrayList<SubjectDTO>();
		
		
		if(s == null) {
			return new ResponseEntity<SubjectRealizationDTO>(HttpStatus.NOT_FOUND);
		}
		
		s.setId(t.getId());
		s.setEvaluation(new Evaluation(t.getEvaluation().getId(), 
				t.getEvaluation().getStartTime(), s.getEvaluation().getEndTime(),
				t.getEvaluation().getNumberOfPoints(),
				new EvaluationType(t.getEvaluation().getEvaluationType().getId(),
						t.getEvaluation().getEvaluationType().getName(), 
						t.getEvaluation().getEvaluationType().getActive()),
				new EvaluationInstrument(t.getEvaluation().getEvaluationInstrument().getId(),
						t.getEvaluation().getEvaluationInstrument().getName(),null, null),
				new Examination(t.getEvaluation().getExamination().getId(),
						t.getEvaluation().getExamination().getNumberOfPoints(),
						null, new StudentOnYear(t.getEvaluation().getExamination().getStudentOnYear().getId(),
								t.getEvaluation().getExamination().getStudentOnYear().getDateOfApplication(),
								new Student(t.getEvaluation().getExamination().getStudentOnYear().getStudent().getId(),
										null, t.getEvaluation().getExamination().getStudentOnYear().getStudent().getFirstName(),
										t.getEvaluation().getExamination().getStudentOnYear().getStudent().getLastName(),
										null, null, null, null, null, t.getEvaluation().getExamination().getStudentOnYear().getStudent().getActive()),
								null, null, t.getEvaluation().getExamination().getStudentOnYear().getActive()), 
						t.getEvaluation().getExamination().getActive())));
		
		s.setSubject(new Subject(t.getSubject().getId(), t.getSubject().getName(),t.getSubject().getEcts(), 
				t.getSubject().isCompulsory(), t.getSubject().getNumberOfClasses(), t.getSubject().getNumberOfPractices(),
				t.getSubject().getOtherTypesOfClasses(), t.getSubject().getResearchWork(),
				t.getSubject().getClassesLeft(), 
				t.getSubject().getNumberOfSemesters(),
				new YearOfStudy(t.getSubject().getYearOfStudy().getId(),
						t.getSubject().getYearOfStudy().getYearOfStudy(),
						new ArrayList<Subject>(), 
						t.getSubject().getYearOfStudy().getActive()),
				new Outcome(t.getSubject().getOutcome().getId(),
						t.getSubject().getOutcome().getDescription(), 
						new EducationGoal(t.getSubject().getOutcome().getEducationGoal().getId(),
								t.getSubject().getOutcome().getEducationGoal().getDescription(), null,
								t.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
						t.getSubject().getOutcome().getActive()), null, t.getSubject().getActive()));
		t.setTeacherOnRealization(new TeacherOnRealization(t.getTeacherOnRealization().getId(), 
				t.getTeacherOnRealization().getNumberOfClasses(), 
				new Teacher(t.getTeacherOnRealization().getTeacher().getId(),
						new RegisteredUser(t.getTeacherOnRealization().getTeacher().getUser().getId(),
								t.getTeacherOnRealization().getTeacher().getUser().getUsername(),
								t.getTeacherOnRealization().getTeacher().getUser().getPassword(),
								t.getTeacherOnRealization().getTeacher().getUser().getEmail(), null, null, null,
								t.getTeacherOnRealization().getTeacher().getUser().getActive()),
						t.getTeacherOnRealization().getTeacher().getFirstName(),
						t.getTeacherOnRealization().getTeacher().getLastName(),
						t.getTeacherOnRealization().getTeacher().getUmcn(),
						t.getTeacherOnRealization().getTeacher().getBiography(), null, null,
						new Department(t.getTeacherOnRealization().getTeacher().getDepartment().getId(),
								t.getTeacherOnRealization().getTeacher().getDepartment().getName(),
								t.getTeacherOnRealization().getTeacher().getDepartment().getDescription(), null, null, null, null,
								t.getTeacherOnRealization().getTeacher().getDepartment().getActive()),
						t.getTeacherOnRealization().getTeacher().getActive()), null, null, null,
				t.getTeacherOnRealization().getActive()),
		new Subject(t.getSubject().getId(), t.getSubject().getName(),t.getSubject().getEcts(), 
		t.getSubject().isCompulsory(), t.getSubject().getNumberOfClasses(), t.getSubject().getNumberOfPractices(),
		t.getSubject().getOtherTypesOfClasses(), s.getSubject().getResearchWork(),
		t.getSubject().getClassesLeft(), 
		t.getSubject().getNumberOfSemesters(),
		new YearOfStudy(t.getSubject().getYearOfStudy().getId(),
				t.getSubject().getYearOfStudy().getYearOfStudy(),
				new ArrayList<Subject>(), t.getSubject().getYearOfStudy().getActive()),
		new Outcome(t.getSubject().getOutcome().getId(),
				t.getSubject().getOutcome().getDescription(), 
				new EducationGoal(s.getSubject().getOutcome().getEducationGoal().getId(),
						s.getSubject().getOutcome().getEducationGoal().getDescription(), null,
						s.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
				s.getSubject().getOutcome().getActive()), null, s.getSubject().getActive()),s.getActive());
		s.setActive(t.getActive());
		
		s = service.update(s);
		
		return new ResponseEntity<SubjectRealizationDTO>(new SubjectRealizationDTO(s.getId(),
				new EvaluationDTO(s.getEvaluation().getId(), 
	    				s.getEvaluation().getStartTime(), s.getEvaluation().getEndTime(),
	    				s.getEvaluation().getNumberOfPoints(),
	    				new EvaluationTypeDTO(s.getEvaluation().getEvaluationType().getId(),
	    						s.getEvaluation().getEvaluationType().getName(), s.getEvaluation().getEvaluationType().getActive()),
	    				new EvaluationInstrumentDTO(s.getEvaluation().getEvaluationInstrument().getId(),
	    						s.getEvaluation().getEvaluationInstrument().getName(),null, null),
	    				new ExaminationDTO(s.getEvaluation().getExamination().getId(),
	    						s.getEvaluation().getExamination().getNumberOfPoints(),
	    						null, new StudentOnYearDTO(s.getEvaluation().getExamination().getStudentOnYear().getId(),
	    								s.getEvaluation().getExamination().getStudentOnYear().getDateOfApplication(),
	    								new StudentDTO(s.getEvaluation().getExamination().getStudentOnYear().getStudent().getId(),
	    										null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getFirstName(),
	    										s.getEvaluation().getExamination().getStudentOnYear().getStudent().getLastName(),
	    										null, null, null, null, null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getActive()),
	    								null, null, s.getEvaluation().getExamination().getStudentOnYear().getActive()), 
	    						s.getEvaluation().getExamination().getActive()),
	    				s.getActive()),
				new TeacherOnRealizationDTO(s.getTeacherOnRealization().getId(), 
						s.getTeacherOnRealization().getNumberOfClasses(), 
						new TeacherDTO(s.getTeacherOnRealization().getTeacher().getId(),
								new RegisteredUserDTO(s.getTeacherOnRealization().getTeacher().getUser().getUsername(),
										null, s.getTeacherOnRealization().getTeacher().getUser().getEmail()),
								s.getTeacherOnRealization().getTeacher().getFirstName(),
								s.getTeacherOnRealization().getTeacher().getLastName(),
								s.getTeacherOnRealization().getTeacher().getUmcn(),
								s.getTeacherOnRealization().getTeacher().getBiography(), null, null,
								new DepartmentDTO(s.getTeacherOnRealization().getTeacher().getDepartment().getId(),
										s.getTeacherOnRealization().getTeacher().getDepartment().getName(),
										s.getTeacherOnRealization().getTeacher().getDepartment().getDescription(), null, null, null, null,
										s.getTeacherOnRealization().getTeacher().getDepartment().getActive()),
								s.getTeacherOnRealization().getTeacher().getActive()), null, null, null,
						s.getTeacherOnRealization().getActive()),
				new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(),s.getSubject().getEcts(), 
				s.getSubject().isCompulsory(), s.getSubject().getNumberOfClasses(), s.getSubject().getNumberOfPractices(),
				s.getSubject().getOtherTypesOfClasses(), s.getSubject().getResearchWork(),
				s.getSubject().getClassesLeft(), 
				s.getSubject().getNumberOfSemesters(),
				new YearOfStudyDTO(s.getSubject().getYearOfStudy().getId(),
						s.getSubject().getYearOfStudy().getYearOfStudy(),
						new ArrayList<SubjectDTO>(), s.getSubject().getYearOfStudy().getActive()),
				new OutcomeDTO(s.getSubject().getOutcome().getId(),
						s.getSubject().getOutcome().getDescription(), 
						new EducationGoalDTO(s.getSubject().getOutcome().getEducationGoal().getId(),
								s.getSubject().getOutcome().getEducationGoal().getDescription(), null,
								s.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
						s.getSubject().getOutcome().getActive()), null, s.getSubject().getActive()),
		s.getActive()), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectRealizationDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/deleted/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectRealizationDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		SubjectRealization s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<SubjectRealizationDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.softDelete(id);
		return new ResponseEntity<SubjectRealizationDTO>(new SubjectRealizationDTO(s.getId(),
				new EvaluationDTO(s.getEvaluation().getId(), 
	    				s.getEvaluation().getStartTime(), s.getEvaluation().getEndTime(),
	    				s.getEvaluation().getNumberOfPoints(),
	    				new EvaluationTypeDTO(s.getEvaluation().getEvaluationType().getId(),
	    						s.getEvaluation().getEvaluationType().getName(), s.getEvaluation().getEvaluationType().getActive()),
	    				new EvaluationInstrumentDTO(s.getEvaluation().getEvaluationInstrument().getId(),
	    						s.getEvaluation().getEvaluationInstrument().getName(),null, null),
	    				new ExaminationDTO(s.getEvaluation().getExamination().getId(),
	    						s.getEvaluation().getExamination().getNumberOfPoints(),
	    						null, new StudentOnYearDTO(s.getEvaluation().getExamination().getStudentOnYear().getId(),
	    								s.getEvaluation().getExamination().getStudentOnYear().getDateOfApplication(),
	    								new StudentDTO(s.getEvaluation().getExamination().getStudentOnYear().getStudent().getId(),
	    										null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getFirstName(),
	    										s.getEvaluation().getExamination().getStudentOnYear().getStudent().getLastName(),
	    										null, null, null, null, null, s.getEvaluation().getExamination().getStudentOnYear().getStudent().getActive()),
	    								null, null, s.getEvaluation().getExamination().getStudentOnYear().getActive()), 
	    						s.getEvaluation().getExamination().getActive()),
	    				s.getActive()),
				new TeacherOnRealizationDTO(s.getTeacherOnRealization().getId(), 
						s.getTeacherOnRealization().getNumberOfClasses(), 
						new TeacherDTO(s.getTeacherOnRealization().getTeacher().getId(),
								new RegisteredUserDTO(s.getTeacherOnRealization().getTeacher().getUser().getUsername(),
										null, s.getTeacherOnRealization().getTeacher().getUser().getEmail()),
								s.getTeacherOnRealization().getTeacher().getFirstName(),
								s.getTeacherOnRealization().getTeacher().getLastName(),
								s.getTeacherOnRealization().getTeacher().getUmcn(),
								s.getTeacherOnRealization().getTeacher().getBiography(), null, null,
								new DepartmentDTO(s.getTeacherOnRealization().getTeacher().getDepartment().getId(),
										s.getTeacherOnRealization().getTeacher().getDepartment().getName(),
										s.getTeacherOnRealization().getTeacher().getDepartment().getDescription(), null, null, null, null,
										s.getTeacherOnRealization().getTeacher().getDepartment().getActive()),
								s.getTeacherOnRealization().getTeacher().getActive()), null, null, null,
						s.getTeacherOnRealization().getActive()),
				new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(),s.getSubject().getEcts(), 
				s.getSubject().isCompulsory(), s.getSubject().getNumberOfClasses(), s.getSubject().getNumberOfPractices(),
				s.getSubject().getOtherTypesOfClasses(), s.getSubject().getResearchWork(),
				s.getSubject().getClassesLeft(), 
				s.getSubject().getNumberOfSemesters(),
				new YearOfStudyDTO(s.getSubject().getYearOfStudy().getId(),
						s.getSubject().getYearOfStudy().getYearOfStudy(),
						new ArrayList<SubjectDTO>(), s.getSubject().getYearOfStudy().getActive()),
				new OutcomeDTO(s.getSubject().getOutcome().getId(),
						s.getSubject().getOutcome().getDescription(), 
						new EducationGoalDTO(s.getSubject().getOutcome().getEducationGoal().getId(),
								s.getSubject().getOutcome().getEducationGoal().getDescription(), null,
								s.getSubject().getOutcome().getEducationGoal().getActive()), null, null, 
						s.getSubject().getOutcome().getActive()), null, s.getSubject().getActive()),
		s.getActive()), HttpStatus.OK);
	}
}
