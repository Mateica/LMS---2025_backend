package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.Subject;
import main.repository.SubjectRepository;

@Service
public class SubjectService implements ServiceInterface<Subject> {
	@Autowired
	private SubjectRepository repo;

	@Override
	public Iterable<Subject> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<Subject> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<Subject> findById(Long id) {
		// TODO Auto-generated method stub
		return this.findById(id);
	}

	@Override
	public Subject create(Subject t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public Subject update(Subject t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No subject with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		Subject s = repo.findById(id).orElse(null);
		
		if(s == null) {
			throw new RuntimeException("No subject with such ID!");
		}
		
		s.setActive(false);
		repo.save(s);
	}
}
