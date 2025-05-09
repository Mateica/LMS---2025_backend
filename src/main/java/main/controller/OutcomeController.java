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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.EvaluationTypeDTO;
import main.dto.OutcomeDTO;
import main.model.EvaluationType;
import main.model.Outcome;
import main.service.EvaluationTypeService;
import main.service.OutcomeService;

@RestController
@RequestMapping("/api/outcomes")
public class OutcomeController implements ControllerInterface<OutcomeDTO> {
	@Autowired
	private OutcomeService service;

	@Override
	@Secured("{ADMIN, TEACHER}")
	@GetMapping
	public ResponseEntity<Iterable<OutcomeDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<OutcomeDTO> syllabi = new ArrayList<OutcomeDTO>();
		
		for(Outcome o : service.findAll()) {
			syllabi.add(new OutcomeDTO(et.getId(), et.getName(), et.getActive()));
		}
		
		return new ResponseEntity<Iterable<OutcomeDTO>>(syllabi, HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/params")
	public ResponseEntity<Page<OutcomeDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<EvaluationType> evaluationTypePage = service.findAll(pageable);

	    List<EvaluationTypeDTO> evaluationTypeDTOs = evaluationTypePage.stream().map(et -> 
	    new EvaluationTypeDTO(
	            et.getId(),
	            et.getName(),
	            et.getActive()
	        )
	    ).collect(Collectors.toList());

	    Page<EvaluationTypeDTO> resultPage = new PageImpl<OutcomeDTO>(evaluationTypeDTOs, pageable, evaluationTypePage.getTotalElements());

	    return new ResponseEntity<Page<OutcomeDTO>>(resultPage, HttpStatus.OK);
	}

	@Override
	@Secured("{ADMIN, TEACHER}")
	@GetMapping("/{id}")
	public ResponseEntity<OutcomeDTO> findById(Long id) {
		// TODO Auto-generated method stub
		EvaluationType et = service.findById(id).orElse(null);
		
		if(et == null) {
			return new ResponseEntity<OutcomeDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OutcomeDTO>(new OutcomeDTO(et.getId(), et.getName(), et.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured("{ADMIN, TEACHER}")
	@PostMapping
	public ResponseEntity<OutcomeDTO> create(OutcomeDTO t) {
		// TODO Auto-generated method stub
		Outcome o = service.create(new Outcome(null, t.getName(), true));
		
		if(o == null) {
			return new ResponseEntity<OutcomeDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<OutcomeDTO>(new EvaluationTypeDTO(et.getId(), et.getName(), et.getActive()), HttpStatus.CREATED);
	}

	@Override
	@Secured("{ADMIN, TEACHER}")
	@PutMapping("/{id}")
	public ResponseEntity<OutcomeDTO> update(OutcomeDTO t, Long id) {
		// TODO Auto-generated method stub
		Outcome o = service.findById(id).orElse(null);
		
		if(o == null) {
			return new ResponseEntity<OutcomeDTO>(HttpStatus.NOT_FOUND);
		}
		
		et.setId(t.getId());
		et.setName(t.getName());
		et.setActive(t.getActive());
		
		et = service.update(et)
;
		return new ResponseEntity<EvaluationTypeDTO>(new OutcomeDTO(et.getId(), et.getName(), et.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured("{ADMIN, TEACHER}")
	@DeleteMapping("/{id}")
	public ResponseEntity<OutcomeDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Secured("{ADMIN, TEACHER}")
	@PutMapping("/softDelete/{id}")
	public ResponseEntity<OutcomeDTO> softDelete(Long id) {
		// TODO Auto-generated method stub
		Outcome o = service.findById(id).orElse(null);
		
		if(o == null) {
			return new ResponseEntity<OutcomeDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<OutcomeDTO>(new OutcomeDTO(et.getId(), et.getName(), et.getActive()), HttpStatus.OK);
	}
}
