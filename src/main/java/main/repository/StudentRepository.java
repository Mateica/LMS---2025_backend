package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.RegisteredUser;
import main.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {
	@Query("UPDATE Student t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public Student findByUser(RegisteredUser user);
	
	public List<Student> findByActiveIsTrue();
}
