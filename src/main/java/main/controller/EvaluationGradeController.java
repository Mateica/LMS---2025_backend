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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.AddressDTO;
import main.dto.CountryDTO;
import main.dto.EvaluationDTO;
import main.dto.EvaluationGradeDTO;
import main.dto.ExaminationDTO;
import main.dto.FacultyDTO;
import main.dto.NoteDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentDTO;
import main.dto.StudentOnYearDTO;
import main.dto.SubjectDTO;
import main.dto.TeacherDTO;
import main.dto.YearOfStudyDTO;
import main.model.EvaluationGrade;
import main.model.StudentOnYear;
import main.service.EvaluationGradeService;

@RestController
@RequestMapping("/api/evaluationGrades")
public class EvaluationGradeController implements ControllerInterface<EvaluationGradeDTO> {
	@Autowired
	private EvaluationGradeService service;

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
	@Secured({"ADMIN","TEACHER"})
	public ResponseEntity<EvaluationGradeDTO> create(@RequestBody EvaluationGradeDTO t) {
		// TODO Auto-generated method stub
		EvaluationGrade g = service.create(new EvaluationGrade(null, 
											service.findById(t.getId()).get().getEvaluation(), 
											service.findById(t.getId()).get().getTeacher(),
											t.getDateTimeEvaluated(), t.getMark(), true));
		
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
