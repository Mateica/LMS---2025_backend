package main.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Teacher;
import main.model.TeacherOnRealization;

@Repository
public interface TeacherOnRealizationRepository extends CrudRepository<TeacherOnRealization,Long>, PagingAndSortingRepository<TeacherOnRealization,Long> {
	@Query("UPDATE TeacherOnRealization t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
}
