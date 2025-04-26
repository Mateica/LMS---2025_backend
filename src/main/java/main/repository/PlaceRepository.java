package main.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Place;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long>, PagingAndSortingRepository<Place, Long> {
	@Query("UPDATE Place t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
}
