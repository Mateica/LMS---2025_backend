package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.EvaluationInstrument;
import main.model.EvaluationType;
import main.repository.EvaluationInstrumentRepository;

@Service
public class EvaluationInstrumentService implements ServiceInterface<EvaluationInstrument> {
	@Autowired
	private EvaluationInstrumentRepository repo;

	@Override
	public Iterable<EvaluationInstrument> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<EvaluationInstrument> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<EvaluationInstrument> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public EvaluationInstrument create(EvaluationInstrument t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public EvaluationInstrument update(EvaluationInstrument t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No evaluation type with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		EvaluationInstrument ei = findById(id).orElse(null);
		
		if(ei != null) {
			ei.setActive(false);
			repo.save(ei);
		}
		
		throw new RuntimeException("No evaluation instrument with such ID!");
		
	}
}
