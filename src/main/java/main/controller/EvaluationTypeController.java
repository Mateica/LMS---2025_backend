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
import main.model.Evaluation;
import main.model.EvaluationType;
import main.service.EvaluationTypeService;

@RestController
@RequestMapping("/api/evaluationTypes")
public class EvaluationTypeController implements ControllerInterface<EvaluationTypeDTO> {
	@Autowired
	private EvaluationTypeService service;

	@Override
	@Secured({"ADMIN, TEACHER"})
	@GetMapping
	public ResponseEntity<Iterable<EvaluationTypeDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<EvaluationTypeDTO> evaluationTypes = new ArrayList<EvaluationTypeDTO>();
		
		for(EvaluationType et : service.findAll()) {
			evaluationTypes.add(new EvaluationTypeDTO(et.getId(), et.getName(), null, et.getActive()));
		}
		
		return new ResponseEntity<Iterable<EvaluationTypeDTO>>(evaluationTypes, HttpStatus.OK);
	}
	
	@Override
	@Secured({"ADMIN, TEACHER"})
	@GetMapping("/params")
	public ResponseEntity<Page<EvaluationTypeDTO>> findAll(@RequestParam(defaultValue = "0") int page,
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
	            null, et.getActive()
	        )
	    ).collect(Collectors.toList());

	    Page<EvaluationTypeDTO> resultPage = new PageImpl<EvaluationTypeDTO>(evaluationTypeDTOs, pageable, evaluationTypePage.getTotalElements());

	    return new ResponseEntity<Page<EvaluationTypeDTO>>(resultPage, HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN, TEACHER"})
	@GetMapping("/{id}")
	public ResponseEntity<EvaluationTypeDTO> findById(Long id) {
		// TODO Auto-generated method stub
		EvaluationType et = service.findById(id).orElse(null);
		
		if(et == null) {
			return new ResponseEntity<EvaluationTypeDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EvaluationTypeDTO>(new EvaluationTypeDTO(et.getId(), et.getName(), null, et.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN, TEACHER"})
	@PostMapping
	public ResponseEntity<EvaluationTypeDTO> create(EvaluationTypeDTO t) {
		// TODO Auto-generated method stub
		EvaluationType et = service.create(new EvaluationType(null, t.getName(), new ArrayList<Evaluation>(), true));
		
		if(et == null) {
			return new ResponseEntity<EvaluationTypeDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<EvaluationTypeDTO>(new EvaluationTypeDTO(et.getId(), et.getName(), null, et.getActive()), HttpStatus.CREATED);
	}

	@Override
	@Secured({"ADMIN, TEACHER"})
	@PutMapping("/{id}")
	public ResponseEntity<EvaluationTypeDTO> update(EvaluationTypeDTO t, Long id) {
		// TODO Auto-generated method stub
		EvaluationType et = service.findById(id).orElse(null);
		
		if(et == null) {
			return new ResponseEntity<EvaluationTypeDTO>(HttpStatus.NOT_FOUND);
		}
		
		et.setId(t.getId());
		et.setName(t.getName());
		et.setActive(t.getActive());
		
		et = service.update(et)
;
		return new ResponseEntity<EvaluationTypeDTO>(new EvaluationTypeDTO(et.getId(), et.getName(), null, et.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN, TEACHER"})
	@DeleteMapping("/{id}")
	public ResponseEntity<EvaluationTypeDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Secured({"ADMIN, TEACHER"})
	@PutMapping("/softDelete/{id}")
	public ResponseEntity<EvaluationTypeDTO> softDelete(Long id) {
		// TODO Auto-generated method stub
		EvaluationType et = service.findById(id).orElse(null);
		
		if(et == null) {
			return new ResponseEntity<EvaluationTypeDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<EvaluationTypeDTO>(new EvaluationTypeDTO(et.getId(), et.getName(), null, et.getActive()), HttpStatus.OK);
	}
}
