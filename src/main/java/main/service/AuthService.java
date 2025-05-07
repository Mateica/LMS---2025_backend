package main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import main.dto.RegisteredUserDTO;
import main.model.RegisteredUser;
import main.repository.RegisteredUserRepository;
import main.repository.RoleRepository;

@Service
public class AuthService {
	@Autowired
	private RegisteredUserRepository repo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public RegisteredUser register(RegisteredUserDTO userDTO) {
		try {
			RegisteredUser user = new RegisteredUser();
			user.setUsername(userDTO.getUsername());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setEmail(userDTO.getEmail());
			user.getRoles().add(roleRepo.findByName("USER"));
			user.setActive(true);
			
			return this.repo.save(user);
		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("TEST");
			return null;
		}	
	}

	public RegisteredUser findByUsernameAndPassword(String email, String password) {
		RegisteredUser user =  this.repo.findByEmail(email);
		System.out.println(user.getPassword());
		System.out.println(passwordEncoder.matches(password, user.getPassword()));
		if(passwordEncoder.matches(password, user.getPassword())) {
			return user;
		}
		return null;
	}
	
}
