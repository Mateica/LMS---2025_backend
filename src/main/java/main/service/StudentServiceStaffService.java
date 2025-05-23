package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.StudentServiceStaff;
import main.repository.StudentServiceStaffRepository;

@Service
public class StudentServiceStaffService implements ServiceInterface<StudentServiceStaff> {
	@Autowired
	private StudentServiceStaffRepository repo;

	@Override
	public Iterable<StudentServiceStaff> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<StudentServiceStaff> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public List<StudentServiceStaff> findAllActive(){
		return this.repo.findByActiveIsTrue();
	}

	@Override
	public Optional<StudentServiceStaff> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public StudentServiceStaff create(StudentServiceStaff t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public StudentServiceStaff update(StudentServiceStaff t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No staff member with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		StudentServiceStaff staff = findById(id).orElse(null);
		
		if(staff != null) {
			staff.setActive(false);
			repo.save(staff);
		}
		
		throw new RuntimeException("No staff member with such ID!");
	}

}
