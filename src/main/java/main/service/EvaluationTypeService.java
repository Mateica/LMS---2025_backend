package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.model.EvaluationType;
import main.repository.EvaluationTypeRepository;

@Service
public class EvaluationTypeService implements ServiceInterface<EvaluationType> {
	@Autowired
	private EvaluationTypeRepository repo;

	@Override
	public Iterable<EvaluationType> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Optional<EvaluationType> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public EvaluationType create(EvaluationType t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public EvaluationType update(EvaluationType t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No evaluation type with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.repo.deleteById(id);
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		EvaluationType et = findById(id).orElse(null);
		
		if(et != null) {
			et.setActive(false);
			repo.save(et);
		}
		
		throw new RuntimeException("No evaluation type with such ID!");
		
	}
}
