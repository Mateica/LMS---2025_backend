package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.Address;
import main.repository.AddressRepository;

@Service
public class AddressService implements ServiceInterface<Address> {
	@Autowired
	private AddressRepository repo;

	@Override
	public Iterable<Address> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<Address> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public List<Address> findAllActive(){
		return this.repo.findByActiveIsTrue();
	}

	@Override
	public Optional<Address> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public Address create(Address t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public Address update(Address t) {
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
		Address a = repo.findById(id).orElse(null);
		
		if(a != null) {
			a.setActive(false);
			this.repo.save(a);
		}
	}
	
	
}
