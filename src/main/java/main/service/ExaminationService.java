package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import main.model.Examination;
import main.repository.ExaminationRepository;

@Service
public class ExaminationService implements ServiceInterface<Examination> {
	@Autowired
	private ExaminationRepository repo;

	@Override
	public Iterable<Examination> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<Examination> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public List<Examination> findAllActive(){
		return this.repo.findByActiveIsTrue();
	}

	@Override
	public Optional<Examination> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	@Transactional
	public Examination create(Examination t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	@Transactional
	public Examination update(Examination t) {
		// TODO Auto-generated method stub
		if(repo.findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		Examination e = repo.findById(id).orElse(null);
		
		if(e != null) {
			e.setActive(false);
			this.repo.save(e);
		}
	}
	
}
