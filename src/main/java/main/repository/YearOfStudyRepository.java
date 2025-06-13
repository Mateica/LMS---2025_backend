package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.YearOfStudy;

@Repository
public interface YearOfStudyRepository extends CrudRepository<YearOfStudy, Long>, PagingAndSortingRepository<YearOfStudy, Long> {
	@Query("UPDATE YearOfStudy t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	
	public List<YearOfStudy> findByActiveIsTrue();
}
