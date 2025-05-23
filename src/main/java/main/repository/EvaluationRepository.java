package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Evaluation;
import main.model.File;

@Repository
public interface EvaluationRepository extends CrudRepository<Evaluation, Long>, PagingAndSortingRepository<Evaluation, Long> {
	@Query("UPDATE Evaluation t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public List<Evaluation> findByActiveIsTrue();

}
