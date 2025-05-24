package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Role;
import main.model.StudentOnYear;

@Repository
public interface StudentOnYearRepository extends CrudRepository<StudentOnYear, Long>,PagingAndSortingRepository<StudentOnYear, Long> {
	@Query("UPDATE StudentOnYear t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public List<StudentOnYear> findByActiveIsTrue();
}
