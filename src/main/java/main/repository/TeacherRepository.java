package main.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher,Long>, PagingAndSortingRepository<Teacher,Long> {
	@Query("UPDATE Teacher t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
}
