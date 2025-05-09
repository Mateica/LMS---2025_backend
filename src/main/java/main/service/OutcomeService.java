package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.EvaluationType;
import main.model.Outcome;
import main.repository.OutcomeRepository;

@Service
public class OutcomeService implements ServiceInterface<Outcome> {
	@Autowired
	private OutcomeRepository repo;

	@Override
	public Iterable<Outcome> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<Outcome> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<Outcome> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public Outcome create(Outcome t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public Outcome update(Outcome t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No syllabus with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		Outcome o = findById(id).orElse(null);
		
		if(o != null) {
			o.setActive(false);
			repo.save(o);
		}
		
		throw new RuntimeException("No syllabus with such ID!");
	}
}
