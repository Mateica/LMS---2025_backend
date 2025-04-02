package main.service;

import java.util.Optional;

public interface ServiceInterface<T>{
	public Iterable<T> findAll();
	public Optional<T> findById(Long id);
	public T create(T t);
	public T update(T t);
	public void delete(Long id);
	public void softDelete(Long id);
	
}
