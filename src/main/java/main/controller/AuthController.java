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
import main.dto.LoginDTO;
import main.model.RegisteredUser;
import main.service.AuthService;
import main.util.TokenUtils;
import main.config.SecurityConfig;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
	@Autowired
	private AuthService service;
	
	@Autowired
	private SecurityConfig sc;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	
	@PostMapping("/register")
	public ResponseEntity<RegisteredUser> register(@RequestBody RegisteredUserDTO userDTO){
		RegisteredUser user = service.register(userDTO);
		
		if(user == null) {
			return new ResponseEntity<RegisteredUser>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<RegisteredUser>(user, HttpStatus.CREATED);
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<String> login(@RequestBody LoginDTO korisnikDTO) throws Exception {
		RegisteredUser user = korisnikService.findByUsernameAndPassword(korisnikDTO.getUsername(), sc.passwordEncoder().encode(korisnikDTO.getPassword()));
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(korisnik.getKorisnickoIme(),
				korisnik.getLozinka());
		Authentication auth = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwt = tokenUtils.generateToken(userDetailsService.loadUserByUsername(korisnik.getKorisnickoIme()));
		System.out.println(jwt);
		return new ResponseEntity<String>(jwt, HttpStatus.OK);
	}
	
	
	
	
}
