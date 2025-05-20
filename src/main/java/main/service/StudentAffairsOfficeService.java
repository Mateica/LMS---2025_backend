package main.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.StudentAffairsOffice;
import main.repository.StudentAffairsOfficeRepository;

@Service
public class StudentAffairsOfficeService implements ServiceInterface<StudentAffairsOffice> {
	@Autowired
	private StudentAffairsOfficeRepository repo;

	@Override
	public Iterable<StudentAffairsOffice> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<StudentAffairsOffice> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<StudentAffairsOffice> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public StudentAffairsOffice create(StudentAffairsOffice t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public StudentAffairsOffice update(StudentAffairsOffice t) {
		// TODO Auto-generated method stub
		if(repo.findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No office with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		StudentAffairsOffice office = findById(id).orElse(null);
		
		if(office != null) {
			office.setActive(false);
			this.repo.save(office);
		}
		
		throw new RuntimeException("No office with such ID!");
	}
}
