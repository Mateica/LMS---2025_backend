package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.TeachingMaterial;
import main.repository.TeachingMaterialRepository;

@Service
public class TeachingMaterialService implements ServiceInterface<TeachingMaterial> {
	@Autowired
	private TeachingMaterialRepository repo;

	@Override
	public Iterable<TeachingMaterial> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<TeachingMaterial> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public Iterable<TeachingMaterial> findAllActive(){
		return this.repo.findByActiveIsTrue();
	}

	@Override
	public Optional<TeachingMaterial> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public TeachingMaterial create(TeachingMaterial t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public TeachingMaterial update(TeachingMaterial t) {
		// TODO Auto-generated method stub
		if(repo.findById(t.getId()).isPresent()) {
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
		TeachingMaterial t = repo.findById(id).orElse(null);
		
		if(t != null) {
			t.setActive(false);
			this.repo.save(t);
		}
	}
}
