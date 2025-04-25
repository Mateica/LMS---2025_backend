package main.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>, PagingAndSortingRepository<Role, Long> {
	@Query("UPDATE Role r SET r.active = false WHERE r.id = :id")
	public void softDelete(Long id);
	
	public Role findByName(String name);
}
