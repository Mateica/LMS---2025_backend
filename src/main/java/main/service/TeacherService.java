package main.service;

import java.awt.color.ProfileDataException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.dto.ProfileDTO;
import main.model.RegisteredUser;
import main.model.Teacher;
import main.repository.TeacherRepository;

@Service
public class TeacherService implements ServiceInterface<Teacher> {
	@Autowired
	private TeacherRepository repo;

	@Override
	public Iterable<Teacher> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}
	
	public Page<Teacher> findAll(Pageable pageable){
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<Teacher> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public Teacher create(Teacher t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public Teacher update(Teacher t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No teacher with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Teacher t = repo.findById(id).orElse(null);
		
		if(t == null) {
			throw new RuntimeException("No teacher with such ID!");
		}
		
		repo.deleteById(id);
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		Teacher t = repo.findById(id).orElse(null);
		
		if(t == null) {
			throw new RuntimeException("No teacher with such ID!");
		}
		
		t.setActive(false);
		repo.save(t);
	}
	
	public Teacher updateProfile(RegisteredUser user, ProfileDTO profile) {
		Teacher teacher = repo.findByUser(user);
		
		if(teacher == null) {
			throw new RuntimeException("No such user!");
		}
		
		teacher.setFirstName(profile.getFirstName());
		teacher.setLastName(profile.getLastName());
		teacher.setUmcn(profile.getUmcn());
		teacher.setBiography(profile.getBiography());
		
		return this.repo.save(teacher);
	}
	
	
}
