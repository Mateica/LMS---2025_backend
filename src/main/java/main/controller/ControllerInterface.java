package main.controller;

import org.springframework.http.ResponseEntity;

public interface ControllerInterface<T>{
	public ResponseEntity<Iterable<T>> findAll();
	public ResponseEntity<T> findById(Long id);
	public ResponseEntity<T> create(T t);
	public ResponseEntity<T> update(T t,Long id);
	public ResponseEntity<T> delete(Long id);
	public ResponseEntity<T> softDelete(Long id);
}
