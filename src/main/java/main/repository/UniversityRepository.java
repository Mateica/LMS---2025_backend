package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Faculty;
import main.model.University;

@Repository
public interface UniversityRepository extends CrudRepository<University, Long>, PagingAndSortingRepository<University, Long> {
	@Query("UPDATE Faculty t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public University findByFaculty(Faculty faculty);
	public List<Faculty> findByActiveIsTrue();
}
