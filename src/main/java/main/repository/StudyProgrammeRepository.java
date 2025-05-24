package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Outcome;
import main.model.StudyProgramme;

@Repository
public interface StudyProgrammeRepository extends CrudRepository<StudyProgramme, Long>, PagingAndSortingRepository<StudyProgramme, Long> {
	@Query("UPDATE StudyProgramme t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	public List<StudyProgramme> findByActiveIsTrue();
}
