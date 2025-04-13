package main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.StudyProgramme;

@Repository
public interface StudyProgrammeRepository extends CrudRepository<StudyProgramme, Long>, PagingAndSortingRepository<StudyProgramme, Long> {

}
