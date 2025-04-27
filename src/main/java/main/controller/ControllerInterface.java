package main.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface ControllerInterface<T>{
	public ResponseEntity<Iterable<T>> findAll();
	public ResponseEntity<Page<T>> findAll(
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending);
	public ResponseEntity<T> findById(Long id);
	public ResponseEntity<T> create(T t);
	public ResponseEntity<T> update(T t,Long id);
	public ResponseEntity<T> delete(Long id);
	public ResponseEntity<T> softDelete(Long id);
}
