package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Outcome;

@Repository
public interface OutcomeRepository extends CrudRepository<Outcome, Long>, PagingAndSortingRepository<Outcome, Long> {
	@Query("UPDATE Outcome t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	public List<Outcome> findByActiveIsTrue();
}
