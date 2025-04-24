package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.dto.RegisteredUserDTO;
import main.model.RegisteredUser;
import main.service.AuthService;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
	@Autowired
	private AuthService service;
	
	@PostMapping("/register")
	public ResponseEntity<RegisteredUser> register(@RequestBody RegisteredUserDTO userDTO){
		RegisteredUser user = service.register(userDTO);
		
		if(user == null) {
			return new ResponseEntity<RegisteredUser>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<RegisteredUser>(user, HttpStatus.CREATED);
	}
	
	
}
