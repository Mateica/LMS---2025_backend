package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.StudyProgramme;
import main.model.TeacherOnRealization;
import main.repository.TeacherOnRealizationRepository;

@Service
public class TeacherOnRealizationService implements ServiceInterface<TeacherOnRealization> {
	@Autowired
	private TeacherOnRealizationRepository repo;

	@Override
	public Iterable<TeacherOnRealization> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<TeacherOnRealization> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public List<TeacherOnRealization> findByTeacherId(Long id) {
		return this.repo.findByTeacherId(id);
	}

	@Override
	public Optional<TeacherOnRealization> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public TeacherOnRealization create(TeacherOnRealization t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public TeacherOnRealization update(TeacherOnRealization t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No teacher with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		TeacherOnRealization t = repo.findById(id).orElse(null);
		
		if(t == null) {
			throw new RuntimeException("No study programme with such ID!");
		}
		
		t.setActive(false);
		repo.save(t);
	}
	
}
