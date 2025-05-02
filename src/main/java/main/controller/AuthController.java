package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.dto.RegisteredUserDTO;
import main.dto.TokenDTO;
import main.dto.LoginDTO;
import main.model.RegisteredUser;
import main.service.AuthService;
import main.service.RegisteredUserService;
import main.util.TokenUtils;
import main.config.SecurityConfig;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
	@Autowired
	private AuthService service;
	
	@Autowired
	private RegisteredUserService userService;
	
	@Autowired
	private SecurityConfig sc;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	
	@PostMapping("/register")
	public ResponseEntity<RegisteredUserDTO> register(@RequestBody RegisteredUserDTO userDTO){
		RegisteredUser user = service.register(userDTO);
		
		if(user == null) {
			return new ResponseEntity<RegisteredUserDTO>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<RegisteredUserDTO>(new RegisteredUserDTO(user.getUsername(), null, user.getEmail()), HttpStatus.CREATED);
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO korisnikDTO) throws Exception {
		RegisteredUser user = service.findByUsernameAndPassword(korisnikDTO.getUsername(), 
				korisnikDTO.getPassword());
		if(user == null) {
			return ResponseEntity.badRequest().build();
		}
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),
//				user.getPassword());
//		Authentication auth = authenticationManager.authenticate(token);
//		SecurityContextHolder.getContext().setAuthentication(auth);

		
		String jwt = tokenUtils.generateToken(userDetailsService.loadUserByUsername(user.getUsername()));
		TokenDTO token = new TokenDTO(jwt);
		System.out.println(jwt);
		return new ResponseEntity<TokenDTO>(token, HttpStatus.OK);
	}
	
	
	
	
}
