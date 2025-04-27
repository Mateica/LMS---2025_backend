package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.Role;
import main.model.StudyProgramme;
import main.repository.StudyProgrammeRepository;

@Service
public class StudyProgrammeService implements ServiceInterface<StudyProgramme> {
	@Autowired
	private StudyProgrammeRepository repo;

	@Override
	public Iterable<StudyProgramme> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}
	
	@Override
	public Page<StudyProgramme> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<StudyProgramme> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public StudyProgramme create(StudyProgramme t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public StudyProgramme update(StudyProgramme t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No study programme with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		StudyProgramme sp = repo.findById(id).orElse(null);
		
		if(sp == null) {
			throw new RuntimeException("No study programme with such ID!");
		}
		
		repo.deleteById(id);
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		StudyProgramme sp = repo.findById(id).orElse(null);
		
		if(sp == null) {
			throw new RuntimeException("No study programme with such ID!");
		}
		
		sp.setActive(false);
		repo.save(sp);
	}
}
