package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.File;

@Repository
public interface FileRepository extends CrudRepository<File, Long>, PagingAndSortingRepository<File, Long> {
	@Query("UPDATE File t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public List<File> findByActiveIsTrue();
}
