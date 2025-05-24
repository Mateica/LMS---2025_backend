package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.model.Address;
import main.model.File;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>, PagingAndSortingRepository<Address, Long> {
	@Query("UPDATE Address t SET t.active = false WHERE t.id = :id")
	public void softDelete(Long id);
	
	public List<Address> findByActiveIsTrue();
}
