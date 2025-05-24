package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Outcome;
import main.model.Subject;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long>, PagingAndSortingRepository<Subject, Long> {
	@Query("UPDATE Subject t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	public List<Subject> findByActiveIsTrue();
}
