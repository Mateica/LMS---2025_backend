package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.Subject;
import main.model.SubjectRealization;
import main.repository.SubjectRealizationRepository;
import main.repository.SubjectRepository;

@Service
public class SubjectRealizationService implements ServiceInterface<SubjectRealization> {
	@Autowired
	private SubjectRealizationRepository repo;

	@Override
	public Iterable<SubjectRealization> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<SubjectRealization> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<SubjectRealization> findById(Long id) {
		// TODO Auto-generated method stub
		return this.findById(id);
	}

	@Override
	public SubjectRealization create(SubjectRealization t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public SubjectRealization update(SubjectRealization t) {
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
		SubjectRealization s = repo.findById(id).orElse(null);
		
		if(s == null) {
			throw new RuntimeException("No subject realization with such ID!");
		}
		
		s.setActive(false);
		repo.save(s);
	}
}
