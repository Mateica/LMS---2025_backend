package main.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.StudentAffairsOffice;

@Repository
public interface StudentAffairsOfficeRepository extends CrudRepository<StudentAffairsOffice, Long>, PagingAndSortingRepository<StudentAffairsOffice, Long> {
	@Query("UPDATE StudentAffairsOffice t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
}
