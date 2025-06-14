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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.AddressDTO;
import main.dto.CountryDTO;
import main.dto.EvaluationDTO;
import main.dto.EvaluationGradeDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.ExaminationDTO;
import main.dto.FacultyDTO;
import main.dto.NoteDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentDTO;
import main.dto.StudentOnYearDTO;
import main.dto.SubjectDTO;
import main.dto.SubjectRealizationDTO;
import main.dto.TeacherDTO;
import main.dto.YearOfStudyDTO;
import main.model.Evaluation;
import main.model.EvaluationGrade;
import main.model.StudentOnYear;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.service.EvaluationGradeService;
import main.service.EvaluationService;
import main.service.EvaluationTypeService;
import main.service.TeacherService;

@RestController
@RequestMapping("/api/evaluationGrades")
public class EvaluationGradeController implements ControllerInterface<EvaluationGradeDTO> {
	@Autowired
	private EvaluationGradeService service;
	
	@Autowired
	private EvaluationService evalService;
	
	@Autowired
	private TeacherService teacherService;

	@Override
	@GetMapping
	@Secured({"ADMIN","TEACHER","STUDENT","STAFF"})
	public ResponseEntity<Iterable<EvaluationGradeDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<EvaluationGradeDTO> grades = new ArrayList<EvaluationGradeDTO>();
		
		for(EvaluationGrade g : service.findAll()) {
			grades.add(new EvaluationGradeDTO(g.getId(),
					new EvaluationDTO(g.getEvaluation().getId(), g.getEvaluation().getStartTime(),
										g.getEvaluation().getEndTime(), g.getEvaluation().getNumberOfPoints(),
										null, null, null, null, null, g.getEvaluation().getActive()),
					new TeacherDTO(g.getTeacher().getId(), new RegisteredUserDTO(g.getTeacher().getUser().getUsername(),
							null, g.getTeacher().getUser().getEmail()),
									g.getTeacher().getFirstName(), g.getTeacher().getLastName(),
									g.getTeacher().getUmcn(),
									g.getTeacher().getBiography(),
									null, null, null, null, null, null),
					g.getDateTimeEvaluated(), g.getMark(), g.getActive()));
		}
		
		return new ResponseEntity<Iterable<EvaluationGradeDTO>>(grades, HttpStatus.OK);
	}

	@Override
	@GetMapping("/params")
	@Secured({"ADMIN","TEACHER","STUDENT","STAFF"})
	public ResponseEntity<Page<EvaluationGradeDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		// TODO Auto-generated method stub
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<EvaluationGrade> gradePage = service.findAll(pageable);
	    
	    List<EvaluationGradeDTO> gradeDTOs = gradePage.stream().map(g -> new EvaluationGradeDTO(g.getId(),
				new EvaluationDTO(g.getEvaluation().getId(), g.getEvaluation().getStartTime(),
						g.getEvaluation().getEndTime(), g.getEvaluation().getNumberOfPoints(),
						null, null, null, null, null, g.getEvaluation().getActive()),
	new TeacherDTO(g.getTeacher().getId(), new RegisteredUserDTO(g.getTeacher().getUser().getUsername(),
			null, g.getTeacher().getUser().getEmail()),
					g.getTeacher().getFirstName(), g.getTeacher().getLastName(),
					g.getTeacher().getUmcn(),
					g.getTeacher().getBiography(),
					null, null, null, null, null, null),
	g.getDateTimeEvaluated(), g.getMark(), g.getActive())).collect(Collectors.toList());
	    
	    Page<EvaluationGradeDTO> resultPage = new PageImpl<>(gradeDTOs, pageable, gradePage.getTotalElements());
		
		return new ResponseEntity<Page<EvaluationGradeDTO>>(resultPage, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	@Secured({"ADMIN","TEACHER","STUDENT","STAFF"})
	public ResponseEntity<Iterable<EvaluationGradeDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<EvaluationGradeDTO> grades = new ArrayList<EvaluationGradeDTO>();
		
		for(EvaluationGrade g : service.findAllActive()) {
			EvaluationTypeDTO evalTypeDTO = new EvaluationTypeDTO(
				null,
				g.getEvaluation().getEvaluationType().getName(),
				null,
				null
			);
			
			SubjectRealization subjectRealization = g.getEvaluation().getSubjectRealization();
			SubjectRealizationDTO subjectRealizationDTO = new SubjectRealizationDTO(
				null,
				null,
				null,
				null,
				new SubjectDTO(
					null,
					(subjectRealization == null) ? null : subjectRealization.getSubject().getName(),
					null,
					null
				),
				null
			);
					
			grades.add(
					new EvaluationGradeDTO(g.getId(),
							new EvaluationDTO(
									g.getEvaluation().getId(),
									g.getEvaluation().getStartTime(),
									g.getEvaluation().getEndTime(),
									g.getEvaluation().getNumberOfPoints(),
									evalTypeDTO,
									null,
									null,
									subjectRealizationDTO,
									null,
									g.getEvaluation().getActive()),
					new TeacherDTO(
							g.getTeacher().getId(),
							new RegisteredUserDTO(
									g.getTeacher().getUser().getUsername(),
									null,
									g.getTeacher().getUser().getEmail()),
									g.getTeacher().getFirstName(),
									g.getTeacher().getLastName(),
									g.getTeacher().getUmcn(),
									g.getTeacher().getBiography(),
									null,
									null,
									null,
									null,
									null,
									null
								),
					g.getDateTimeEvaluated(),
					g.getMark(),
					g.getActive()));
		}
		
		return new ResponseEntity<Iterable<EvaluationGradeDTO>>(grades, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN","TEACHER","STUDENT","STAFF"})
	public ResponseEntity<EvaluationGradeDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		EvaluationGrade g = service.findById(id).orElse(null);
		
		if(g == null) {
			return new ResponseEntity<EvaluationGradeDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<EvaluationGradeDTO>(new EvaluationGradeDTO(g.getId(),
					new EvaluationDTO(g.getEvaluation().getId(), g.getEvaluation().getStartTime(),
										g.getEvaluation().getEndTime(), g.getEvaluation().getNumberOfPoints(),
										null, null, null, null, null, g.getEvaluation().getActive()),
					new TeacherDTO(g.getTeacher().getId(), new RegisteredUserDTO(g.getTeacher().getUser().getUsername(),
							null, g.getTeacher().getUser().getEmail()),
									g.getTeacher().getFirstName(), g.getTeacher().getLastName(),
									g.getTeacher().getUmcn(),
									g.getTeacher().getBiography(),
									null, null, null, null, null, null),
					g.getDateTimeEvaluated(), g.getMark(), g.getActive()),HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN","TEACHER"})
	public ResponseEntity<EvaluationGradeDTO> create(@RequestBody EvaluationGradeDTO t) {
		Evaluation evaluation = evalService.findById(t.getEvaluation().getId()).get();
		Teacher teacher = teacherService.findById(t.getTeacher().getId()).get();
		EvaluationGrade g = service.create(
			new EvaluationGrade(
				null, 
				evaluation, 
				teacher,
				t.getDateTimeEvaluated(),
				t.getMark(),
				true
			)
		);
		
		if(g == null) {
			return new ResponseEntity<EvaluationGradeDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<EvaluationGradeDTO>(new EvaluationGradeDTO(g.getId(),
					new EvaluationDTO(g.getEvaluation().getId(), g.getEvaluation().getStartTime(),
										g.getEvaluation().getEndTime(), g.getEvaluation().getNumberOfPoints(),
										null, null, null, null, null, g.getEvaluation().getActive()),
					new TeacherDTO(g.getTeacher().getId(), new RegisteredUserDTO(g.getTeacher().getUser().getUsername(),
							null, g.getTeacher().getUser().getEmail()),
									g.getTeacher().getFirstName(), g.getTeacher().getLastName(),
									g.getTeacher().getUmcn(),
									g.getTeacher().getBiography(),
									null, null, null, null, null, null),
					g.getDateTimeEvaluated(), g.getMark(), g.getActive()),HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<EvaluationGradeDTO> update(@RequestBody EvaluationGradeDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		EvaluationGrade g = service.findById(id).orElse(null);
		
		if(g == null) {
			return new ResponseEntity<EvaluationGradeDTO>(HttpStatus.NOT_FOUND);
		}
		
		g.setId(t.getId());
		g.setEvaluation(service.findById(t.getId()).get().getEvaluation());
		g.setTeacher(service.findById(t.getId()).get().getTeacher());
		g.setDateTimeEvaluated(t.getDateTimeEvaluated());
		g.setMark(t.getMark());
		g.setActive(t.getActive());
		
		return new ResponseEntity<EvaluationGradeDTO>(new EvaluationGradeDTO(g.getId(),
					new EvaluationDTO(g.getEvaluation().getId(), g.getEvaluation().getStartTime(),
										g.getEvaluation().getEndTime(), g.getEvaluation().getNumberOfPoints(),
										null, null, null, null, null, g.getEvaluation().getActive()),
					new TeacherDTO(g.getTeacher().getId(), new RegisteredUserDTO(g.getTeacher().getUser().getUsername(),
							null, g.getTeacher().getUser().getEmail()),
									g.getTeacher().getFirstName(), g.getTeacher().getLastName(),
									g.getTeacher().getUmcn(),
									g.getTeacher().getBiography(),
									null, null, null, null, null, null),
					g.getDateTimeEvaluated(), g.getMark(), g.getActive()),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EvaluationGradeDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PatchMapping("/{id}")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<EvaluationGradeDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		EvaluationGrade g = service.findById(id).orElse(null);
		
		if(g == null) {
			return new ResponseEntity<EvaluationGradeDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<EvaluationGradeDTO>(new EvaluationGradeDTO(g.getId(),
					new EvaluationDTO(g.getEvaluation().getId(), g.getEvaluation().getStartTime(),
										g.getEvaluation().getEndTime(), g.getEvaluation().getNumberOfPoints(),
										null, null, null, null, null, g.getEvaluation().getActive()),
					new TeacherDTO(g.getTeacher().getId(), new RegisteredUserDTO(g.getTeacher().getUser().getUsername(),
							null, g.getTeacher().getUser().getEmail()),
									g.getTeacher().getFirstName(), g.getTeacher().getLastName(),
									g.getTeacher().getUmcn(),
									g.getTeacher().getBiography(),
									null, null, null, null, null, null),
					g.getDateTimeEvaluated(), g.getMark(), g.getActive()),HttpStatus.OK);
	}
}
