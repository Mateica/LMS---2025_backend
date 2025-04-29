package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.RegisteredUser;
import main.repository.RegisteredUserRepository;

@Service
public class RegisteredUserService implements ServiceInterface<RegisteredUser> {
	@Autowired
	private RegisteredUserRepository repo;

	@Override
	public Iterable<RegisteredUser> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}
	
	@Override
	public Page<RegisteredUser> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<RegisteredUser> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}
	
	public RegisteredUser findByUsername(String username) {
		return this.repo.findByUsername(username);
	}
	
	public RegisteredUser findByUsernameAndPassword(String username, String password) {
		return this.repo.findByUsernameAndPassword(username, password);
	}

	@Override
	public RegisteredUser create(RegisteredUser t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public RegisteredUser update(RegisteredUser t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No user with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.repo.deleteById(id);
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		RegisteredUser user = findById(id).orElse(null);
		
		if(user != null) {
			user.setActive(false);
			repo.save(user);
		}
		
		throw new RuntimeException("No user with such ID!");
		
	}
	
}
