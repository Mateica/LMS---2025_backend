package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Faculty;
import main.model.RegisteredUser;
import main.model.Student;
import main.model.University;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Long>, PagingAndSortingRepository<Faculty, Long> {
	@Query("UPDATE Faculty t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public List<Faculty> findByUniversity(University university);
	public List<Faculty> findByActiveIsTrue();
}
