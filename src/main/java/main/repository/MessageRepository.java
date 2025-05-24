package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.EvaluationGrade;
import main.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>, PagingAndSortingRepository<Message, Long> {
	@Query("UPDATE Message t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	public List<Message> findByActiveIsTrue();
}
