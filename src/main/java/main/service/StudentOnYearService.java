package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.StudentOnYear;
import main.repository.StudentOnYearRepository;

@Service
public class StudentOnYearService implements ServiceInterface<StudentOnYear> {
	@Autowired
	private StudentOnYearRepository repo;

	@Override
	public Iterable<StudentOnYear> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<StudentOnYear> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<StudentOnYear> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public StudentOnYear create(StudentOnYear t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public StudentOnYear update(StudentOnYear t) {
		// TODO Auto-generated method stub
		if(repo.findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No student on year with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		StudentOnYear s = findById(id).orElse(null);
		
		if(s != null) {
			s.setActive(false);
			this.repo.save(s);
		}
		
		throw new RuntimeException("No student on year with such ID!");
	}
}
