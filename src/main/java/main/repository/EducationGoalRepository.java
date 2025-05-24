package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.EducationGoal;

@Repository
public interface EducationGoalRepository extends CrudRepository<EducationGoal, Long>, PagingAndSortingRepository<EducationGoal, Long> {
	@Query("UPDATE EducationGoal t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	public List<EducationGoal> findByActiveIsTrue();
}
