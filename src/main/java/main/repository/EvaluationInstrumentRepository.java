package main.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.EvaluationInstrument;

@Repository
public interface EvaluationInstrumentRepository extends CrudRepository<EvaluationInstrument, Long>, PagingAndSortingRepository<EvaluationInstrument, Long> {
	@Query("UPDATE EvaluationInstrument t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
}
