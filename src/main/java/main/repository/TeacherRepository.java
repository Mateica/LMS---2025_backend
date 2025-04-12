package main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher,Long>, PagingAndSortingRepository<Teacher,Long> {

}
