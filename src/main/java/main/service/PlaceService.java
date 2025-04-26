package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.model.Place;
import main.repository.PlaceRepository;

@Service
public class PlaceService implements ServiceInterface<Place> {
	@Autowired
	private PlaceRepository repo;

	@Override
	public Iterable<Place> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Optional<Place> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public Place create(Place t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public Place update(Place t) {
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
		Place p = findById(id).orElse(null);
		
		if(p != null) {
			p.setActive(false);
			repo.save(p);
		}
		
		throw new RuntimeException("No place with such ID!");
		
	}
}
