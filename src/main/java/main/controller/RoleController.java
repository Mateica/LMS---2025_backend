package main.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import main.dto.RoleDTO;
import main.model.Role;
import main.service.RoleService;

@Controller
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
	public ResponseEntity<Iterable<RoleDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<RoleDTO> roles = new ArrayList<RoleDTO>();
		
		for(Role r : service.findAll()) {
			roles.add(new RoleDTO(r.getId(), r.getName(), r.getActive()));
		}
		
		return new ResponseEntity<Iterable<RoleDTO>>(roles, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
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
	@PutMapping("/deleted/{id}")
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
