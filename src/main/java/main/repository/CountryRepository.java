package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Country;
import main.model.EducationGoal;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long>, PagingAndSortingRepository<Country, Long> {
	@Query("UPDATE Country t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	public List<Country> findByActiveIsTrue();
}
