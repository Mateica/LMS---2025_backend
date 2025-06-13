package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.YearOfStudy;
import main.repository.YearOfStudyRepository;

@Service
public class YearOfStudyService implements ServiceInterface<YearOfStudy> {
	@Autowired
	private YearOfStudyRepository repo;
	
	@Override
	public Iterable<YearOfStudy> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<YearOfStudy> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}
	
	public List<YearOfStudy> findAllActive(){
		return this.repo.findByActiveIsTrue();
	}

	@Override
	public Optional<YearOfStudy> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public YearOfStudy create(YearOfStudy t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public YearOfStudy update(YearOfStudy t) {
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
		YearOfStudy year = repo.findById(id).orElse(null);
		
		if(year != null) {
			year.setActive(false);
			this.repo.save(year);
		}
	}
}
