package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.EvaluationGrade;
import main.model.Faculty;
import main.model.StudentAffairsOffice;
import main.model.University;
import main.repository.EvaluationGradeRepository;
import main.repository.FacultyRepository;

@Service
public class FacultyService implements ServiceInterface<Faculty> {
	@Autowired
	private FacultyRepository repo;

	@Override
	public Iterable<Faculty> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<Faculty> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public List<Faculty> findAllActive(){
		return this.repo.findByActiveIsTrue();
	}
	
	public List<Faculty> findByUniversity(University university) {
		return this.repo.findByUniversity(university);
	}
	
	public Faculty findByStudentAffairsOffice(StudentAffairsOffice office) {
		return this.repo.findByStudentAffairsOffice(office);
	}

	@Override
	public Optional<Faculty> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}
	

	@Override
	public Faculty create(Faculty t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public Faculty update(Faculty t) {
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
		Faculty et = findById(id).orElse(null);
		
		if(et != null) {
			et.setActive(false);
			repo.save(et);
		}
	}
}
