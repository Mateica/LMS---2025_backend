package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.EvaluationGrade;
import main.model.EvaluationType;
import main.repository.EvaluationGradeRepository;

@Service
public class EvaluationGradeService implements ServiceInterface<EvaluationGrade> {
	@Autowired
	private EvaluationGradeRepository repo;

	@Override
	public Iterable<EvaluationGrade> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<EvaluationGrade> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public List<EvaluationGrade> findAllActive(){
		return this.repo.findByActiveIsTrue();
	}

	@Override
	public Optional<EvaluationGrade> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public EvaluationGrade create(EvaluationGrade t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public EvaluationGrade update(EvaluationGrade t) {
		// TODO Auto-generated method stub
		if(repo.findById(t.getId()).isPresent()) {
			this.repo.save(t);
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		EvaluationGrade et = findById(id).orElse(null);
		
		if(et != null) {
			et.setActive(false);
			repo.save(et);
		}
	}
}
