package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Outcome;
import main.model.RegisteredUser;

@Repository
public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long>, PagingAndSortingRepository<RegisteredUser, Long> {
	@Query("UPDATE RegisteredUser r SET r.active = false WHERE r.id = :id")
	public void softDelete(Long id);
	public RegisteredUser findByUsername(String username);
	public RegisteredUser findByUsernameAndPassword(String username, String password);
	public RegisteredUser findByEmail(String email);
	public List<RegisteredUser> findByActiveIsTrue();
}
