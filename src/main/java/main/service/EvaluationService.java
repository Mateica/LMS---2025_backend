package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.Evaluation;
import main.repository.EvaluationRepository;

@Service
public class EvaluationService implements ServiceInterface<Evaluation> {
	@Autowired
	private EvaluationRepository repo;

	@Override
	public Iterable<Evaluation> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<Evaluation> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public List<Evaluation> findAllActive(){
		return this.repo.findByActiveIsTrue();
	}

	@Override
	public Optional<Evaluation> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Evaluation create(Evaluation t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Evaluation update(Evaluation t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		
	}
	
}
