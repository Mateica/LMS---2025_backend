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

import main.dto.EducationGoalDTO;
import main.dto.EvaluationDTO;
import main.dto.EvaluationInstrumentDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.ExaminationDTO;
import main.dto.OutcomeDTO;
import main.dto.SubjectDTO;
import main.dto.SubjectRealizationDTO;
import main.dto.TeacherOnRealizationDTO;
import main.dto.YearOfStudyDTO;
import main.model.EducationGoal;
import main.model.EvaluationInstrument;
import main.model.EvaluationType;
import main.model.Examination;
import main.model.Outcome;
import main.model.Subject;
import main.model.SubjectRealization;
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
	@Secured({"ADMIN", "TEACHER"})
	public ResponseEntity<Iterable<SubjectRealizationDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<SubjectRealizationDTO> subjectRealizations = new ArrayList<SubjectRealizationDTO>();
		
		for(SubjectRealization s : service.findAll()) {
			if(s.getSubject().getPrerequisite() != null) {
				subjectRealizations.add(new SubjectRealizationDTO(s.getId(),
						new EvaluationDTO(),
						new TeacherOnRealizationDTO(),
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
						new EvaluationDTO(),
						new TeacherOnRealizationDTO(),
						new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(),s.getSubject().getEcts(), 
						s.getSubject().isCompulsory(), s.getSubject().getNumberOfClasses(), s.getSubject().getNumberOfPractices(),
						s.getSubject().getOtherTypesOfClasses(), s.getSubject().getResearchWork(),
						s.getSubject().getClassesLeft(), 
						s.getSubject().getNumberOfSemesters(),
						new YearOfStudyDTO(s.getSubject().getYearOfStudy().getId(),
								s.getSubject().getYearOfStudy().getYearOfStudy(),
								new ArrayList<SubjectDTO>(), s.getSubject().getYearOfStudy().getActive()),
						new OutcomeDTO(),
						new SubjectDTO(), s.getActive()), s.getActive()));
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
	    				new EvaluationInstrumentDTO(),
	    				new ExaminationDTO(),
	    				s.getActive()),
				new TeacherOnRealizationDTO(),
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
	public ResponseEntity<SubjectDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Subject s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<SubjectDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<SubjectDTO>(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
				s.isCompulsory(), s.getNumberOfClasses(), s.getNumberOfPractices(),
				s.getOtherTypesOfClasses(), s.getResearchWork(), s.getClassesLeft(), s.getNumberOfSemesters(),
				new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, null),
				new OutcomeDTO(s.getOutcome().getId(), s.getOutcome().getDescription(), 
						null, null, null, s.getOutcome().getActive()),
				new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),
						s.getPrerequisite().getEcts(), 
						s.getPrerequisite().isCompulsory(), 
						null, null, null, null, null, null, null, null, null,null), s.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping("")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectDTO> create(@RequestBody SubjectDTO t) {
		// TODO Auto-generated method stub
		Subject s = service.create(new Subject(null, t.getName(),t.getEcts(), 
				t.isCompulsory(), t.getNumberOfClasses(), t.getNumberOfPractices(),
				t.getOtherTypesOfClasses(), t.getResearchWork(), t.getClassesLeft(), t.getNumberOfSemesters(),
				new YearOfStudy(),
				new Outcome(t.getOutcome().getId(), t.getOutcome().getDescription(), 
						null, null, null, t.getOutcome().getActive()),
				new Subject(), true));
		
		if(s == null) {
			return new ResponseEntity<SubjectDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<SubjectDTO>(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
				s.isCompulsory(), s.getNumberOfClasses(), s.getNumberOfPractices(),
				s.getOtherTypesOfClasses(), s.getResearchWork(), s.getClassesLeft(), s.getNumberOfSemesters(),
				new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, null),
				new OutcomeDTO(s.getOutcome().getId(), s.getOutcome().getDescription(), 
						null, null, null, s.getOutcome().getActive()),
				new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),
						s.getPrerequisite().getEcts(), 
						s.getPrerequisite().isCompulsory(), 
						null, null, null, null, null, null, null, null, null,null), s.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectDTO> update(@RequestBody SubjectDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Subject s = service.findById(id).orElse(null);
		
		ArrayList<Subject> subjectsOnYear = new ArrayList<Subject>();
		ArrayList<SubjectDTO> subjectsOnYearDTO= new ArrayList<SubjectDTO>();
		
		
		if(s == null) {
			return new ResponseEntity<SubjectDTO>(HttpStatus.NOT_FOUND);
		}
		
		for(Subject yearSubject : s.getYearOfStudy().getSubjects()) {
			subjectsOnYearDTO.add(new SubjectDTO(yearSubject.getId(), 
					yearSubject.getName(),
					yearSubject.getEcts(),
					yearSubject.isCompulsory(), yearSubject.getNumberOfClasses(),
					yearSubject.getNumberOfPractices(),
					yearSubject.getOtherTypesOfClasses(),
					yearSubject.getResearchWork(),
					yearSubject.getClassesLeft(),
					yearSubject.getNumberOfSemesters(),
					new YearOfStudyDTO(yearSubject.getYearOfStudy().getId(),
							yearSubject.getYearOfStudy().getYearOfStudy(),
							new ArrayList<SubjectDTO>(), yearSubject.getYearOfStudy().getActive()),
					new OutcomeDTO(yearSubject.getOutcome().getId(), yearSubject.getOutcome().getDescription(), 
							null, null, null, yearSubject.getOutcome().getActive()), 
					new SubjectDTO(), null));
		}
		
		for(SubjectDTO dto : subjectsOnYearDTO) {
			subjectsOnYear.add(new Subject(dto.getId(), 
					dto.getName(),
					dto.getEcts(),
					dto.isCompulsory(), dto.getNumberOfClasses(),
					dto.getNumberOfPractices(),
					dto.getOtherTypesOfClasses(),
					dto.getResearchWork(),
					dto.getClassesLeft(),
					dto.getNumberOfSemesters(),
					new YearOfStudy(dto.getYearOfStudy().getId(),
							dto.getYearOfStudy().getYearOfStudy(),
							subjectsOnYear, 
							dto.getYearOfStudy().getActive()),
					new Outcome(dto.getOutcome().getId(),
							dto.getOutcome().getDescription(),
							new EducationGoal(dto.getOutcome().getEducationGoal().getId(),
									dto.getOutcome().getEducationGoal().getDescription(),
								null, dto.getOutcome().getEducationGoal().getActive()),
							new TeachingMaterial(), new Subject(dto.getOutcome().getSubject().getId(), 
									dto.getOutcome().getSubject().getName(),
									dto.getOutcome().getSubject().getEcts(),
									dto.getOutcome().getSubject().isCompulsory(),
									dto.getOutcome().getSubject().getNumberOfClasses(),
									dto.getOutcome().getSubject().getNumberOfPractices(),
									dto.getOutcome().getSubject().getOtherTypesOfClasses(),
									dto.getOutcome().getSubject().getResearchWork(),
									dto.getOutcome().getSubject().getClassesLeft(),
									dto.getOutcome().getSubject().getNumberOfSemesters(),
									null, null, null, true), dto.getOutcome().getActive()), 
					new Subject(), null));
		}
		
		s.setName(t.getName());
		s.setEcts(t.getEcts());
		s.setCompulsory(t.isCompulsory());
		s.setNumberOfClasses(t.getNumberOfClasses());
		s.setOtherTypesOfClasses(t.getOtherTypesOfClasses());
		s.setResearchWork(t.getResearchWork());
		s.setClassesLeft(t.getClassesLeft());
		s.setNumberOfPractices(t.getNumberOfPractices());
		s.setNumberOfSemesters(t.getNumberOfSemesters());
		s.setYearOfStudy(new YearOfStudy(t.getYearOfStudy().getId(), t.getYearOfStudy().getYearOfStudy(),null, true));
		s.setOutcome(new Outcome(t.getOutcome().getId(),
				t.getOutcome().getDescription(),
				new EducationGoal(),
				new TeachingMaterial(), new Subject(t.getOutcome().getSubject().getId(), 
						t.getOutcome().getSubject().getName(),
						t.getOutcome().getSubject().getEcts(),
						t.getOutcome().getSubject().isCompulsory(),
						t.getOutcome().getSubject().getNumberOfClasses(),
						t.getOutcome().getSubject().getNumberOfPractices(),
						t.getOutcome().getSubject().getOtherTypesOfClasses(),
						t.getOutcome().getSubject().getResearchWork(),
						t.getOutcome().getSubject().getClassesLeft(),
						t.getOutcome().getSubject().getNumberOfSemesters(),
						null, null, null, true), t.getOutcome().getActive()));
		
		s.setPrerequisite(new Subject(t.getPrerequisite().getId(), 
				t.getPrerequisite().getName(),
				t.getPrerequisite().getEcts(),
				t.getPrerequisite().isCompulsory(),
				t.getPrerequisite().getNumberOfClasses(),
				t.getPrerequisite().getNumberOfPractices(),
				t.getPrerequisite().getOtherTypesOfClasses(),
				t.getPrerequisite().getResearchWork(),
				t.getPrerequisite().getClassesLeft(),
				t.getPrerequisite().getNumberOfSemesters(),
				new YearOfStudy(t.getPrerequisite().getYearOfStudy().getId(),
						t.getPrerequisite().getYearOfStudy().getYearOfStudy(),
						new ArrayList<Subject>(), t.getPrerequisite().getYearOfStudy().getActive()),
				new Outcome(t.getOutcome().getId(),
						t.getOutcome().getDescription(),
						new EducationGoal(),
						new TeachingMaterial(), new Subject(t.getOutcome().getSubject().getId(), 
								t.getOutcome().getSubject().getName(),
								t.getOutcome().getSubject().getEcts(),
								t.getOutcome().getSubject().isCompulsory(),
								t.getOutcome().getSubject().getNumberOfClasses(),
								t.getOutcome().getSubject().getNumberOfPractices(),
								t.getOutcome().getSubject().getOtherTypesOfClasses(),
								t.getOutcome().getSubject().getResearchWork(),
								t.getOutcome().getSubject().getClassesLeft(),
								t.getOutcome().getSubject().getNumberOfSemesters(),
								null, null, null, true), t.getOutcome().getActive()),
				new Subject(t.getPrerequisite().getId(), 
						t.getPrerequisite().getName(),
						t.getPrerequisite().getEcts(),
						t.getPrerequisite().isCompulsory(),
						t.getPrerequisite().getNumberOfClasses(),
						t.getPrerequisite().getNumberOfPractices(),
						t.getPrerequisite().getOtherTypesOfClasses(),
						t.getPrerequisite().getResearchWork(),
						t.getPrerequisite().getClassesLeft(),
						t.getPrerequisite().getNumberOfSemesters(),
						null, null, null, true), t.getActive()));
		s.setActive(t.getActive());
		
		s = service.update(s);
		
		return new ResponseEntity<SubjectDTO>(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
				s.isCompulsory(), s.getNumberOfClasses(), s.getNumberOfPractices(),
				s.getOtherTypesOfClasses(), s.getResearchWork(), s.getClassesLeft(), s.getNumberOfSemesters(),
				new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, null),
				new OutcomeDTO(s.getOutcome().getId(), s.getOutcome().getDescription(), 
						null, null, null, s.getOutcome().getActive()),
				new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),
						s.getPrerequisite().getEcts(), 
						s.getPrerequisite().isCompulsory(), 
						null, null, null, null, null, null, null, null, null,null), s.getActive()), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/deleted/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Subject s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<SubjectDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.softDelete(id);
		return new ResponseEntity<SubjectDTO>(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
				s.isCompulsory(), s.getNumberOfClasses(), s.getNumberOfPractices(),
				s.getOtherTypesOfClasses(), s.getResearchWork(), s.getClassesLeft(), s.getNumberOfSemesters(),
				new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, null),
				new OutcomeDTO(s.getOutcome().getId(), s.getOutcome().getDescription(), 
						null, null, null, s.getOutcome().getActive()),
				new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),
						s.getPrerequisite().getEcts(), 
						s.getPrerequisite().isCompulsory(), 
						null, null, null, null, null, null, null, null, null,null), s.getActive()), HttpStatus.OK);
	}
}
