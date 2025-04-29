package main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.CountryDTO;
import main.dto.PlaceDTO;
import main.model.Country;
import main.model.Place;
import main.service.PlaceService;

@RestController
@RequestMapping("/api/places")
public class PlaceController implements ControllerInterface<PlaceDTO> {
	@Autowired
	private PlaceService service;

	@Override
	@GetMapping
	@Secured("{ADMIN}")
	public ResponseEntity<Iterable<PlaceDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<PlaceDTO> places = new ArrayList<PlaceDTO>();
		
		for(Place p : service.findAll()) {
			places.add(new PlaceDTO(p.getId(), p.getName(), 
					new CountryDTO(p.getCountry().getId(), p.getCountry().getName(), 
							new ArrayList<PlaceDTO>(), p.getCountry().getActive()), p.getActive()));
		}
		
		return new ResponseEntity<Iterable<PlaceDTO>>(places, HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/params")
	@Secured("{ADMIN}")
	public ResponseEntity<Page<PlaceDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Place> placePage = service.findAll(pageable);

	    List<PlaceDTO> placeDTOs = placePage.stream().map(p -> 
	        new PlaceDTO(
	            p.getId(),
	            p.getName(),
	            new CountryDTO(
	                p.getCountry().getId(),
	                p.getCountry().getName(),
	                new ArrayList<PlaceDTO>(),
	                p.getCountry().getActive()
	            ),
	            p.getActive()
	        )
	    ).collect(Collectors.toList());

	    Page<PlaceDTO> resultPage = new PageImpl<>(placeDTOs, pageable, placePage.getTotalElements());

	    return new ResponseEntity<>(resultPage, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured("{ADMIN}")
	public ResponseEntity<PlaceDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Place p = service.findById(id).orElse(null);
		
		if(p == null) {
			return new ResponseEntity<PlaceDTO>(HttpStatus.BAD_REQUEST);
		}
		
		
		return new ResponseEntity<PlaceDTO>(new PlaceDTO(p.getId(), p.getName(), 
				new CountryDTO(p.getCountry().getId(), p.getCountry().getName(), 
						new ArrayList<PlaceDTO>(), p.getCountry().getActive()), p.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured("{ADMIN}")
	public ResponseEntity<PlaceDTO> create(@RequestBody PlaceDTO t) {
		// TODO Auto-generated method stub
		Place p = service.create(new Place(null,t.getName(), 
				new Country(t.getCountry().getId(), t.getCountry().getName(), new ArrayList<Place>(), t.getCountry().getActive()),true));
		
		if(p == null) {
			return new ResponseEntity<PlaceDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<PlaceDTO>(new PlaceDTO(p.getId(), p.getName(), 
				new CountryDTO(p.getCountry().getId(), p.getCountry().getName(), 
						new ArrayList<PlaceDTO>(), p.getCountry().getActive()), p.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured("{ADMIN}")
	public ResponseEntity<PlaceDTO> update(@RequestBody PlaceDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Place p = service.findById(id).orElse(null);
		
		if(p == null) {
			return new ResponseEntity<PlaceDTO>(HttpStatus.BAD_REQUEST);
		}
		
		p.setId(t.getId());
		p.setName(t.getName());
		p.setCounty(new Country(t.getCountry().getId(), t.getCountry().getName(), 
						new ArrayList<Place>(), t.getCountry().getActive()));
		p.setActive(t.getActive());
		
		p = service.update(p);
		
		return new ResponseEntity<PlaceDTO>(new PlaceDTO(p.getId(), p.getName(), 
				new CountryDTO(p.getCountry().getId(), p.getCountry().getName(), 
						new ArrayList<PlaceDTO>(), p.getCountry().getActive()), p.getActive()), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	@Secured("{ADMIN}")
	public ResponseEntity<PlaceDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
	@Secured("{ADMIN}")
	public ResponseEntity<PlaceDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Place p = service.findById(id).orElse(null);
		
		if(p == null) {
			return new ResponseEntity<PlaceDTO>(HttpStatus.BAD_REQUEST);
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<PlaceDTO>(new PlaceDTO(p.getId(), p.getName(), 
				new CountryDTO(p.getCountry().getId(), p.getCountry().getName(), 
						new ArrayList<PlaceDTO>(), p.getCountry().getActive()), p.getActive()), HttpStatus.OK);
	}
}
