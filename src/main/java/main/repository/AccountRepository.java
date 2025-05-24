package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>, PagingAndSortingRepository<Account, Long> {
	@Query("UPDATE Account t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public List<Account> findByRegisteredUserId(Long id);
	public List<Account> findByActiveIsTrue();
}
