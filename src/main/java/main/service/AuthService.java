package main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import main.dto.RegisteredUserDTO;
import main.model.RegisteredUser;
import main.repository.RegisteredUserRepository;

@Service
public class AuthService {
	@Autowired
	private RegisteredUserRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public RegisteredUser register(RegisteredUserDTO userDTO) {
		try {
			RegisteredUser user = new RegisteredUser();
			user.setUsername(userDTO.getUsername());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setEmail(userDTO.getEmail());
			
			return this.repo.save(user);
		
		}
		catch(Exception e) {
			return null;
		}	
	}
	
	
}
