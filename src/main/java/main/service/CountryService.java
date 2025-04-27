package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.Country;
import main.repository.CountryRepository;

@Service
public class CountryService implements ServiceInterface<Country> {
	@Autowired
	private CountryRepository repo;

	@Override
	public Iterable<Country> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}
	
	@Override
	public Page<Country> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<Country> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public Country create(Country t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public Country update(Country t) {
		// TODO Auto-generated method stub
		if(findById(t.getId()).isPresent()) {
			return this.repo.save(t);
		}
		
		throw new RuntimeException("No country with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		Country c = findById(id).orElse(null);
		
		if(c != null) {
			c.setActive(false);
			repo.save(c);
		}
		
		throw new RuntimeException("No country with such ID!");
		
	}
	
	
}
