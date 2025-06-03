package main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.dto.TitleTypeDTO;
import main.model.Outcome;
import main.model.TitleType;

@Repository
public interface TitleTypeRepository {
	public Iterable<TitleType> findAll();
	public Page<TitleType> findAll(Pageable pageable);
	public List<TitleType> findAllActive();
	public Optional<TitleType> findById(Long id);
	public TitleType save(TitleType tt);
	public TitleType update(TitleType tt);
	
	@Query("UPDATE TitleType t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	public List<TitleType> findByActiveIsTrue();
}
