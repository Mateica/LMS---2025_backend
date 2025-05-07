package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface ControllerInterface<T> {
	@RequestMapping(method = RequestMethod.GET)	
	public ResponseEntity<Iterable<T>> findAll();
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<T> findById(@PathVariable("id") Long id);
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<T> create(@RequestBody T t);
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<T> update(@RequestBody T t, @PathVariable("id") Long id);
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<T> delete(@PathVariable("id") Long id);
}
