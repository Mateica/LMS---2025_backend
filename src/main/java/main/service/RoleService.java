package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.model.Role;
import main.repository.RoleRepository;

@Service
public class RoleService implements ServiceInterface<Role> {
	@Autowired
	private RoleRepository repo;

	public RoleRepository getRepo() {
		return repo;
	}

	public void setRepo(RoleRepository repo) {
		this.repo = repo;
	}

	@Override
	public Iterable<Role> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Optional<Role> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public Role create(Role t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public Role update(Role t) {
		// TODO Auto-generated method stub
		Role r = repo.findById(t.getId()).orElse(null);
		
		if(r != null) {
			this.repo.save(r);
		}
		
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Role r = repo.findById(id).orElse(null);
		
		if(r == null) {
			throw new RuntimeException("Uloga ne postoji!");
		}
		
		repo.deleteById(id);
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		Role r = repo.findById(id).orElse(null);
		
		if(r == null) {
			throw new RuntimeException("Uloga ne postoji!");
		}
		
		r.setActive(false);
		repo.save(r);
		
	}
	
}
