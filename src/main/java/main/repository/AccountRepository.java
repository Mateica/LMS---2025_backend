package main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>, PagingAndSortingRepository<Account, Long> {

}
