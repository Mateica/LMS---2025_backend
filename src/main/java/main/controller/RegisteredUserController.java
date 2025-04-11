package main.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.dto.RegisteredUserDTO;
import main.model.ForumUser;
import main.model.RegisteredUser;
import main.service.RegisteredUserService;

@RestController
@RequestMapping(path = "/api/registeredUsers")
public class RegisteredUserController implements ControllerInterface<RegisteredUserDTO> {
	@Autowired
	private RegisteredUserService service;
	
	@Override
	@GetMapping("")
	public ResponseEntity<Iterable<RegisteredUserDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<RegisteredUserDTO> users = new ArrayList<RegisteredUserDTO>();
		
		for(RegisteredUser u : service.findAll()) {
			users.add(new RegisteredUserDTO(u.getUsername(), u.getPassword(), u.getEmail()));
		}
		
		return new ResponseEntity<Iterable<RegisteredUserDTO>>(users,HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<RegisteredUserDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		RegisteredUser user = service.findById(id).orElse(null);
		
		if(user == null) {
			return new ResponseEntity<RegisteredUserDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RegisteredUserDTO>(new RegisteredUserDTO(user.getUsername(), user.getPassword(), user.getEmail()), HttpStatus.OK);
	}

	@Override
	@PostMapping("")
	public ResponseEntity<RegisteredUserDTO> create(@RequestBody RegisteredUserDTO t) {
		// TODO Auto-generated method stub
		RegisteredUser user = service.create(new RegisteredUser(null, t.getUsername(), t.getPassword(), t.getEmail(), new ArrayList<ForumUser>(), true));
		
		if(user == null) {
			return new ResponseEntity<RegisteredUserDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RegisteredUserDTO>(new RegisteredUserDTO(user.getUsername(), user.getPassword(), user.getEmail()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<RegisteredUserDTO> update(@RequestBody RegisteredUserDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		RegisteredUser user = service.findById(id).orElse(null);
		
		if(user == null) {
			return new ResponseEntity<RegisteredUserDTO>(HttpStatus.BAD_REQUEST);
		}
		
		user.setUsername(t.getUsername());
		user.setPassword(t.getPassword());
		user.setEmail(t.getEmail());
		
		user = service.update(user);
		
		return new ResponseEntity<RegisteredUserDTO>(new RegisteredUserDTO(user.getUsername(), user.getPassword(), user.getEmail()), HttpStatus.CREATED);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<RegisteredUserDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
	public ResponseEntity<RegisteredUserDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		RegisteredUser user = service.findById(id).orElse(null);
		
		if(user == null) {
			return new ResponseEntity<RegisteredUserDTO>(HttpStatus.BAD_REQUEST);
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<RegisteredUserDTO>(new RegisteredUserDTO(user.getUsername(), user.getPassword(), user.getEmail()), HttpStatus.OK);
	}

}
