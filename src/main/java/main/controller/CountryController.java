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
import org.springframework.http.HttpStatusCode;
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

import main.dto.AccountDTO;
import main.dto.CountryDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.model.Account;
import main.model.Country;
import main.model.Place;
import main.service.CountryService;

@RestController
@RequestMapping("/api/countries")
public class CountryController implements ControllerInterface<CountryDTO> {
	@Autowired
	private CountryService service;

	@Override
	@GetMapping
	@Secured("{ADMIN}")
	public ResponseEntity<Iterable<CountryDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<CountryDTO> countries = new ArrayList<CountryDTO>();
		ArrayList<PlaceDTO> places = new ArrayList<PlaceDTO>();
		
		for(Country c : service.findAll()) {
			for(Place p : c.getPlaces()) {
				places.add(new PlaceDTO(p.getId(), p.getName(), new CountryDTO(p.getCountry().getId(), p.getCountry().getName(),
						new ArrayList<PlaceDTO>() , p.getCountry().getActive()), p.getCountry().getActive()));
			}
			countries.add(new CountryDTO(c.getId(), c.getName(),places ,c.getActive()));
		}
		
		return new ResponseEntity<Iterable<CountryDTO>>(countries, HttpStatus.OK);
	}
	
	@Override
	@GetMapping
	public ResponseEntity<Page<CountryDTO>> findAll(
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Country> countryPage = service.findAll(pageable);

	    List<CountryDTO> countryDTOs = countryPage.stream().map(c -> {
	        List<PlaceDTO> placeDTOs = c.getPlaces().stream().map(p ->
	            new PlaceDTO(
	                p.getId(),
	                p.getName(),
	                new CountryDTO(p.getCountry().getId(), p.getCountry().getName(), 
	                		new ArrayList<PlaceDTO>(), p.getCountry().getActive()),
	                p.getCountry().getActive()
	            )
	        ).collect(Collectors.toList());

	        return new CountryDTO(c.getId(), c.getName(), placeDTOs, c.getActive());
	    }).collect(Collectors.toList());

	    Page<CountryDTO> resultPage = new PageImpl<>(countryDTOs, pageable, countryPage.getTotalElements());

	    return new ResponseEntity<>(resultPage, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured("{ADMIN}")
	public ResponseEntity<CountryDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		ArrayList<PlaceDTO> places = new ArrayList<PlaceDTO>();
		Country c = service.findById(id).orElse(null);
		
		if(c == null) {
			return new ResponseEntity<CountryDTO>(HttpStatus.BAD_REQUEST);
		}
		
		for(Place p : c.getPlaces()) {
			places.add(new PlaceDTO(p.getId(), p.getName(), new CountryDTO(p.getCountry().getId(), p.getCountry().getName(),
					new ArrayList<PlaceDTO>() , p.getCountry().getActive()), p.getCountry().getActive()));
		}
		
		return new ResponseEntity<CountryDTO>(new CountryDTO(c.getId(), c.getName(),places, c.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured("{ADMIN}")
	public ResponseEntity<CountryDTO> create(@RequestBody CountryDTO t) {
		// TODO Auto-generated method stub
		Country c = service.create(new Country(null,t.getName(), new ArrayList<Place>(), true));
		
		if(c == null) {
			return new ResponseEntity<CountryDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<CountryDTO>(new CountryDTO(c.getId(), c.getName(), new ArrayList<PlaceDTO>(), c.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured("{ADMIN}")
	public ResponseEntity<CountryDTO> update(@RequestBody CountryDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		ArrayList<PlaceDTO> placesDTO = new ArrayList<PlaceDTO>();
		ArrayList<Place> places = new ArrayList<Place>();
		
		Country c = service.findById(id).orElse(null);
		
		if(c == null) {
			return new ResponseEntity<CountryDTO>(HttpStatus.BAD_REQUEST);
		}
		
		for(PlaceDTO pdto : t.getPlaces()) {
			places.add(new Place(pdto.getId(), pdto.getName(), 
					new Country(pdto.getCountry().getId(), pdto.getCountry().getName(),new ArrayList<Place>(), pdto.getCountry().getActive()), pdto.getActive()));
		}
		
		c.setId(t.getId());
		c.setName(t.getName());
		c.setPlaces(places);
		c.setActive(t.getActive());
		
		c = service.update(c);
		
		for(Place p : c.getPlaces()) {
			placesDTO.add(new PlaceDTO(p.getId(), p.getName(), new CountryDTO(p.getCountry().getId(), p.getCountry().getName(),
					new ArrayList<PlaceDTO>() , p.getCountry().getActive()), p.getCountry().getActive()));
		}
		
		return new ResponseEntity<CountryDTO>(new CountryDTO(c.getId(), c.getName(),placesDTO, c.getActive()), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	@Secured("{ADMIN}")
	public ResponseEntity<CountryDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
	@Secured("{ADMIN}")
	public ResponseEntity<CountryDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		ArrayList<PlaceDTO> places = new ArrayList<PlaceDTO>();
		Country c = service.findById(id).orElse(null);
		
		if(c == null) {
			return new ResponseEntity<CountryDTO>(HttpStatus.BAD_REQUEST);
		}
		
		for(Place p : c.getPlaces()) {
			places.add(new PlaceDTO(p.getId(), p.getName(), new CountryDTO(p.getCountry().getId(), p.getCountry().getName(),
					new ArrayList<PlaceDTO>() , p.getCountry().getActive()), p.getCountry().getActive()));
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<CountryDTO>(new CountryDTO(c.getId(), c.getName(),places, c.getActive()), HttpStatus.OK);
	}
}
