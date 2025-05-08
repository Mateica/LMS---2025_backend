package main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.SubjectRealization;

@Repository
public interface SubjectRealizationRepository extends CrudRepository<SubjectRealization, Long>, PagingAndSortingRepository<SubjectRealization, Long> {

}
