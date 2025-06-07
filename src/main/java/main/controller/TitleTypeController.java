package main.controller;

import java.util.ArrayList;
import java.util.List;

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

import main.dto.StudentDTO;
import main.dto.TitleTypeDTO;
import main.model.Student;
import main.model.TitleType;
import main.service.TitleTypeService;

@RestController
@RequestMapping("/api/titleTypes")
public class TitleTypeController implements ControllerInterface<TitleTypeDTO> {
	@Autowired
	private TitleTypeService service;
	
	@Override
	@GetMapping
	@Secured({"ADMIN","TEACHER","STUDENT","STAFF","USER"})
	public ResponseEntity<Iterable<TitleTypeDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<TitleTypeDTO> titleTypes = new ArrayList<TitleTypeDTO>();
		
		for(TitleType tt : service.findAll()) {
			titleTypes.add(new TitleTypeDTO(tt.getId(), tt.getName(), tt.getActive()));
		}
				return new ResponseEntity<Iterable<TitleTypeDTO>>(titleTypes, HttpStatus.OK);
	}

	@Override
	@GetMapping("/params")
	@Secured({"ADMIN","TEACHER","STUDENT","STAFF","USER"})
	public ResponseEntity<Page<TitleTypeDTO>> findAll(
		@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		// TODO Auto-generated method stub
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<TitleType> titleTypePage = service.findAll(pageable);
	    
	    List<TitleTypeDTO> titleTypeDTOs = new ArrayList<TitleTypeDTO>();
	    
	    Page<TitleTypeDTO> resultPage = new PageImpl<TitleTypeDTO>(titleTypeDTOs, pageable, titleTypePage.getTotalElements());
	    
		return new ResponseEntity<Page<TitleTypeDTO>>(resultPage, HttpStatus.OK);
	}	
	
	@GetMapping("/active")
	@Secured({"ADMIN","TEACHER","STUDENT","STAFF","USER"})
	public ResponseEntity<Iterable<TitleTypeDTO>> findAllActive() {
		// TODO Auto-generated method stub
	ArrayList<TitleTypeDTO> titleTypes = new ArrayList<TitleTypeDTO>();
	for(TitleType tt : service.findAllActive()) {
			titleTypes.add(new TitleTypeDTO(tt.getId(), tt.getName(), tt.getActive()));
		}
		return new ResponseEntity<Iterable<TitleTypeDTO>>(titleTypes, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN","TEACHER","STUDENT","STAFF","USER"})
	public ResponseEntity<TitleTypeDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		TitleType tt = service.findById(id).orElse(null);
		
		if(tt == null) {
			return new ResponseEntity<TitleTypeDTO>(HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<TitleTypeDTO>(new TitleTypeDTO(tt.getId(), tt.getName(), tt.getActive()), HttpStatus.OK);

}

	@Override
	@PostMapping
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<TitleTypeDTO> create(@RequestBody TitleTypeDTO t) {
		// TODO Auto-generated method stub
		TitleType tt = service.create(new TitleType(null, t.getName(), true));
		
		if(tt == null) {
			return new ResponseEntity<TitleTypeDTO>(HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<TitleTypeDTO>(new TitleTypeDTO(tt.getId(), tt.getName(), tt.getActive()), HttpStatus.OK);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<TitleTypeDTO> update(@RequestBody TitleTypeDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub

		TitleType tt = service.findById(id).orElse(null);
		
		if(tt == null) {
			return new ResponseEntity<TitleTypeDTO>(HttpStatus.NOT_FOUND);
		}
	
		tt.setName(t.getName());
		tt.setActive(t.getActive());
	
		tt = service.update(tt);	
	return new ResponseEntity<TitleTypeDTO>(new TitleTypeDTO(tt.getId(), tt.getName(), tt.getActive()), HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<TitleTypeDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PatchMapping("/{id}")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<TitleTypeDTO> softDelete(@PathVariable("id") Long id) {
	// TODO Auto-generated method stub
		TitleType tt = service.findById(id).orElse(null);		

		if(tt == null) {

			return new ResponseEntity<TitleTypeDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.softDelete(id);

		return new ResponseEntity<TitleTypeDTO>(new TitleTypeDTO(tt.getId(), tt.getName(), tt.getActive()), HttpStatus.OK);
	}
}
