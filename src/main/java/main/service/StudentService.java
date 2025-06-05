package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.Student;
import main.repository.StudentRepository;

@Service
public class StudentService implements ServiceInterface<Student>{
	@Autowired
	private StudentRepository repo;

	@Override
	public Iterable<Student> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<Student> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public List<Student> findAllActive(){
		return this.repo.findByActiveIsTrue();
	}

	@Override
	public Optional<Student> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public Student create(Student t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public Student update(Student t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
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
		Student s = findById(id).orElse(null);
		
		if(s != null) {
			s.setActive(false);
			repo.save(s);
		}
	}
	
	
}
