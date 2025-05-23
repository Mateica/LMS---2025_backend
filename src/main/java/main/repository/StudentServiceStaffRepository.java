package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.StudentServiceStaff;
import main.model.Teacher;

@Repository
public interface StudentServiceStaffRepository extends CrudRepository<StudentServiceStaff, Long>, PagingAndSortingRepository<StudentServiceStaff, Long> {
	@Query("UPDATE StudentServiceStaff t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public List<StudentServiceStaff> findByActiveIsTrue();
}
