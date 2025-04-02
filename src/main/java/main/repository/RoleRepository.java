package main.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import main.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	@Query("UPDATE Role r SET r.active = false WHERE r.id = :id")
	public void softDelete(Long id);
}
