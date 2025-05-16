package main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.EducationGoal;

@Repository
public interface EducationGoalRepository extends CrudRepository<EducationGoal, Long>, PagingAndSortingRepository<EducationGoal, Long> {

}
