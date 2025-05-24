package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.EvaluationGrade;
import main.model.EvaluationType;

@Repository
public interface EvaluationTypeRepository extends CrudRepository<EvaluationType, Long>, PagingAndSortingRepository<EvaluationType, Long> {
	@Query("UPDATE EvaluationType t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	public List<EvaluationType> findByActiveIsTrue();
}
