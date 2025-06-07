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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import main.dto.AddressDTO;
import main.dto.CountryDTO;
import main.dto.FacultyDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.dto.RoleDTO;
import main.dto.TeacherDTO;
import main.dto.UniversityDTO;
import main.model.Role;
import main.model.University;
import main.service.UniversityService;

public class UniversityController implements ControllerInterface<UniversityDTO> {
	@Autowired
	private UniversityService service;

	@Override
	@GetMapping
	@Secured({"ADMIN","TEACHER", "STAFF", "STUDENT", "USER"})
	public ResponseEntity<Iterable<UniversityDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<UniversityDTO> universities = new ArrayList<UniversityDTO>();
		ArrayList<FacultyDTO> faculties = new ArrayList<FacultyDTO>();
		
		for(University u : service.findAll()) {
			
			faculties = (ArrayList<FacultyDTO>) u.getFaculties().stream().map(f -> 
			new FacultyDTO(f.getId(), f.getName(),
					new AddressDTO(f.getAddress().getId(), f.getAddress().getStreet(), f.getAddress().getHouseNumber(),
							new PlaceDTO(f.getAddress().getPlace().getId(), f.getAddress().getPlace().getName(),
									new CountryDTO(f.getAddress().getPlace().getCountry().getId(), f.getAddress().getPlace().getCountry().getName(), null, f.getAddress().getPlace().getCountry().getActive()),
									f.getAddress().getPlace().getActive()),
							f.getAddress().getActive()),
					new TeacherDTO(f.getHeadmaster().getId(), 
							new RegisteredUserDTO(f.getHeadmaster().getUser().getUsername(), null, f.getHeadmaster().getUser().getEmail()), 
							f.getHeadmaster().getFirstName(), f.getHeadmaster().getLastName(),
							null, null, null, null, null, null, null, null),
					null, f.getContactDetails(), f.getDescription(), null, null, null, null, f.getActive()))
					.collect(Collectors.toList());
			
			universities.add(new UniversityDTO(u.getId(), u.getName(), u.getDateEstablished(),
					new AddressDTO(u.getAddress().getId(), u.getAddress().getStreet(), u.getAddress().getHouseNumber(),
							new PlaceDTO(u.getAddress().getPlace().getId(), u.getAddress().getPlace().getName(),
									new CountryDTO(u.getAddress().getPlace().getCountry().getId(), u.getAddress().getPlace().getCountry().getName(), null, u.getAddress().getPlace().getCountry().getActive()),
									u.getAddress().getPlace().getActive()),
							u.getAddress().getActive()),
					new TeacherDTO(u.getRector().getId(), 
							new RegisteredUserDTO(u.getRector().getUser().getUsername(), null, u.getRector().getUser().getEmail()), 
							u.getRector().getFirstName(), u.getRector().getLastName(),
							null, null, null, null, null, null, null, null),
					u.getContactDetails(), u.getDescription(),
					null, u.getActive()));
		}
		
		return new ResponseEntity<Iterable<UniversityDTO>>(universities, HttpStatus.OK);
	}

	@Override
	@GetMapping("/params")
	@Secured({"ADMIN","TEACHER", "STAFF", "STUDENT", "USER"})
	public ResponseEntity<Page<UniversityDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<University> universityPage = service.findAll(pageable);

	    List<UniversityDTO> universityDTOs = universityPage.stream().map(u -> {
	    	List<FacultyDTO> faculties = (ArrayList<FacultyDTO>) u.getFaculties().stream().map(f -> 
				new FacultyDTO(f.getId(), f.getName(),
						new AddressDTO(f.getAddress().getId(), f.getAddress().getStreet(), f.getAddress().getHouseNumber(),
								new PlaceDTO(f.getAddress().getPlace().getId(), f.getAddress().getPlace().getName(),
										new CountryDTO(f.getAddress().getPlace().getCountry().getId(), f.getAddress().getPlace().getCountry().getName(), null, f.getAddress().getPlace().getCountry().getActive()),
										f.getAddress().getPlace().getActive()),
								f.getAddress().getActive()),
						new TeacherDTO(f.getHeadmaster().getId(), 
								new RegisteredUserDTO(f.getHeadmaster().getUser().getUsername(), null, f.getHeadmaster().getUser().getEmail()), 
								f.getHeadmaster().getFirstName(), f.getHeadmaster().getLastName(),
								null, null, null, null, null, null, null, null),
						null, f.getContactDetails(), f.getDescription(), null, null, null, null, f.getActive()))
						.collect(Collectors.toList());

	    	return new UniversityDTO(u.getId(), u.getName(), u.getDateEstablished(),
					new AddressDTO(u.getAddress().getId(), u.getAddress().getStreet(), u.getAddress().getHouseNumber(),
							new PlaceDTO(u.getAddress().getPlace().getId(), u.getAddress().getPlace().getName(),
									new CountryDTO(u.getAddress().getPlace().getCountry().getId(), u.getAddress().getPlace().getCountry().getName(), null, u.getAddress().getPlace().getCountry().getActive()),
									u.getAddress().getPlace().getActive()),
							u.getAddress().getActive()),
					new TeacherDTO(u.getRector().getId(), 
							new RegisteredUserDTO(u.getRector().getUser().getUsername(), null, u.getRector().getUser().getEmail()), 
							u.getRector().getFirstName(), u.getRector().getLastName(),
							null, null, null, null, null, null, null, null),
					u.getContactDetails(), u.getDescription(),
					null, u.getActive());
	    }).collect(Collectors.toList());

	    Page<UniversityDTO> resultPage = new PageImpl<UniversityDTO>(universityDTOs, pageable, universityPage.getTotalElements());

	    return new ResponseEntity<Page<UniversityDTO>>(resultPage, HttpStatus.OK);
	}
	
	@GetMapping
	@Secured({"ADMIN","TEACHER", "STAFF", "STUDENT", "USER"})
	public ResponseEntity<Iterable<UniversityDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<UniversityDTO> universities = new ArrayList<UniversityDTO>();
		ArrayList<FacultyDTO> faculties = new ArrayList<FacultyDTO>();
		
		for(University u : service.findAllActive()) {
			
			faculties = (ArrayList<FacultyDTO>) u.getFaculties().stream().map(f -> 
			new FacultyDTO(f.getId(), f.getName(),
					new AddressDTO(f.getAddress().getId(), f.getAddress().getStreet(), f.getAddress().getHouseNumber(),
							new PlaceDTO(f.getAddress().getPlace().getId(), f.getAddress().getPlace().getName(),
									new CountryDTO(f.getAddress().getPlace().getCountry().getId(), f.getAddress().getPlace().getCountry().getName(), null, f.getAddress().getPlace().getCountry().getActive()),
									f.getAddress().getPlace().getActive()),
							f.getAddress().getActive()),
					new TeacherDTO(f.getHeadmaster().getId(), 
							new RegisteredUserDTO(f.getHeadmaster().getUser().getUsername(), null, f.getHeadmaster().getUser().getEmail()), 
							f.getHeadmaster().getFirstName(), f.getHeadmaster().getLastName(),
							null, null, null, null, null, null, null, null),
					null, f.getContactDetails(), f.getDescription(), null, null, null, null, f.getActive()))
					.collect(Collectors.toList());
			
			universities.add(new UniversityDTO(u.getId(), u.getName(), u.getDateEstablished(),
					new AddressDTO(u.getAddress().getId(), u.getAddress().getStreet(), u.getAddress().getHouseNumber(),
							new PlaceDTO(u.getAddress().getPlace().getId(), u.getAddress().getPlace().getName(),
									new CountryDTO(u.getAddress().getPlace().getCountry().getId(), u.getAddress().getPlace().getCountry().getName(), null, u.getAddress().getPlace().getCountry().getActive()),
									u.getAddress().getPlace().getActive()),
							u.getAddress().getActive()),
					new TeacherDTO(u.getRector().getId(), 
							new RegisteredUserDTO(u.getRector().getUser().getUsername(), null, u.getRector().getUser().getEmail()), 
							u.getRector().getFirstName(), u.getRector().getLastName(),
							null, null, null, null, null, null, null, null),
					u.getContactDetails(), u.getDescription(),
					null, u.getActive()));
		}
		
		return new ResponseEntity<Iterable<UniversityDTO>>(universities, HttpStatus.OK);
	}
	

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN","TEACHER", "STAFF", "STUDENT", "USER"})
	public ResponseEntity<UniversityDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		University u = service.findById(id).orElse(null);
		
		if(u == null) {
			return new ResponseEntity<UniversityDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<FacultyDTO> faculties = new ArrayList<FacultyDTO>();
		faculties = (ArrayList<FacultyDTO>) u.getFaculties().stream().map(f -> 
		new FacultyDTO(f.getId(), f.getName(),
				new AddressDTO(f.getAddress().getId(), f.getAddress().getStreet(), f.getAddress().getHouseNumber(),
						new PlaceDTO(f.getAddress().getPlace().getId(), f.getAddress().getPlace().getName(),
								new CountryDTO(f.getAddress().getPlace().getCountry().getId(), f.getAddress().getPlace().getCountry().getName(), null, f.getAddress().getPlace().getCountry().getActive()),
								f.getAddress().getPlace().getActive()),
						f.getAddress().getActive()),
				new TeacherDTO(f.getHeadmaster().getId(), 
						new RegisteredUserDTO(f.getHeadmaster().getUser().getUsername(), null, f.getHeadmaster().getUser().getEmail()), 
						f.getHeadmaster().getFirstName(), f.getHeadmaster().getLastName(),
						null, null, null, null, null, null, null, null),
				null, f.getContactDetails(), f.getDescription(), null, null, null, null, f.getActive()))
				.collect(Collectors.toList());
		
		
		return new ResponseEntity<UniversityDTO>(new UniversityDTO(u.getId(), u.getName(), u.getDateEstablished(),
					new AddressDTO(u.getAddress().getId(), u.getAddress().getStreet(), u.getAddress().getHouseNumber(),
							new PlaceDTO(u.getAddress().getPlace().getId(), u.getAddress().getPlace().getName(),
									new CountryDTO(u.getAddress().getPlace().getCountry().getId(), u.getAddress().getPlace().getCountry().getName(), null, u.getAddress().getPlace().getCountry().getActive()),
									u.getAddress().getPlace().getActive()),
							u.getAddress().getActive()),
					new TeacherDTO(u.getRector().getId(), 
							new RegisteredUserDTO(u.getRector().getUser().getUsername(), null, u.getRector().getUser().getEmail()), 
							u.getRector().getFirstName(), u.getRector().getLastName(),
							null, null, null, null, null, null, null, null),
					u.getContactDetails(), u.getDescription(),
					null, u.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN"})
	public ResponseEntity<UniversityDTO> create(@RequestBody UniversityDTO t) {
		// TODO Auto-generated method stub
		University u = service.create(new University(null, t.getName(), t.getDateEstablished(),
													service.findById(t.getId()).get().getAddress(),
													service.findById(t.getId()).get().getRector(),
													t.getContactDetails(),
													t.getDescription(), 
													service.findById(t.getId()).get().getFaculties(), true));
		
		if(u == null) {
			return new ResponseEntity<UniversityDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<FacultyDTO> faculties = new ArrayList<FacultyDTO>();
		
		faculties = (ArrayList<FacultyDTO>) u.getFaculties().stream().map(f -> 
		new FacultyDTO(f.getId(), f.getName(),
				new AddressDTO(f.getAddress().getId(), f.getAddress().getStreet(), f.getAddress().getHouseNumber(),
						new PlaceDTO(f.getAddress().getPlace().getId(), f.getAddress().getPlace().getName(),
								new CountryDTO(f.getAddress().getPlace().getCountry().getId(), f.getAddress().getPlace().getCountry().getName(), null, f.getAddress().getPlace().getCountry().getActive()),
								f.getAddress().getPlace().getActive()),
						f.getAddress().getActive()),
				new TeacherDTO(f.getHeadmaster().getId(), 
						new RegisteredUserDTO(f.getHeadmaster().getUser().getUsername(), null, f.getHeadmaster().getUser().getEmail()), 
						f.getHeadmaster().getFirstName(), f.getHeadmaster().getLastName(),
						null, null, null, null, null, null, null, null),
				null, f.getContactDetails(), f.getDescription(), null, null, null, null, f.getActive()))
				.collect(Collectors.toList());
		
		return new ResponseEntity<UniversityDTO>(new UniversityDTO(u.getId(), u.getName(), u.getDateEstablished(),
				new AddressDTO(u.getAddress().getId(), u.getAddress().getStreet(), u.getAddress().getHouseNumber(),
						new PlaceDTO(u.getAddress().getPlace().getId(), u.getAddress().getPlace().getName(),
								new CountryDTO(u.getAddress().getPlace().getCountry().getId(), u.getAddress().getPlace().getCountry().getName(), null, u.getAddress().getPlace().getCountry().getActive()),
								u.getAddress().getPlace().getActive()),
						u.getAddress().getActive()),
				new TeacherDTO(u.getRector().getId(), 
						new RegisteredUserDTO(u.getRector().getUser().getUsername(), null, u.getRector().getUser().getEmail()), 
						u.getRector().getFirstName(), u.getRector().getLastName(),
						null, null, null, null, null, null, null, null),
				u.getContactDetails(), u.getDescription(),
				null, u.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<UniversityDTO> update(@RequestBody UniversityDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		University u = service.findById(id).orElse(null);
		
		if(u == null) {
			return new ResponseEntity<UniversityDTO>(HttpStatus.NOT_FOUND);
		}
		
		u.setId(t.getId());
		u.setName(t.getName());
		u.setDateEstablished(t.getDateEstablished());
		u.setAddress(service.findById(t.getId()).get().getAddress());
		u.setRector(service.findById(t.getId()).get().getRector());
		u.setContactDetails(t.getContactDetails());
		u.setDescription(t.getDescription());
		u.setFaculties(service.findById(t.getId()).get().getFaculties());
		u.setActive(t.getActive());
		
		u = service.update(u);
		
		ArrayList<FacultyDTO> faculties = new ArrayList<FacultyDTO>();
		
		faculties = (ArrayList<FacultyDTO>) u.getFaculties().stream().map(f -> 
		new FacultyDTO(f.getId(), f.getName(),
				new AddressDTO(f.getAddress().getId(), f.getAddress().getStreet(), f.getAddress().getHouseNumber(),
						new PlaceDTO(f.getAddress().getPlace().getId(), f.getAddress().getPlace().getName(),
								new CountryDTO(f.getAddress().getPlace().getCountry().getId(), f.getAddress().getPlace().getCountry().getName(), null, f.getAddress().getPlace().getCountry().getActive()),
								f.getAddress().getPlace().getActive()),
						f.getAddress().getActive()),
				new TeacherDTO(f.getHeadmaster().getId(), 
						new RegisteredUserDTO(f.getHeadmaster().getUser().getUsername(), null, f.getHeadmaster().getUser().getEmail()), 
						f.getHeadmaster().getFirstName(), f.getHeadmaster().getLastName(),
						null, null, null, null, null, null, null, null),
				null, f.getContactDetails(), f.getDescription(), null, null, null, null, f.getActive()))
				.collect(Collectors.toList());
		
		
		return new ResponseEntity<UniversityDTO>(new UniversityDTO(u.getId(), u.getName(), u.getDateEstablished(),
					new AddressDTO(u.getAddress().getId(), u.getAddress().getStreet(), u.getAddress().getHouseNumber(),
							new PlaceDTO(u.getAddress().getPlace().getId(), u.getAddress().getPlace().getName(),
									new CountryDTO(u.getAddress().getPlace().getCountry().getId(), u.getAddress().getPlace().getCountry().getName(), null, u.getAddress().getPlace().getCountry().getActive()),
									u.getAddress().getPlace().getActive()),
							u.getAddress().getActive()),
					new TeacherDTO(u.getRector().getId(), 
							new RegisteredUserDTO(u.getRector().getUser().getUsername(), null, u.getRector().getUser().getEmail()), 
							u.getRector().getFirstName(), u.getRector().getLastName(),
							null, null, null, null, null, null, null, null),
					u.getContactDetails(), u.getDescription(),
					null, u.getActive()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<UniversityDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PatchMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<UniversityDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		University u = service.findById(id).orElse(null);
		
		if(u == null) {
			return new ResponseEntity<UniversityDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<FacultyDTO> faculties = new ArrayList<FacultyDTO>();
		faculties = (ArrayList<FacultyDTO>) u.getFaculties().stream().map(f -> 
		new FacultyDTO(f.getId(), f.getName(),
				new AddressDTO(f.getAddress().getId(), f.getAddress().getStreet(), f.getAddress().getHouseNumber(),
						new PlaceDTO(f.getAddress().getPlace().getId(), f.getAddress().getPlace().getName(),
								new CountryDTO(f.getAddress().getPlace().getCountry().getId(), f.getAddress().getPlace().getCountry().getName(), null, f.getAddress().getPlace().getCountry().getActive()),
								f.getAddress().getPlace().getActive()),
						f.getAddress().getActive()),
				new TeacherDTO(f.getHeadmaster().getId(), 
						new RegisteredUserDTO(f.getHeadmaster().getUser().getUsername(), null, f.getHeadmaster().getUser().getEmail()), 
						f.getHeadmaster().getFirstName(), f.getHeadmaster().getLastName(),
						null, null, null, null, null, null, null, null),
				null, f.getContactDetails(), f.getDescription(), null, null, null, null, f.getActive()))
				.collect(Collectors.toList());
		
		service.softDelete(id);
		
		
		return new ResponseEntity<UniversityDTO>(new UniversityDTO(u.getId(), u.getName(), u.getDateEstablished(),
					new AddressDTO(u.getAddress().getId(), u.getAddress().getStreet(), u.getAddress().getHouseNumber(),
							new PlaceDTO(u.getAddress().getPlace().getId(), u.getAddress().getPlace().getName(),
									new CountryDTO(u.getAddress().getPlace().getCountry().getId(), u.getAddress().getPlace().getCountry().getName(), null, u.getAddress().getPlace().getCountry().getActive()),
									u.getAddress().getPlace().getActive()),
							u.getAddress().getActive()),
					new TeacherDTO(u.getRector().getId(), 
							new RegisteredUserDTO(u.getRector().getUser().getUsername(), null, u.getRector().getUser().getEmail()), 
							u.getRector().getFirstName(), u.getRector().getLastName(),
							null, null, null, null, null, null, null, null),
					u.getContactDetails(), u.getDescription(),
					null, u.getActive()), HttpStatus.OK);
	}
}
