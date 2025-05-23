package main.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import main.model.Role;
import main.model.Teacher;

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

import main.dto.AccountDTO;
import main.dto.ProfileDTO;
import main.dto.RegisteredUserDTO;
import main.model.Account;
import main.model.ForumUser;
import main.model.RegisteredUser;
import main.service.RegisteredUserService;
import main.service.TeacherService;

@RestController
@RequestMapping(path = "/api/registeredUsers")
public class RegisteredUserController implements ControllerInterface<RegisteredUserDTO> {
	@Autowired
	private RegisteredUserService service;
	
	@Autowired
	private TeacherService teacherService;
	
	@Override
	@GetMapping("")
	@Secured({"ADMIN"})
	public ResponseEntity<Iterable<RegisteredUserDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<RegisteredUserDTO> users = new ArrayList<RegisteredUserDTO>();
		
		for(RegisteredUser u : service.findAll()) {
			users.add(new RegisteredUserDTO(u.getUsername(), u.getPassword(), u.getEmail()));
		}
		
		return new ResponseEntity<Iterable<RegisteredUserDTO>>(users,HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/params")
	@Secured({"ADMIN"})
	public ResponseEntity<Page<RegisteredUserDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<RegisteredUser> userPage = service.findAll(pageable);

	    List<RegisteredUserDTO> userDTOs = userPage.stream().map(u ->
	        new RegisteredUserDTO(
	        		u.getUsername(), 
	        		u.getPassword(), 
	        		u.getEmail()
	        )
	    ).collect(Collectors.toList());

	    Page<RegisteredUserDTO> resultPage = new PageImpl<RegisteredUserDTO>(userDTOs, pageable, userPage.getTotalElements());

	    return new ResponseEntity<Page<RegisteredUserDTO>>(resultPage, HttpStatus.OK);
	}


	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN"})
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
	@Secured({"ADMIN"})
	public ResponseEntity<RegisteredUserDTO> create(@RequestBody RegisteredUserDTO t) {
		// TODO Auto-generated method stub
		RegisteredUser user = service.create(new RegisteredUser(null, t.getUsername(), t.getPassword(), t.getEmail(),
				new ArrayList<ForumUser>(), new ArrayList<Account>(), new HashSet<Role>(), true));
		
		if(user == null) {
			return new ResponseEntity<RegisteredUserDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RegisteredUserDTO>(new RegisteredUserDTO(user.getUsername(), user.getPassword(), user.getEmail()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN"})
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
	@Secured({"ADMIN"})
	public ResponseEntity<RegisteredUserDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<RegisteredUserDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		RegisteredUser user = service.findById(id).orElse(null);
		
		if(user == null) {
			return new ResponseEntity<RegisteredUserDTO>(HttpStatus.BAD_REQUEST);
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<RegisteredUserDTO>(new RegisteredUserDTO(user.getUsername(), user.getPassword(), user.getEmail()), HttpStatus.OK);
	}
	
	@PutMapping("/updateProfile")
	@Secured({"USER"})
	public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profile){
		RegisteredUser user = service.findByUsername(profile.getUsername());
		
		if(user == null) {
			return new ResponseEntity<ProfileDTO>(HttpStatus.BAD_REQUEST);
		}
		
		if(user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("TEACHER"))) {
			Teacher teacher = teacherService.updateProfile(user, profile);
		}
		
		return new ResponseEntity<ProfileDTO>(profile, HttpStatus.OK);
	}
}
