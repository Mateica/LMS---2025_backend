package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import main.model.EvaluationGrade;
import main.model.Examination;

@Service
public interface ExaminationRepository extends CrudRepository<Examination, Long>, PagingAndSortingRepository<Examination, Long> {
	@Query("UPDATE Examination t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public List<Examination> findByActiveIsTrue();
}
