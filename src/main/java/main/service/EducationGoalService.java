package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.EducationGoal;
import main.repository.EducationGoalRepository;

@Service
public class EducationGoalService implements ServiceInterface<EducationGoal> {
	@Autowired
	private EducationGoalRepository repo;

	@Override
	public Iterable<EducationGoal> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<EducationGoal> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<EducationGoal> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public EducationGoal create(EducationGoal t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public EducationGoal update(EducationGoal t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No education goal with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		EducationGoal eg = findById(id).orElse(null);
		
		if(eg != null) {
			eg.setActive(false);
			repo.save(eg);
		}
		
		throw new RuntimeException("No education goal with such ID!");
		
	}
}
