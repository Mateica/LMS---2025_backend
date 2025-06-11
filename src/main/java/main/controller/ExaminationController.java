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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.EvaluationDTO;
import main.dto.EvaluationInstrumentDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.ExaminationDTO;
import main.dto.FileDTO;
import main.dto.NoteDTO;
import main.dto.StudentDTO;
import main.dto.StudentOnYearDTO;
import main.dto.YearOfStudyDTO;
import main.model.EvaluationInstrument;
import main.model.Examination;
import main.model.File;
import main.service.ExaminationService;
import main.service.StudentOnYearService;

@RestController
@RequestMapping("/api/examinations")
public class ExaminationController implements ControllerInterface<ExaminationDTO> {
	@Autowired
	private ExaminationService service;
	
	@Autowired
	private StudentOnYearService studentOnYearService;

	@Override
	@Secured({"ADMIN", "TEACHER","STUDENT", "STAFF"})
	@GetMapping
	public ResponseEntity<Iterable<ExaminationDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<ExaminationDTO> exams = new ArrayList<ExaminationDTO>();
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		
		for(Examination e : service.findAll()) {
			notes = (ArrayList<NoteDTO>) e.getNotes()
					.stream()
					.map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
					.collect(Collectors.toList());
			
			evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev -> 
			new EvaluationDTO(ev.getId(), 
							ev.getStartTime(),
							ev.getEndTime(),
							ev.getNumberOfPoints(),
							new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
									null, ev.getEvaluationType().getActive()),
							new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
									null, ev.getEvaluationInstrument().getActive()),
							null,null, null,
							ev.getActive()))
								.collect(Collectors.toList());
			
			
			exams.add(new ExaminationDTO(
					e.getId(),
					e.getNumberOfPoints(),
					notes, evaluations, 
					new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
							new StudentDTO(e.getStudentOnYear().getStudent().getId(),
									null, e.getStudentOnYear().getStudent().getFirstName(),
									e.getStudentOnYear().getStudent().getLastName(),
									e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null),
							e.getStudentOnYear().getIndexNumber(),
							new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
									e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
									null, null),
							null, null, null),
					e.getActive()));
		}
		
		return new ResponseEntity<Iterable<ExaminationDTO>>(exams, HttpStatus.OK);
	}
	
	@Override
	@Secured({"ADMIN", "TEACHER","STUDENT", "STAFF"})
	@GetMapping("/params")
	public ResponseEntity<Page<ExaminationDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Examination> examPage = service.findAll(pageable);

		List<ExaminationDTO> examDTOs = examPage.stream().map(e -> {
		    List<NoteDTO> notes = e.getNotes().stream().map(n ->
		        new NoteDTO(n.getId(), n.getContent(), null, n.getActive())
		    ).collect(Collectors.toList());

		    List<EvaluationDTO> evaluations = e.getEvaluations().stream().map(ev -> 
		        new EvaluationDTO(
		            ev.getId(), 
		            ev.getStartTime(),
		            ev.getEndTime(),
		            ev.getNumberOfPoints(),
		            new EvaluationTypeDTO(
		                ev.getEvaluationType().getId(), 
		                ev.getEvaluationType().getName(),
		                null, 
		                ev.getEvaluationType().getActive()
		            ),
		            new EvaluationInstrumentDTO(
		                ev.getEvaluationInstrument().getId(), 
		                ev.getEvaluationInstrument().getName(),
		                null, 
		                ev.getEvaluationInstrument().getActive()
		            ),
		            null, null, null,
		            ev.getActive()
		        )
		    ).collect(Collectors.toList());

		    return new ExaminationDTO(
		        e.getId(),
		        e.getNumberOfPoints(),
		        notes,
		        evaluations,
		        new StudentOnYearDTO(
		            e.getStudentOnYear().getId(),
		            e.getStudentOnYear().getDateOfApplication(),
		            new StudentDTO(
		                e.getStudentOnYear().getStudent().getId(),
		                null,
		                e.getStudentOnYear().getStudent().getFirstName(),
		                e.getStudentOnYear().getStudent().getLastName(),
		                e.getStudentOnYear().getStudent().getUmcn(),
		                null, null, null, null, null
		            ),
		            e.getStudentOnYear().getIndexNumber(),
		            new YearOfStudyDTO(
		                e.getStudentOnYear().getYearOfStudy().getId(),
		                e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
		                null, null
		            ),
		            null, null, null
		        ),
		        e.getActive()
		    );
		}).collect(Collectors.toList());

		Page<ExaminationDTO> resultPage = new PageImpl<>(examDTOs, pageable, examPage.getTotalElements());
		return new ResponseEntity<>(resultPage, HttpStatus.OK);

	}
	
	@Secured({"ADMIN", "TEACHER","STUDENT", "STAFF"})
	@GetMapping("/active")
	public ResponseEntity<Iterable<ExaminationDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<ExaminationDTO> exams = new ArrayList<ExaminationDTO>();
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		
		for(Examination e : service.findAllActive()) {
			notes = (ArrayList<NoteDTO>) e.getNotes()
					.stream()
					.map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
					.collect(Collectors.toList());
			
			evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev -> 
			new EvaluationDTO(ev.getId(), 
							ev.getStartTime(),
							ev.getEndTime(),
							ev.getNumberOfPoints(),
							new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
									null, ev.getEvaluationType().getActive()),
							new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
									null, ev.getEvaluationInstrument().getActive()),
							null,null, null,
							ev.getActive()))
								.collect(Collectors.toList());
			
			
			exams.add(new ExaminationDTO(e.getId(),e.getNumberOfPoints(),
					notes, evaluations, 
					new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
							new StudentDTO(e.getStudentOnYear().getStudent().getId(),
									null, e.getStudentOnYear().getStudent().getFirstName(),
									e.getStudentOnYear().getStudent().getLastName(),
									e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null),
							e.getStudentOnYear().getIndexNumber(),
							new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
									e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
									null, null),
							null, null, null),
					e.getActive()));
		}
		
		return new ResponseEntity<Iterable<ExaminationDTO>>(exams, HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN", "TEACHER","STUDENT", "STAFF"})
	@GetMapping("/{id}")
	public ResponseEntity<ExaminationDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Examination e = service.findById(id).orElse(null);
		
		if(e == null) {
			return new ResponseEntity<ExaminationDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		
		notes = (ArrayList<NoteDTO>) e.getNotes()
				.stream()
				.map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
				.collect(Collectors.toList());
		
		evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev -> 
		new EvaluationDTO(ev.getId(), 
						ev.getStartTime(),
						ev.getEndTime(),
						ev.getNumberOfPoints(),
						new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
								null, ev.getEvaluationType().getActive()),
						new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
								null, ev.getEvaluationInstrument().getActive()),
						null,null, null,
						ev.getActive()))
							.collect(Collectors.toList());
		
		
		return new ResponseEntity<ExaminationDTO>(new ExaminationDTO(e.getId(),e.getNumberOfPoints(),
				notes, evaluations, 
				new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
						new StudentDTO(e.getStudentOnYear().getStudent().getId(),
								null, e.getStudentOnYear().getStudent().getFirstName(),
								e.getStudentOnYear().getStudent().getLastName(),
								e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null),
						e.getStudentOnYear().getIndexNumber(),
						new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
								e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
								null, null),
						null, null, null),
				e.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN", "TEACHER"})
	@PostMapping
	public ResponseEntity<ExaminationDTO> create(@RequestBody ExaminationDTO t) {
		// TODO Auto-generated method stub
		Examination e = service.create(new Examination(null, t.getNumberOfPoints(), 
				service.findById(t.getId()).get().getNotes(),
				service.findById(t.getId()).get().getEvaluations(),
				service.findById(t.getId()).get().getStudentOnYear(), true));
		
		if(e == null) {
			return new ResponseEntity<ExaminationDTO>(HttpStatus.BAD_REQUEST);
		}
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		
		notes = (ArrayList<NoteDTO>) e.getNotes()
				.stream()
				.map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
				.collect(Collectors.toList());
		
		evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev -> 
		new EvaluationDTO(ev.getId(), 
						ev.getStartTime(),
						ev.getEndTime(),
						ev.getNumberOfPoints(),
						new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
								null, ev.getEvaluationType().getActive()),
						new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
								null, ev.getEvaluationInstrument().getActive()),
						null,null, null,
						ev.getActive()))
							.collect(Collectors.toList());
		
		
		return new ResponseEntity<ExaminationDTO>(new ExaminationDTO(e.getId(),e.getNumberOfPoints(),
				notes, evaluations, 
				new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
						new StudentDTO(e.getStudentOnYear().getStudent().getId(),
								null, e.getStudentOnYear().getStudent().getFirstName(),
								e.getStudentOnYear().getStudent().getLastName(),
								e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null),
						e.getStudentOnYear().getIndexNumber(),
						new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
								e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
								null, null),
						null, null, null),
				e.getActive()), HttpStatus.CREATED);
	}

	@Override
	@Secured({"ADMIN", "TEACHER"})
	@PutMapping("/{id}")
	public ResponseEntity<ExaminationDTO> update(@RequestBody ExaminationDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Examination e = service.findById(id).orElse(null);
		
		if(e == null) {
			return new ResponseEntity<ExaminationDTO>(HttpStatus.NOT_FOUND);
		}
		
		List<NoteDTO> notes = (ArrayList<NoteDTO>) e.getNotes()
				.stream()
				.map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
				.collect(Collectors.toList());
		
	List<EvaluationDTO>	evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev -> 
		new EvaluationDTO(ev.getId(), 
						ev.getStartTime(),
						ev.getEndTime(),
						ev.getNumberOfPoints(),
						new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
								null, ev.getEvaluationType().getActive()),
						new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
								null, ev.getEvaluationInstrument().getActive()),
						null,null, null,
						ev.getActive()))
							.collect(Collectors.toList());
		
		e.setId(t.getId());
		e.setNumberOfPoints(t.getNumberOfPoints());
		e.setNotes(service.findById(t.getId()).get().getNotes());
		e.setEvaluations(service.findById(t.getId()).get().getEvaluations());
		e.setStudentOnYear(service.findById(t.getId()).get().getStudentOnYear());
		e.setActive(t.getActive());
		
		e = service.update(e);
		
		return new ResponseEntity<ExaminationDTO>(new ExaminationDTO(e.getId(),e.getNumberOfPoints(),
				notes, evaluations, 
				new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
						new StudentDTO(e.getStudentOnYear().getStudent().getId(),
								null, e.getStudentOnYear().getStudent().getFirstName(),
								e.getStudentOnYear().getStudent().getLastName(),
								e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null),
						e.getStudentOnYear().getIndexNumber(),
						new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
								e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
								null, null),
						null, null, null),
				e.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN", "TEACHER"})
	@DeleteMapping("/{id}")
	public ResponseEntity<ExaminationDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Secured({"ADMIN", "TEACHER","STAFF"})
	@PatchMapping("/{id}")
	public ResponseEntity<ExaminationDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Examination e = service.findById(id).orElse(null);
		
		if(e == null) {
			return new ResponseEntity<ExaminationDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		
		notes = (ArrayList<NoteDTO>) e.getNotes()
				.stream()
				.map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
				.collect(Collectors.toList());
		
		evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev -> 
		new EvaluationDTO(ev.getId(), 
						ev.getStartTime(),
						ev.getEndTime(),
						ev.getNumberOfPoints(),
						new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
								null, ev.getEvaluationType().getActive()),
						new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
								null, ev.getEvaluationInstrument().getActive()),
						null,null, null,
						ev.getActive()))
							.collect(Collectors.toList());
		
		service.softDelete(id);
		
		return new ResponseEntity<ExaminationDTO>(new ExaminationDTO(e.getId(),e.getNumberOfPoints(),
				notes, evaluations, 
				new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
						new StudentDTO(e.getStudentOnYear().getStudent().getId(),
								null, e.getStudentOnYear().getStudent().getFirstName(),
								e.getStudentOnYear().getStudent().getLastName(),
								e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null),
						e.getStudentOnYear().getIndexNumber(),
						new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
								e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
								null, null),
						null, null, null),
				e.getActive()), HttpStatus.OK);
	}
}
