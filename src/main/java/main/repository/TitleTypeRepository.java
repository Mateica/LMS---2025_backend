package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.dto.TitleTypeDTO;
import main.model.Outcome;
import main.model.TitleType;

@Repository
public interface TitleTypeRepository extends CrudRepository<TitleType, Long>, PagingAndSortingRepository<TitleType, Long> {
	@Query("UPDATE TitleType t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	public List<TitleType> findByActiveIsTrue();
}
