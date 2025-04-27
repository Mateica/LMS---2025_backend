package main.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceInterface<T>{
	public Iterable<T> findAll();
	public Page<T> findAll(Pageable pageable);
	public Optional<T> findById(Long id);
	public T create(T t);
	public T update(T t);
	public void delete(Long id);
	public void softDelete(Long id);
	
}
