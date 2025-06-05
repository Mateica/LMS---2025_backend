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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.FileDTO;
import main.dto.TeacherDTO;
import main.dto.TeachingMaterialDTO;
import main.model.TeachingMaterial;
import main.service.TeachingMaterialService;

@RestController
@RequestMapping("/api/teachingMaterials")
public class TeachingMaterialController implements ControllerInterface<TeachingMaterialDTO> {
	
	@Autowired
	private TeachingMaterialService service;

	@Override
	@GetMapping
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<Iterable<TeachingMaterialDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<TeachingMaterialDTO> teachingMaterial = new ArrayList<TeachingMaterialDTO>();
		ArrayList<TeacherDTO> authors = new ArrayList<TeacherDTO>();
		
		for(TeachingMaterial tm : service.findAll()) {
			authors = (ArrayList<TeacherDTO>) tm.getAuthors()
												.stream()
												.map(a -> 
												new TeacherDTO(a.getId(), null, a.getFirstName(), a.getLastName(), a.getBiography(), null, null, null, null, null, null, null))
												.collect(Collectors.toList());
											
			teachingMaterial.add(new TeachingMaterialDTO(tm.getId(), tm.getName(), authors, tm.getYearOfPublication(),
					new FileDTO(tm.getFile().getId(), tm.getFile().getName(), tm.getFile().getUrl(), tm.getFile().getDescription(),
							null, null, null, null, null, tm.getFile().getDocument(), tm.getFile().getActive()), tm.getActive()));
		}
		
		return new ResponseEntity<Iterable<TeachingMaterialDTO>>(teachingMaterial, HttpStatus.OK);
	}

	@Override
	@GetMapping("/params")
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<Page<TeachingMaterialDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<TeachingMaterial> teachingMaterialPage = service.findAll(pageable);

	    List<TeachingMaterialDTO> teachingMaterialDTOs = teachingMaterialPage.stream().map(tm-> {
	        List<TeacherDTO> authors = (ArrayList<TeacherDTO>) tm.getAuthors()
					.stream()
					.map(a -> 
					new TeacherDTO(a.getId(), null, a.getFirstName(), a.getLastName(), a.getBiography(), null, null, null, null, null, null, null))
					.collect(Collectors.toList());

	        return new TeachingMaterialDTO(tm.getId(), tm.getName(), authors, tm.getYearOfPublication(),
					new FileDTO(tm.getFile().getId(), tm.getFile().getName(), tm.getFile().getUrl(), tm.getFile().getDescription(),
							null, null, null, null, null, tm.getFile().getDocument(), tm.getFile().getActive()), tm.getActive());
	    }).collect(Collectors.toList());

	    Page<TeachingMaterialDTO> resultPage = new PageImpl<>(teachingMaterialDTOs, pageable, teachingMaterialPage.getTotalElements());

	    return new ResponseEntity<Page<TeachingMaterialDTO>>(resultPage, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<Iterable<TeachingMaterialDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<TeachingMaterialDTO> teachingMaterial = new ArrayList<TeachingMaterialDTO>();
		ArrayList<TeacherDTO> authors = new ArrayList<TeacherDTO>();
		
		for(TeachingMaterial tm : service.findAllActive()) {
			authors = (ArrayList<TeacherDTO>) tm.getAuthors()
												.stream()
												.map(a -> 
												new TeacherDTO(a.getId(), null, a.getFirstName(), a.getLastName(), a.getBiography(), null, null, null, null, null, null, null))
												.collect(Collectors.toList());
											
			teachingMaterial.add(new TeachingMaterialDTO(tm.getId(), tm.getName(), authors, tm.getYearOfPublication(),
					new FileDTO(tm.getFile().getId(), tm.getFile().getName(), tm.getFile().getUrl(), tm.getFile().getDescription(),
							null, null, null, null, null, tm.getFile().getDocument(), tm.getFile().getActive()), tm.getActive()));
		}
		
		return new ResponseEntity<Iterable<TeachingMaterialDTO>>(teachingMaterial, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<TeachingMaterialDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		TeachingMaterial tm = service.findById(id).orElse(null);
		
		if(tm == null) {
			return new ResponseEntity<TeachingMaterialDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<TeacherDTO> authors = (ArrayList<TeacherDTO>) tm.getAuthors()
				.stream()
				.map(a -> 
				new TeacherDTO(a.getId(), null, a.getFirstName(), a.getLastName(), a.getBiography(), null, null, null, null, null, null, null))
				.collect(Collectors.toList());
		
		
		return new ResponseEntity<TeachingMaterialDTO>(new TeachingMaterialDTO(tm.getId(), tm.getName(), authors, tm.getYearOfPublication(),
				new FileDTO(tm.getFile().getId(), tm.getFile().getName(), tm.getFile().getUrl(), tm.getFile().getDescription(),
						null, null, null, null, null, tm.getFile().getDocument(), tm.getFile().getActive()), tm.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN", "STAFF", "TEACHER"})
	public ResponseEntity<TeachingMaterialDTO> create(@RequestBody TeachingMaterialDTO t) {
		// TODO Auto-generated method stub
		TeachingMaterial tm = service.create(new TeachingMaterial(null, t.getName(), 
												service.findById(t.getId()).get().getAuthors(),
												t.getYearOfPublication(),
												service.findById(t.getId()).get().getFile(), true));
		
		if(tm == null) {
			return new ResponseEntity<TeachingMaterialDTO>(HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<TeacherDTO> authors = (ArrayList<TeacherDTO>) tm.getAuthors()
				.stream()
				.map(a -> 
				new TeacherDTO(a.getId(), null, a.getFirstName(), a.getLastName(), a.getBiography(), null, null, null, null, null, null, null))
				.collect(Collectors.toList());
		
		return new ResponseEntity<TeachingMaterialDTO>(new TeachingMaterialDTO(tm.getId(), tm.getName(), authors, tm.getYearOfPublication(),
				new FileDTO(tm.getFile().getId(), tm.getFile().getName(), tm.getFile().getUrl(), tm.getFile().getDescription(),
						null, null, null, null, null, tm.getFile().getDocument(), tm.getFile().getActive()), tm.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN", "STAFF", "TEACHER"})
	public ResponseEntity<TeachingMaterialDTO> update(@RequestBody TeachingMaterialDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		TeachingMaterial tm = service.findById(id).orElse(null);
		
		if(tm == null) {
			return new ResponseEntity<TeachingMaterialDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<TeacherDTO> authors = (ArrayList<TeacherDTO>) tm.getAuthors()
				.stream()
				.map(a -> 
				new TeacherDTO(a.getId(), null, a.getFirstName(), a.getLastName(), a.getBiography(), null, null, null, null, null, null, null))
				.collect(Collectors.toList());
		
		tm.setId(t.getId());
		tm.setName(t.getName());
		tm.setAuthors(service.findById(t.getId()).get().getAuthors());
		tm.setYearOfPublication(t.getYearOfPublication());
		tm.setFile(service.findById(t.getId()).get().getFile());
		tm.setActive(t.getActive());
		
		tm = service.update(tm);
		
		return new ResponseEntity<TeachingMaterialDTO>(new TeachingMaterialDTO(tm.getId(), tm.getName(), authors, tm.getYearOfPublication(),
				new FileDTO(tm.getFile().getId(), tm.getFile().getName(), tm.getFile().getUrl(), tm.getFile().getDescription(),
						null, null, null, null, null, tm.getFile().getDocument(), tm.getFile().getActive()), tm.getActive()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TeachingMaterialDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
	@Secured({"ADMIN", "STAFF", "TEACHER"})
	public ResponseEntity<TeachingMaterialDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		TeachingMaterial tm = service.findById(id).orElse(null);
		
		if(tm == null) {
			return new ResponseEntity<TeachingMaterialDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<TeacherDTO> authors = (ArrayList<TeacherDTO>) tm.getAuthors()
				.stream()
				.map(a -> 
				new TeacherDTO(a.getId(), null, a.getFirstName(), a.getLastName(), a.getBiography(), null, null, null, null, null, null, null))
				.collect(Collectors.toList());
		
		service.softDelete(id);
		
		return new ResponseEntity<TeachingMaterialDTO>(new TeachingMaterialDTO(tm.getId(), tm.getName(), authors, tm.getYearOfPublication(),
				new FileDTO(tm.getFile().getId(), tm.getFile().getName(), tm.getFile().getUrl(), tm.getFile().getDescription(),
						null, null, null, null, null, tm.getFile().getDocument(), tm.getFile().getActive()), tm.getActive()), HttpStatus.OK);
	}
	
}
