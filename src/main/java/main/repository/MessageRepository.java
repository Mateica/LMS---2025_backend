package main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>, PagingAndSortingRepository<Message, Long> {

}
