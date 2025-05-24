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

import main.dto.AddressDTO;
import main.dto.CountryDTO;
import main.dto.FacultyDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentAffairsOfficeDTO;
import main.dto.StudentServiceStaffDTO;
import main.dto.SubjectDTO;
import main.dto.SubjectRealizationDTO;
import main.dto.TeacherDTO;
import main.dto.TeacherOnRealizationDTO;
import main.dto.TeachingTypeDTO;
import main.dto.TitleDTO;
import main.dto.UniversityDTO;
import main.model.Department;
import main.model.EvaluationGrade;
import main.model.Faculty;
import main.model.File;
import main.model.RegisteredUser;
import main.model.StudentAffairsOffice;
import main.model.StudentServiceStaff;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.TeachingMaterial;
import main.model.Title;
import main.service.EvaluationGradeService;
import main.service.FacultyService;
import main.service.RegisteredUserService;
import main.service.ServiceInterface;
import main.service.StudentAffairsOfficeService;
import main.service.StudentServiceStaffService;
import main.service.TeacherOnRealizationService;
import main.service.TeacherService;
import main.service.UniversityService;

@RestController
@RequestMapping("/api/studentAffairsOffices")
public class StudentAffairsOfficeController implements ControllerInterface<StudentAffairsOfficeDTO> {
	@Autowired
	private StudentAffairsOfficeService service;
	
	@Autowired
	private StudentServiceStaffService staffService;
	
	@Autowired
	private RegisteredUserService userService;
	
	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private UniversityService universityService;
	
	@Autowired
	private TeacherOnRealizationService teacherOnRealizationService;
	
	@Autowired
	private EvaluationGradeService gradeService;
	
	@Override
	@GetMapping
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<Iterable<StudentAffairsOfficeDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<StudentAffairsOfficeDTO> offices = new ArrayList<StudentAffairsOfficeDTO>();
		ArrayList<StudentServiceStaffDTO> staff = new ArrayList<StudentServiceStaffDTO>();
		
		for(StudentAffairsOffice s : service.findAll()) {
			staff = (ArrayList<StudentServiceStaffDTO>) s.getStaff()
					.stream()
					.map(n -> new StudentServiceStaffDTO(n.getId(), 
							new RegisteredUserDTO(n.getRegisteredUser().getUsername(), null, n.getRegisteredUser().getEmail()),
							n.getFirstName(),
							n.getLastName(), null, n.getActive()))
					.collect(Collectors.toList());
			offices.add(new StudentAffairsOfficeDTO(s.getId(), staff, 
					new FacultyDTO(s.getFaculty().getId(), s.getFaculty().getName(),
							new AddressDTO(s.getFaculty().getAddress().getId(), 
									s.getFaculty().getAddress().getStreet(),
									s.getFaculty().getAddress().getHouseNumber(), null,
									s.getFaculty().getAddress().getActive()),
							new TeacherDTO(null, null, null, null, null, null, null, null, null, null, null, null),
							new UniversityDTO(null, null, null, null, null, null, null, null, null), 
							s.getFaculty().getContactDetails(), s.getFaculty().getDescription(),
							null, null, null, null, s.getFaculty().getActive()),
					s.getActive()));
		}
		return new ResponseEntity<Iterable<StudentAffairsOfficeDTO>>(offices, HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN", "STAFF"})
	@GetMapping("/params")
	public ResponseEntity<Page<StudentAffairsOfficeDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		// TODO Auto-generated method stub

		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<StudentAffairsOffice> officePage = service.findAll(pageable);

	    List<StudentAffairsOfficeDTO> officeDTOs = officePage.stream().map(o -> {
	        List<StudentServiceStaffDTO> staff = o.getStaff().stream().map(s -> 
	            new StudentServiceStaffDTO(
	                s.getId(),         
	                new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()),
	                s.getFirstName(), s.getLastName(), null, s.getActive()
	            )
	        ).collect(Collectors.toList());

	        return new StudentAffairsOfficeDTO(
	            o.getId(),       
	            staff,
	            new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
						new AddressDTO(o.getFaculty().getAddress().getId(), 
								o.getFaculty().getAddress().getStreet(),
								o.getFaculty().getAddress().getHouseNumber(), null,
								o.getFaculty().getAddress().getActive()),
						new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
								null, o.getFaculty().getHeadmaster().getFirstName(),
								o.getFaculty().getHeadmaster().getLastName(), o.getFaculty().getHeadmaster().getUmcn(),
								o.getFaculty().getHeadmaster().getBiography(), null, null, null, null, null,
								o.getFaculty().getHeadmaster().getActive()),
						new UniversityDTO(o.getFaculty().getUniversity().getId(),
								o.getFaculty().getUniversity().getName(),
								o.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(),
										o.getFaculty().getUniversity().getAddress().getStreet(),
										o.getFaculty().getUniversity().getAddress().getHouseNumber(),
										new PlaceDTO(o.getFaculty().getUniversity().getAddress().getPlace().getId(),
												o.getFaculty().getUniversity().getAddress().getPlace().getName(),
												new CountryDTO(o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
														o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(), null, o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()),
												o.getFaculty().getUniversity().getAddress().getPlace().getActive()),
										o.getFaculty().getUniversity().getAddress().getActive()),
								null, o.getFaculty().getUniversity().getContactDetails(),
								o.getFaculty().getUniversity().getDescription(),
								null, o.getFaculty().getUniversity().getActive()), 
						o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
						null, null, null, null, o.getFaculty().getActive()),
	            o.getActive()
	        );
	    }).collect(Collectors.toList());

	    Page<StudentAffairsOfficeDTO> resultPage = new PageImpl<>(officeDTOs, pageable, officePage.getTotalElements());

	    return new ResponseEntity<Page<StudentAffairsOfficeDTO>>(resultPage, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<Iterable<StudentAffairsOfficeDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<StudentAffairsOfficeDTO> offices = new ArrayList<StudentAffairsOfficeDTO>();
		ArrayList<StudentServiceStaffDTO> staff = new ArrayList<StudentServiceStaffDTO>();
		
		for(StudentAffairsOffice o : service.findAllActive()) {
			staff = (ArrayList<StudentServiceStaffDTO>) o.getStaff()
					.stream()
					.map(n -> new StudentServiceStaffDTO(n.getId(), 
							new RegisteredUserDTO(n.getRegisteredUser().getUsername(), null, n.getRegisteredUser().getEmail()),
							n.getFirstName(),
							n.getLastName(), null, n.getActive()))
					.collect(Collectors.toList());
			offices.add(new StudentAffairsOfficeDTO(
		            o.getId(),       
		            staff,
		            new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
							new AddressDTO(o.getFaculty().getAddress().getId(), 
									o.getFaculty().getAddress().getStreet(),
									o.getFaculty().getAddress().getHouseNumber(), null,
									o.getFaculty().getAddress().getActive()),
							new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
									null, o.getFaculty().getHeadmaster().getFirstName(),
									o.getFaculty().getHeadmaster().getLastName(), o.getFaculty().getHeadmaster().getUmcn(),
									o.getFaculty().getHeadmaster().getBiography(), null, null, null, null, null,
									o.getFaculty().getHeadmaster().getActive()),
							new UniversityDTO(o.getFaculty().getUniversity().getId(),
									o.getFaculty().getUniversity().getName(),
									o.getFaculty().getUniversity().getDateEstablished(),
									new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(),
											o.getFaculty().getUniversity().getAddress().getStreet(),
											o.getFaculty().getUniversity().getAddress().getHouseNumber(),
											new PlaceDTO(o.getFaculty().getUniversity().getAddress().getPlace().getId(),
													o.getFaculty().getUniversity().getAddress().getPlace().getName(),
													new CountryDTO(o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
															o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(), null, o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()),
													o.getFaculty().getUniversity().getAddress().getPlace().getActive()),
											o.getFaculty().getUniversity().getAddress().getActive()),
									null, o.getFaculty().getUniversity().getContactDetails(),
									o.getFaculty().getUniversity().getDescription(),
									null, o.getFaculty().getUniversity().getActive()), 
							o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
							null, null, null, null, o.getFaculty().getActive()),
		            o.getActive()
		        ));
		}
		
		return new ResponseEntity<Iterable<StudentAffairsOfficeDTO>>(offices, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<StudentAffairsOfficeDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudentAffairsOffice o = service.findById(id).orElse(null);
		
		ArrayList<StudentServiceStaffDTO> staff = new ArrayList<StudentServiceStaffDTO>();
		
		if(o == null) {
			return new ResponseEntity<StudentAffairsOfficeDTO>(HttpStatus.NOT_FOUND);
		}
		
		staff = (ArrayList<StudentServiceStaffDTO>) o.getStaff()
				.stream()
				.map(n -> new StudentServiceStaffDTO(n.getId(), 
						new RegisteredUserDTO(n.getRegisteredUser().getUsername(), null, n.getRegisteredUser().getEmail()),
						n.getFirstName(),
						n.getLastName(), null, n.getActive()))
				.collect(Collectors.toList());
		
		return new ResponseEntity<StudentAffairsOfficeDTO>(new StudentAffairsOfficeDTO(
	            o.getId(),       
	            staff,
	            new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
						new AddressDTO(o.getFaculty().getAddress().getId(), 
								o.getFaculty().getAddress().getStreet(),
								o.getFaculty().getAddress().getHouseNumber(), null,
								o.getFaculty().getAddress().getActive()),
						new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
								null, o.getFaculty().getHeadmaster().getFirstName(),
								o.getFaculty().getHeadmaster().getLastName(), o.getFaculty().getHeadmaster().getUmcn(),
								o.getFaculty().getHeadmaster().getBiography(), null, null, null, null, null,
								o.getFaculty().getHeadmaster().getActive()),
						new UniversityDTO(o.getFaculty().getUniversity().getId(),
								o.getFaculty().getUniversity().getName(),
								o.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(),
										o.getFaculty().getUniversity().getAddress().getStreet(),
										o.getFaculty().getUniversity().getAddress().getHouseNumber(),
										new PlaceDTO(o.getFaculty().getUniversity().getAddress().getPlace().getId(),
												o.getFaculty().getUniversity().getAddress().getPlace().getName(),
												new CountryDTO(o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
														o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(), null, o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()),
												o.getFaculty().getUniversity().getAddress().getPlace().getActive()),
										o.getFaculty().getUniversity().getAddress().getActive()),
								null, o.getFaculty().getUniversity().getContactDetails(),
								o.getFaculty().getUniversity().getDescription(),
								null, o.getFaculty().getUniversity().getActive()), 
						o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
						null, null, null, null, o.getFaculty().getActive()),
	            o.getActive()
	        ), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN"})
	public ResponseEntity<StudentAffairsOfficeDTO> create(@RequestBody StudentAffairsOfficeDTO t) {
		// TODO Auto-generated method stub
		StudentAffairsOffice o = service.create(new StudentAffairsOffice(null,new ArrayList<StudentServiceStaff>(),
				facultyService.findById(t.getFaculty().getId()).get(), true));
		
		if(o == null) {
			return new ResponseEntity<StudentAffairsOfficeDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<StudentAffairsOfficeDTO>(new StudentAffairsOfficeDTO(
	            o.getId(),       
	            null,
	            new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
						new AddressDTO(o.getFaculty().getAddress().getId(), 
								o.getFaculty().getAddress().getStreet(),
								o.getFaculty().getAddress().getHouseNumber(), null,
								o.getFaculty().getAddress().getActive()),
						new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
								null, o.getFaculty().getHeadmaster().getFirstName(),
								o.getFaculty().getHeadmaster().getLastName(), o.getFaculty().getHeadmaster().getUmcn(),
								o.getFaculty().getHeadmaster().getBiography(), null, null, null, null, null,
								o.getFaculty().getHeadmaster().getActive()),
						new UniversityDTO(o.getFaculty().getUniversity().getId(),
								o.getFaculty().getUniversity().getName(),
								o.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(),
										o.getFaculty().getUniversity().getAddress().getStreet(),
										o.getFaculty().getUniversity().getAddress().getHouseNumber(),
										new PlaceDTO(o.getFaculty().getUniversity().getAddress().getPlace().getId(),
												o.getFaculty().getUniversity().getAddress().getPlace().getName(),
												new CountryDTO(o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
														o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(), null, o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()),
												o.getFaculty().getUniversity().getAddress().getPlace().getActive()),
										o.getFaculty().getUniversity().getAddress().getActive()),
								null, o.getFaculty().getUniversity().getContactDetails(),
								o.getFaculty().getUniversity().getDescription(),
								null, o.getFaculty().getUniversity().getActive()), 
						o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
						null, null, null, null, o.getFaculty().getActive()),
	            o.getActive()
	        ), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<StudentAffairsOfficeDTO> update(@RequestBody StudentAffairsOfficeDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudentAffairsOffice o = service.findById(id).orElse(null);
		
		if(o == null) {
			return new ResponseEntity<StudentAffairsOfficeDTO>(HttpStatus.NOT_FOUND);
		}
		
		List<StudentServiceStaff> staff = new ArrayList<StudentServiceStaff>();
		
		staff = (List<StudentServiceStaff>) o.getStaff().stream()
				.map(s -> new StudentServiceStaff(s.getId(), 
						new RegisteredUser(s.getRegisteredUser().getId(),
								s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail(), null, null, null,
								s.getRegisteredUser().getActive()),
						s.getFirstName(), s.getLastName(), null, s.getActive())).collect(Collectors.toList());
		
		
		o.setStaff(staff);
		o.setFaculty(facultyService.findById(t.getFaculty().getId()).get());
		o.setActive(t.getActive());
		
		o = service.update(o);
		
		if(o == null) {
			return new ResponseEntity<StudentAffairsOfficeDTO>(HttpStatus.OK);
		}
		
		
		return new ResponseEntity<StudentAffairsOfficeDTO>(new StudentAffairsOfficeDTO(
	            o.getId(),       
	            null,
	            new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
						new AddressDTO(o.getFaculty().getAddress().getId(), 
								o.getFaculty().getAddress().getStreet(),
								o.getFaculty().getAddress().getHouseNumber(), null,
								o.getFaculty().getAddress().getActive()),
						new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
								null, o.getFaculty().getHeadmaster().getFirstName(),
								o.getFaculty().getHeadmaster().getLastName(), o.getFaculty().getHeadmaster().getUmcn(),
								o.getFaculty().getHeadmaster().getBiography(), null, null, null, null, null,
								o.getFaculty().getHeadmaster().getActive()),
						new UniversityDTO(o.getFaculty().getUniversity().getId(),
								o.getFaculty().getUniversity().getName(),
								o.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(),
										o.getFaculty().getUniversity().getAddress().getStreet(),
										o.getFaculty().getUniversity().getAddress().getHouseNumber(),
										new PlaceDTO(o.getFaculty().getUniversity().getAddress().getPlace().getId(),
												o.getFaculty().getUniversity().getAddress().getPlace().getName(),
												new CountryDTO(o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
														o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(), null, o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()),
												o.getFaculty().getUniversity().getAddress().getPlace().getActive()),
										o.getFaculty().getUniversity().getAddress().getActive()),
								null, o.getFaculty().getUniversity().getContactDetails(),
								o.getFaculty().getUniversity().getDescription(),
								null, o.getFaculty().getUniversity().getActive()), 
						o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
						null, null, null, null, o.getFaculty().getActive()),
	            o.getActive()
	        ), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StudentAffairsOfficeDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<StudentAffairsOfficeDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudentAffairsOffice o = service.findById(id).orElse(null);
		
		ArrayList<StudentServiceStaffDTO> staff = new ArrayList<StudentServiceStaffDTO>();
		
		if(o == null) {
			return new ResponseEntity<StudentAffairsOfficeDTO>(HttpStatus.NOT_FOUND);
		}
		
		staff = (ArrayList<StudentServiceStaffDTO>) o.getStaff()
				.stream()
				.map(n -> new StudentServiceStaffDTO(n.getId(), 
						new RegisteredUserDTO(n.getRegisteredUser().getUsername(), null, n.getRegisteredUser().getEmail()),
						n.getFirstName(),
						n.getLastName(), null, n.getActive()))
				.collect(Collectors.toList());
		
		service.softDelete(id);
		
		return new ResponseEntity<StudentAffairsOfficeDTO>(new StudentAffairsOfficeDTO(
	            o.getId(),       
	            staff,
	            new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
						new AddressDTO(o.getFaculty().getAddress().getId(), 
								o.getFaculty().getAddress().getStreet(),
								o.getFaculty().getAddress().getHouseNumber(), null,
								o.getFaculty().getAddress().getActive()),
						new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
								null, o.getFaculty().getHeadmaster().getFirstName(),
								o.getFaculty().getHeadmaster().getLastName(), o.getFaculty().getHeadmaster().getUmcn(),
								o.getFaculty().getHeadmaster().getBiography(), null, null, null, null, null,
								o.getFaculty().getHeadmaster().getActive()),
						new UniversityDTO(o.getFaculty().getUniversity().getId(),
								o.getFaculty().getUniversity().getName(),
								o.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(),
										o.getFaculty().getUniversity().getAddress().getStreet(),
										o.getFaculty().getUniversity().getAddress().getHouseNumber(),
										new PlaceDTO(o.getFaculty().getUniversity().getAddress().getPlace().getId(),
												o.getFaculty().getUniversity().getAddress().getPlace().getName(),
												new CountryDTO(o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
														o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(), null, o.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()),
												o.getFaculty().getUniversity().getAddress().getPlace().getActive()),
										o.getFaculty().getUniversity().getAddress().getActive()),
								null, o.getFaculty().getUniversity().getContactDetails(),
								o.getFaculty().getUniversity().getDescription(),
								null, o.getFaculty().getUniversity().getActive()), 
						o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
						null, null, null, null, o.getFaculty().getActive()),
	            o.getActive()
	        ), HttpStatus.OK);
		}
}
