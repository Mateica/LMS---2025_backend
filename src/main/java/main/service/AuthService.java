package main.service;

import main.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import main.dto.RegisteredUserDTO;
import main.model.RegisteredUser;
import main.repository.RegisteredUserRepository;
import main.repository.RoleRepository;

import java.util.List;

@Service
public class AuthService {
	@Autowired
	private RegisteredUserRepository repo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public RegisteredUser register(RegisteredUserDTO userDTO) {
		if (repo.findByUsername(userDTO.getUsername()) != null) {
			throw new IllegalArgumentException("Username " + userDTO.getUsername() + " is already in use");
		}

		if (repo.findByEmail(userDTO.getEmail()) != null) {
			throw new IllegalArgumentException("Email " + userDTO.getEmail() + " is already in use");
		}

		try {
			RegisteredUser user = new RegisteredUser();
			user.setUsername(userDTO.getUsername());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setEmail(userDTO.getEmail());

			userDTO.getRoleNames().add("USER");
			userDTO.getRoleNames().forEach(roleName -> {
				Role role = roleRepo.findByName(roleName);

				if (role == null) {
// Save role if it does not exist
					role = roleRepo.save(new Role(null, roleName, true));
				} else {
// Add user to role if it exists
					role.addRegisteredUser(user);
				}

// Give user role
				user.getRoles().add(role);
			});
			user.setActive(true);

			return this.repo.save(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public RegisteredUser findByUsernameAndPassword(String email, String password) {
		RegisteredUser user = this.repo.findByEmail(email);
		if (user == null) {
			throw new BadCredentialsException("Invalid username or password");
		}

		System.out.println(user.getPassword());
		System.out.println(passwordEncoder.matches(password, user.getPassword()));
		if (passwordEncoder.matches(password, user.getPassword())) {
			return user;
		}
		return null;
	}

}