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

import main.dto.RoleDTO;
import main.model.Role;
import main.service.RoleService;

@RestController
@RequestMapping(path = "/api/roles")
public class RoleController implements ControllerInterface<RoleDTO> {
	@Autowired
	private RoleService service;

	public RoleService getService() {
		return service;
	}

	public void setService(RoleService service) {
		this.service = service;
	}

	@Override
	@GetMapping("")
	@Secured({"ADMIN"})
	public ResponseEntity<Iterable<RoleDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<RoleDTO> roles = new ArrayList<RoleDTO>();
		
		for(Role r : service.findAll()) {
			roles.add(new RoleDTO(r.getId(), r.getName(), r.getActive()));
		}
		
		return new ResponseEntity<Iterable<RoleDTO>>(roles, HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/params")
	@Secured({"ADMIN"})
	public ResponseEntity<Page<RoleDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Role> rolePage = service.findAll(pageable);

	    List<RoleDTO> roleDTOs = rolePage.stream().map(r ->
	    new RoleDTO(r.getId(), r.getName(), r.getActive())).collect(Collectors.toList());

	    Page<RoleDTO> resultPage = new PageImpl<RoleDTO>(roleDTOs, pageable, rolePage.getTotalElements());

	    return new ResponseEntity<Page<RoleDTO>>(resultPage, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<RoleDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Role r = service.findById(id).orElse(null);
		
		if(r == null) {
			return new ResponseEntity<RoleDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RoleDTO>(new RoleDTO(r.getId(), r.getName(), r.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping("")
	@Secured({"ADMIN"})
	public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO t) {
		// TODO Auto-generated method stub
		Role r = service.create(new Role(t.getId(), t.getName(), t.getActive()));
		
		if(r == null) {
			return new ResponseEntity<RoleDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RoleDTO>(new RoleDTO(r.getId(), r.getName(), r.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<RoleDTO> update(@RequestBody RoleDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Role r = service.findById(id).orElse(null);
		
		if(r == null) {
			return new ResponseEntity<RoleDTO>(HttpStatus.NOT_FOUND);
		}
		
		r.setName(t.getName());
		r.setActive(t.getActive());
		
		r = service.update(r);
		
		return new ResponseEntity<RoleDTO>(new RoleDTO(r.getId(), r.getName(), r.getActive()), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<RoleDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Role r = service.findById(id).orElse(null);
		
		if(r == null) {
			return new ResponseEntity<RoleDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.delete(id);
		return new ResponseEntity<RoleDTO>(new RoleDTO(r.getId(), r.getName(), r.getActive()), HttpStatus.OK);
	}

	@Override
	@PutMapping("/softDelete/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<RoleDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Role r = service.findById(id).orElse(null);
		
		if(r == null) {
			return new ResponseEntity<RoleDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.softDelete(id);
		return new ResponseEntity<RoleDTO>(new RoleDTO(r.getId(), r.getName(), r.getActive()), HttpStatus.OK);
	}
}
