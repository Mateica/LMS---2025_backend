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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.fasterxml.jackson.databind.ser.std.FileSerializer;

import main.dto.AnnouncementDTO;
import main.dto.CountryDTO;
import main.dto.EvaluationDTO;
import main.dto.EvaluationInstrumentDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.FileDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentDTO;
import main.model.Evaluation;
import main.model.Place;
import main.service.EvaluationInstrumentService;
import main.service.EvaluationService;
import main.service.EvaluationTypeService;
import main.service.ExaminationService;
import main.service.FileService;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController implements ControllerInterface<EvaluationDTO> {
	@Autowired
	private EvaluationService service;
	
	@Autowired
	private EvaluationTypeService evaluationTypeService;
	
	@Autowired
	private EvaluationInstrumentService evaluationInstrumentService;
	
	@Autowired
	private ExaminationService examService;
	
	@Autowired
	private FileService fileService;

	@Override
	@GetMapping
	public ResponseEntity<Iterable<EvaluationDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		
		for(Evaluation e : service.findAll()) {
			evaluations.add(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(), 
					new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
							null, e.getEvaluationType().getActive()), 
					new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
							e.getEvaluationInstrument().getName(), 
							new FileDTO(e.getEvaluationInstrument().getFile().getId(),
									e.getEvaluationInstrument().getFile().getName(),
									e.getEvaluationInstrument().getFile().getUrl(),
									e.getEvaluationInstrument().getFile().getDescription(),
									null, null, 
									new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
											e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null, 
											e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
									null, 
									new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
											new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
											e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
											e.getEvaluationInstrument().getFile().getStudent().getLastName(),
											e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
									e.getEvaluationInstrument().getFile().getDocument(),
									e.getEvaluationInstrument().getFile().getActive()), null),
					null, null, null, null));
		}
		return new ResponseEntity<Iterable<EvaluationDTO>>(evaluations, HttpStatus.OK);
	}

	@Override
	@GetMapping("/params")
	public ResponseEntity<Page<EvaluationDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Evaluation> evaluationPage = service.findAll(pageable);

	    List<EvaluationDTO> evaluationDTOs = evaluationPage.stream().map(e -> 
	    new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(), 
				new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
						null, e.getEvaluationType().getActive()), 
				new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
						e.getEvaluationInstrument().getName(), 
						new FileDTO(e.getEvaluationInstrument().getFile().getId(),
								e.getEvaluationInstrument().getFile().getName(),
								e.getEvaluationInstrument().getFile().getUrl(),
								e.getEvaluationInstrument().getFile().getDescription(),
								null, null, 
								new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
										e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
										e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
										e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null, 
										e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
								null, 
								new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
										new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
										e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
										e.getEvaluationInstrument().getFile().getStudent().getLastName(),
										e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
								e.getEvaluationInstrument().getFile().getDocument(),
								e.getEvaluationInstrument().getFile().getActive()), null),
				null, null, null, null)
	    ).collect(Collectors.toList());

	    Page<EvaluationDTO> resultPage = new PageImpl<>(evaluationDTOs, pageable, evaluationPage.getTotalElements());

	    return new ResponseEntity<>(resultPage, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	public ResponseEntity<Iterable<EvaluationDTO>> findAllActive(){
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		
		for(Evaluation e : service.findAllActive()) {
			evaluations.add(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(), 
					new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
							null, e.getEvaluationType().getActive()), 
					new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
							e.getEvaluationInstrument().getName(), 
							new FileDTO(e.getEvaluationInstrument().getFile().getId(),
									e.getEvaluationInstrument().getFile().getName(),
									e.getEvaluationInstrument().getFile().getUrl(),
									e.getEvaluationInstrument().getFile().getDescription(),
									null, null, 
									new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
											e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null, 
											e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
									null, 
									new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
											new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
											e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
											e.getEvaluationInstrument().getFile().getStudent().getLastName(),
											e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
									e.getEvaluationInstrument().getFile().getDocument(),
									e.getEvaluationInstrument().getFile().getActive()), null),
					null, null, null, null));
		}
		return new ResponseEntity<Iterable<EvaluationDTO>>(evaluations, HttpStatus.OK);
	}
	

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<EvaluationDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Evaluation e = service.findById(id).orElse(null);
		
		if(e == null) {
			return new ResponseEntity<EvaluationDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<EvaluationDTO>(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(), 
					new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
							null, e.getEvaluationType().getActive()), 
					new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
							e.getEvaluationInstrument().getName(), 
							new FileDTO(e.getEvaluationInstrument().getFile().getId(),
									e.getEvaluationInstrument().getFile().getName(),
									e.getEvaluationInstrument().getFile().getUrl(),
									e.getEvaluationInstrument().getFile().getDescription(),
									null, null, 
									new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
											e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null, 
											e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
									null, 
									new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
											new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
											e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
											e.getEvaluationInstrument().getFile().getStudent().getLastName(),
											e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
									e.getEvaluationInstrument().getFile().getDocument(),
									e.getEvaluationInstrument().getFile().getActive()), null),
					null, null, null, null), HttpStatus.OK);
	}

	@Override
	@PostMapping
	public ResponseEntity<EvaluationDTO> create(@RequestBody EvaluationDTO t) {
		// TODO Auto-generated method stub
		Evaluation e = service.create(new Evaluation(null, t.getStartTime(), t.getEndTime(), t.getNumberOfPoints(),
													service.findById(t.getId()).get().getEvaluationType(),
													service.findById(t.getId()).get().getEvaluationInstrument(),
													service.findById(t.getId()).get().getExamination(),
													service.findById(t.getId()).get().getSubjectRealization(),
													service.findById(t.getId()).get().getEvaluationGrades(), true));
		
		if(e == null) {
			return new ResponseEntity<EvaluationDTO>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<EvaluationDTO>(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(), 
					new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
							null, e.getEvaluationType().getActive()), 
					new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
							e.getEvaluationInstrument().getName(), 
							new FileDTO(e.getEvaluationInstrument().getFile().getId(),
									e.getEvaluationInstrument().getFile().getName(),
									e.getEvaluationInstrument().getFile().getUrl(),
									e.getEvaluationInstrument().getFile().getDescription(),
									null, null, 
									new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
											e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null, 
											e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
									null, 
									new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
											new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
											e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
											e.getEvaluationInstrument().getFile().getStudent().getLastName(),
											e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
									e.getEvaluationInstrument().getFile().getDocument(),
									e.getEvaluationInstrument().getFile().getActive()), null),
					null, null, null, null), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<EvaluationDTO> update(@RequestBody EvaluationDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Evaluation e = service.findById(id).orElse(null);
		
		if(e == null) {
			return new ResponseEntity<EvaluationDTO>(HttpStatus.NOT_FOUND);
		}
		
		e.setId(t.getId());
		e.setStartTime(t.getStartTime());
		e.setEndTime(t.getEndTime());
		e.setNumberOfPoints(t.getNumberOfPoints());
		e.setEvaluationType(service.findById(t.getId()).get().getEvaluationType());
		e.setEvaluationInstrument(service.findById(t.getId()).get().getEvaluationInstrument());
		e.setExamination(service.findById(t.getId()).get().getExamination());
		e.setSubjectRealization(service.findById(t.getId()).get().getSubjectRealization());
		e.setEvaluationGrades(service.findById(t.getId()).get().getEvaluationGrades());
		e.setActive(t.getActive());
		
		e = service.update(e);
		
		return new ResponseEntity<EvaluationDTO>(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(),
				e.getNumberOfPoints(), 
					new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
							null, e.getEvaluationType().getActive()), 
					new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
							e.getEvaluationInstrument().getName(), 
							new FileDTO(e.getEvaluationInstrument().getFile().getId(),
									e.getEvaluationInstrument().getFile().getName(),
									e.getEvaluationInstrument().getFile().getUrl(),
									e.getEvaluationInstrument().getFile().getDescription(),
									null, null, 
									new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
											e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null, 
											e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
									null, 
									new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
											new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
											e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
											e.getEvaluationInstrument().getFile().getStudent().getLastName(),
											e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
									e.getEvaluationInstrument().getFile().getDocument(),
									e.getEvaluationInstrument().getFile().getActive()), null),
					null, null, null, null), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EvaluationDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
	public ResponseEntity<EvaluationDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Evaluation e = service.findById(id).orElse(null);
		
		if(e == null) {
			return new ResponseEntity<EvaluationDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<EvaluationDTO>(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(), 
					new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
							null, e.getEvaluationType().getActive()), 
					new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
							e.getEvaluationInstrument().getName(), 
							new FileDTO(e.getEvaluationInstrument().getFile().getId(),
									e.getEvaluationInstrument().getFile().getName(),
									e.getEvaluationInstrument().getFile().getUrl(),
									e.getEvaluationInstrument().getFile().getDescription(),
									null, null, 
									new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
											e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
											e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null, 
											e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
									null, 
									new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
											new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
											e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
											e.getEvaluationInstrument().getFile().getStudent().getLastName(),
											e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
									e.getEvaluationInstrument().getFile().getDocument(),
									e.getEvaluationInstrument().getFile().getActive()), null),
					null, null, null, null), HttpStatus.OK);
	}
}
