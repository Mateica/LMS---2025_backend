package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.TeachingMaterial;

@Repository
public interface TeachingMaterialRepository extends CrudRepository<TeachingMaterial, Long>, 
PagingAndSortingRepository<TeachingMaterial, Long> {
	@Query("UPDATE TeachingMaterial t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public List<TeachingMaterial> findByActiveIsTrue();
}
