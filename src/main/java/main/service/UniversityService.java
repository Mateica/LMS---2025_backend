package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.Faculty;
import main.model.University;
import main.repository.UniversityRepository;

@Service
public class UniversityService implements ServiceInterface<University> {
	@Autowired
	private UniversityRepository repo;
	
	@Override
	public Iterable<University> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<University> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public List<University> findAllActive(){
		return this.repo.findByActiveIsTrue();
	}
	
	public University findByFaculty(Faculty faculty) {
		return this.repo.findByFaculty(faculty);
	}
	
	public University findByFacultyId(Long id) {
		return this.repo.findByFacultyId(id);
	}

	@Override
	public Optional<University> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public University create(University t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public University update(University t) {
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
		University et = findById(id).orElse(null);
		
		if(et != null) {
			et.setActive(false);
			repo.save(et);
		}
	}
}
