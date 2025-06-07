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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.EvaluationInstrumentDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.FileDTO;
import main.model.EvaluationInstrument;
import main.service.EvaluationInstrumentService;
import main.model.File;

@RestController
@RequestMapping("api/evaluationInstruments")
public class EvaluationInstrumentController implements ControllerInterface<EvaluationInstrumentDTO> {
	@Autowired
	private EvaluationInstrumentService service;

	@Override
	@Secured({"ADMIN", "TEACHER"})
	@GetMapping
	public ResponseEntity<Iterable<EvaluationInstrumentDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<EvaluationInstrumentDTO> evaluationInstruments = new ArrayList<EvaluationInstrumentDTO>();
		
		for(EvaluationInstrument ei : service.findAll()) {
			evaluationInstruments.add(new EvaluationInstrumentDTO(ei.getId(),ei.getName(),
					new FileDTO(ei.getFile().getId(), ei.getFile().getUrl(), ei.getFile().getDescription(),
							null, null, null, null, null, null, null, ei.getFile().getActive()),
					ei.getActive()));
		}
		
		return new ResponseEntity<Iterable<EvaluationInstrumentDTO>>(evaluationInstruments, HttpStatus.OK);
	}
	
	@Override
	@Secured({"ADMIN", "TEACHER"})
	@GetMapping("/params")
	public ResponseEntity<Page<EvaluationInstrumentDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<EvaluationInstrument> evaluationTypePage = service.findAll(pageable);

	    List<EvaluationInstrumentDTO> evaluationTypeDTOs = evaluationTypePage.stream().map(ei -> 
	    new EvaluationInstrumentDTO(
	            ei.getId(),
	            ei.getName(),
	            new FileDTO(ei.getFile().getId(), ei.getFile().getUrl(), ei.getFile().getDescription(),
						null, null, null, null, null, null, null, ei.getFile().getActive()),
	            ei.getActive()
	        )
	    ).collect(Collectors.toList());

	    Page<EvaluationInstrumentDTO> resultPage = new PageImpl<EvaluationInstrumentDTO>(evaluationTypeDTOs, pageable, evaluationTypePage.getTotalElements());

	    return new ResponseEntity<Page<EvaluationInstrumentDTO>>(resultPage, HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN", "TEACHER"})
	@GetMapping("/{id}")
	public ResponseEntity<EvaluationInstrumentDTO> findById(Long id) {
		// TODO Auto-generated method stub
		EvaluationInstrument ei = service.findById(id).orElse(null);
		
		if(ei == null) {
			return new ResponseEntity<EvaluationInstrumentDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EvaluationInstrumentDTO>(new EvaluationInstrumentDTO(ei.getId(), ei.getName(),
				new FileDTO(ei.getFile().getId(), ei.getFile().getUrl(), ei.getFile().getDescription(),
						null, null, null, null, null, null, null, ei.getFile().getActive()),ei.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN", "TEACHER"})
	@PostMapping
	public ResponseEntity<EvaluationInstrumentDTO> create(EvaluationInstrumentDTO t) {
		// TODO Auto-generated method stub
		EvaluationInstrument ei = service.create(new EvaluationInstrument(null, t.getName(),null, new File(t.getFile().getId(), t.getFile().getUrl(), t.getFile().getDescription(),
				null, null, null, null, null, null, null, t.getFile().getActive()), true));
		
		if(ei == null) {
			return new ResponseEntity<EvaluationInstrumentDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<EvaluationInstrumentDTO>(new EvaluationInstrumentDTO(ei.getId(), ei.getName(),
				new FileDTO(ei.getFile().getId(), ei.getFile().getUrl(), ei.getFile().getDescription(),
						null, null, null, null, null, null, null, ei.getFile().getActive()), ei.getActive()), HttpStatus.CREATED);
	}

	@Override
	@Secured({"ADMIN", "TEACHER"})
	@PutMapping("/{id}")
	public ResponseEntity<EvaluationInstrumentDTO> update(EvaluationInstrumentDTO t, Long id) {
		// TODO Auto-generated method stub
		EvaluationInstrument ei = service.findById(id).orElse(null);
		
		if(ei == null) {
			return new ResponseEntity<EvaluationInstrumentDTO>(HttpStatus.NOT_FOUND);
		}
		
		ei.setId(t.getId());
		ei.setName(t.getName());
		ei.setActive(t.getActive());
		ei.setFile(new File(t.getFile().getId(), t.getFile().getUrl(), t.getFile().getDescription(),
				null, null, null, null, null, null, null, t.getFile().getActive()));
		
		ei = service.update(ei);
		
		return new ResponseEntity<EvaluationInstrumentDTO>(new EvaluationInstrumentDTO(ei.getId(), ei.getName(),
				new FileDTO(ei.getFile().getId(), ei.getFile().getUrl(), ei.getFile().getDescription(),
						null, null, null, null, null, null, null, ei.getFile().getActive()),ei.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN", "TEACHER"})
	@DeleteMapping("/{id}")
	public ResponseEntity<EvaluationInstrumentDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Secured({"ADMIN", "TEACHER"})
	@PatchMapping("/{id}")
	public ResponseEntity<EvaluationInstrumentDTO> softDelete(Long id) {
		// TODO Auto-generated method stub
		EvaluationInstrument ei = service.findById(id).orElse(null);
		
		if(ei == null) {
			return new ResponseEntity<EvaluationInstrumentDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<EvaluationInstrumentDTO>(new EvaluationInstrumentDTO(ei.getId(), ei.getName(),
				new FileDTO(ei.getFile().getId(), ei.getFile().getUrl(), ei.getFile().getDescription(),
						null, null, null, null, null, null, null, ei.getFile().getActive()),ei.getActive()), HttpStatus.OK);
	}
}
