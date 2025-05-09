package main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Outcome;

@Repository
public interface OutcomeRepository extends CrudRepository<Outcome, Long>, PagingAndSortingRepository<Outcome, Long> {

}
