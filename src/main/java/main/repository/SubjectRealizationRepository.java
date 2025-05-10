package main.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.SubjectRealization;

@Repository
public interface SubjectRealizationRepository extends CrudRepository<SubjectRealization, Long>, PagingAndSortingRepository<SubjectRealization, Long> {
	@Query("UPDATE SubjectRealization t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
}
