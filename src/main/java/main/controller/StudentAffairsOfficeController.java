package main.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.PatchMapping;
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
import main.model.Account;
import main.model.Address;
import main.model.Department;
import main.model.EvaluationGrade;
import main.model.Faculty;
import main.model.File;
import main.model.ForumUser;
import main.model.RegisteredUser;
import main.model.Role;
import main.model.StudentAffairsOffice;
import main.model.StudentServiceStaff;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.TeachingMaterial;
import main.model.Title;
import main.model.University;
import main.service.EvaluationGradeService;
import main.service.FacultyService;
import main.service.RegisteredUserService;
import main.service.RoleService;
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
	private RoleService roleService;
	
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
							(n.getRegisteredUser() != null ? 
									new RegisteredUserDTO(n.getRegisteredUser().getUsername(), null, n.getRegisteredUser().getEmail()) : null),
							n.getFirstName(),
							n.getLastName(), null, n.getActive()))
					.collect(Collectors.toList());
			
			offices.add(new StudentAffairsOfficeDTO(s.getId(), staff, 
					(s.getFaculty() != null ? new FacultyDTO(s.getFaculty().getId(), s.getFaculty().getName(),
							(s.getFaculty().getAddress() != null ? new AddressDTO(s.getFaculty().getAddress().getId(), 
									s.getFaculty().getAddress().getStreet(),
									s.getFaculty().getAddress().getHouseNumber(), null,
									s.getFaculty().getAddress().getActive()) : null),
							(s.getFaculty().getHeadmaster() != null ? new TeacherDTO(s.getFaculty().getHeadmaster().getId(),
									null, s.getFaculty().getHeadmaster().getFirstName(),
										s.getFaculty().getHeadmaster().getLastName(),
										s.getFaculty().getHeadmaster().getUmcn(),
										s.getFaculty().getHeadmaster().getBiography(),
										null, null, null, null, null, 
										s.getFaculty().getHeadmaster().getActive()) : null),
							(s.getFaculty().getUniversity() != null ? 
									new UniversityDTO(s.getFaculty().getUniversity().getId(),
											s.getFaculty().getUniversity().getName(),
											s.getFaculty().getUniversity().getDateEstablished(),
											(s.getFaculty().getUniversity().getAddress() != null ? 
													new AddressDTO(s.getFaculty().getUniversity().getAddress().getId(), 
													s.getFaculty().getUniversity().getAddress().getStreet(),
													s.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
													s.getFaculty().getUniversity().getAddress().getActive()) : null),
											(s.getFaculty().getUniversity().getRector() != null ? 
													new TeacherDTO(s.getFaculty().getUniversity().getRector().getId(),
													null, s.getFaculty().getUniversity().getRector().getFirstName(),
													s.getFaculty().getUniversity().getRector().getLastName(),
													s.getFaculty().getUniversity().getRector().getUmcn(),
													s.getFaculty().getUniversity().getRector().getBiography(),
														null, null, null, null, null, 
														s.getFaculty().getUniversity().getRector().getActive()) : null),
											s.getFaculty().getUniversity().getContactDetails(),
											s.getFaculty().getUniversity().getDescription(), null,
											s.getFaculty().getUniversity().getActive()) : null), 
							s.getFaculty().getContactDetails(), s.getFaculty().getDescription(),
							null, null, null, null, s.getFaculty().getActive()) : null),
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
	                (s.getRegisteredUser() != null ?
	                		new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()) : null),
	                s.getFirstName(), s.getLastName(), null, s.getActive()
	            )
	        ).collect(Collectors.toList());

	        return new StudentAffairsOfficeDTO(o.getId(), staff, 
					(o.getFaculty() != null ? new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
							(o.getFaculty().getAddress() != null ? new AddressDTO(o.getFaculty().getAddress().getId(), 
									o.getFaculty().getAddress().getStreet(),
									o.getFaculty().getAddress().getHouseNumber(), null,
									o.getFaculty().getAddress().getActive()) : null),
							(o.getFaculty().getHeadmaster() != null ? new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
									null, o.getFaculty().getHeadmaster().getFirstName(),
										o.getFaculty().getHeadmaster().getLastName(),
										o.getFaculty().getHeadmaster().getUmcn(),
										o.getFaculty().getHeadmaster().getBiography(),
										null, null, null, null, null, 
										o.getFaculty().getHeadmaster().getActive()) : null),
							(o.getFaculty().getUniversity() != null ? 
									new UniversityDTO(o.getFaculty().getUniversity().getId(),
											o.getFaculty().getUniversity().getName(),
											o.getFaculty().getUniversity().getDateEstablished(),
											(o.getFaculty().getUniversity().getAddress() != null ? 
													new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(), 
													o.getFaculty().getUniversity().getAddress().getStreet(),
													o.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
													o.getFaculty().getUniversity().getAddress().getActive()) : null),
											(o.getFaculty().getUniversity().getRector() != null ? 
													new TeacherDTO(o.getFaculty().getUniversity().getRector().getId(),
													null, o.getFaculty().getUniversity().getRector().getFirstName(),
													o.getFaculty().getUniversity().getRector().getLastName(),
													o.getFaculty().getUniversity().getRector().getUmcn(),
													o.getFaculty().getUniversity().getRector().getBiography(),
														null, null, null, null, null, 
														o.getFaculty().getUniversity().getRector().getActive()) : null),
											o.getFaculty().getUniversity().getContactDetails(),
											o.getFaculty().getUniversity().getDescription(), null,
											o.getFaculty().getUniversity().getActive()) : null), 
							o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
							null, null, null, null, o.getFaculty().getActive()) : null),
					o.getActive());
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
							(n.getRegisteredUser() != null ?
									new RegisteredUserDTO(n.getRegisteredUser().getUsername(), null, n.getRegisteredUser().getEmail()) : null),
							n.getFirstName(),
							n.getLastName(), null, n.getActive()))
					.collect(Collectors.toList());
			
			offices.add(new StudentAffairsOfficeDTO(o.getId(), staff, 
					(o.getFaculty() != null ? new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
							(o.getFaculty().getAddress() != null ? new AddressDTO(o.getFaculty().getAddress().getId(), 
									o.getFaculty().getAddress().getStreet(),
									o.getFaculty().getAddress().getHouseNumber(), null,
									o.getFaculty().getAddress().getActive()) : null),
							(o.getFaculty().getHeadmaster() != null ? new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
									null, o.getFaculty().getHeadmaster().getFirstName(),
										o.getFaculty().getHeadmaster().getLastName(),
										o.getFaculty().getHeadmaster().getUmcn(),
										o.getFaculty().getHeadmaster().getBiography(),
										null, null, null, null, null, 
										o.getFaculty().getHeadmaster().getActive()) : null),
							(o.getFaculty().getUniversity() != null ? 
									new UniversityDTO(o.getFaculty().getUniversity().getId(),
											o.getFaculty().getUniversity().getName(),
											o.getFaculty().getUniversity().getDateEstablished(),
											(o.getFaculty().getUniversity().getAddress() != null ? 
													new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(), 
													o.getFaculty().getUniversity().getAddress().getStreet(),
													o.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
													o.getFaculty().getUniversity().getAddress().getActive()) : null),
											(o.getFaculty().getUniversity().getRector() != null ? 
													new TeacherDTO(o.getFaculty().getUniversity().getRector().getId(),
													null, o.getFaculty().getUniversity().getRector().getFirstName(),
													o.getFaculty().getUniversity().getRector().getLastName(),
													o.getFaculty().getUniversity().getRector().getUmcn(),
													o.getFaculty().getUniversity().getRector().getBiography(),
														null, null, null, null, null, 
														o.getFaculty().getUniversity().getRector().getActive()) : null),
											o.getFaculty().getUniversity().getContactDetails(),
											o.getFaculty().getUniversity().getDescription(), null,
											o.getFaculty().getUniversity().getActive()) : null), 
							o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
							null, null, null, null, o.getFaculty().getActive()) : null),
					o.getActive()));
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
		
		return new ResponseEntity<StudentAffairsOfficeDTO>(new StudentAffairsOfficeDTO(o.getId(), staff, 
				(o.getFaculty() != null ? new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
						(o.getFaculty().getAddress() != null ? new AddressDTO(o.getFaculty().getAddress().getId(), 
								o.getFaculty().getAddress().getStreet(),
								o.getFaculty().getAddress().getHouseNumber(), null,
								o.getFaculty().getAddress().getActive()) : null),
						(o.getFaculty().getHeadmaster() != null ? new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
								null, o.getFaculty().getHeadmaster().getFirstName(),
									o.getFaculty().getHeadmaster().getLastName(),
									o.getFaculty().getHeadmaster().getUmcn(),
									o.getFaculty().getHeadmaster().getBiography(),
									null, null, null, null, null, 
									o.getFaculty().getHeadmaster().getActive()) : null),
						(o.getFaculty().getUniversity() != null ? 
								new UniversityDTO(o.getFaculty().getUniversity().getId(),
										o.getFaculty().getUniversity().getName(),
										o.getFaculty().getUniversity().getDateEstablished(),
										(o.getFaculty().getUniversity().getAddress() != null ? 
												new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(), 
												o.getFaculty().getUniversity().getAddress().getStreet(),
												o.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
												o.getFaculty().getUniversity().getAddress().getActive()) : null),
										(o.getFaculty().getUniversity().getRector() != null ? 
												new TeacherDTO(o.getFaculty().getUniversity().getRector().getId(),
												null, o.getFaculty().getUniversity().getRector().getFirstName(),
												o.getFaculty().getUniversity().getRector().getLastName(),
												o.getFaculty().getUniversity().getRector().getUmcn(),
												o.getFaculty().getUniversity().getRector().getBiography(),
													null, null, null, null, null, 
													o.getFaculty().getUniversity().getRector().getActive()) : null),
										o.getFaculty().getUniversity().getContactDetails(),
										o.getFaculty().getUniversity().getDescription(), null,
										o.getFaculty().getUniversity().getActive()) : null), 
						o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
						null, null, null, null, o.getFaculty().getActive()) : null),
				o.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN"})
	public ResponseEntity<StudentAffairsOfficeDTO> create(@RequestBody StudentAffairsOfficeDTO t) {
		// TODO Auto-generated method stub
		HashSet<Role> staffRoles = null;
		HashSet<Role> headmasterRoles = null;
		HashSet<Role> rectorRoles = null;

		ArrayList<StudentServiceStaff> staff = (ArrayList<StudentServiceStaff>) t.getStaff().stream()
												.map(s -> {
													staffRoles = (HashSet<Role>) s.getRegisteredUser().getRoleNames()
															.stream()
															.map(r -> new Role(roleService.findByName(r).getId(),
																	roleService.findByName(r).getName(), 
																	roleService.findByName(r).getActive()))
															.collect(Collectors.toSet());
													
													return new StudentServiceStaff(s.getId(),
															(s.getRegisteredUser() != null ? 
																	new RegisteredUser(s.getRegisteredUser().getId(),
																			s.getRegisteredUser().getUsername(),
																			s.getRegisteredUser().getPassword(),
																			s.getRegisteredUser().getEmail(),
																			null, null, staffRoles,
																			s.getRegisteredUser().getActive()) : null),
															s.getFirstName(), s.getLastName(),
															null,
															s.getActive());
												})
												.collect(Collectors.toList());
		
		headmasterRoles = (HashSet<Role>) t.getFaculty().getHeadmaster().getUser().getRoleNames()
				.stream()
				.map(r -> new Role(roleService.findByName(r).getId(),
						roleService.findByName(r).getName(), 
						roleService.findByName(r).getActive()))
				.collect(Collectors.toSet());
		
		rectorRoles = (HashSet<Role>) t.getFaculty().getUniversity().getRector().getUser().getRoleNames()
				.stream()
				.map(r -> new Role(roleService.findByName(r).getId(),
						roleService.findByName(r).getName(), 
						roleService.findByName(r).getActive()))
				.collect(Collectors.toSet());
		
		StudentAffairsOffice o = service.create(new StudentAffairsOffice(null,
				staff, 
				new Faculty(t.getFaculty().getId(),
						t.getFaculty().getName(),
						(t.getFaculty().getAddress() != null ? new Address(t.getFaculty().getAddress().getId(),
								t.getFaculty().getAddress().getStreet(),
								t.getFaculty().getAddress().getHouseNumber(),
								null, 
								t.getFaculty().getAddress().getActive()) : null),
						(t.getFaculty().getHeadmaster() != null ? new Teacher(t.getFaculty().getHeadmaster().getId(),
								(t.getFaculty().getHeadmaster().getUser() != null ? 
										new RegisteredUser(t.getFaculty().getHeadmaster().getUser().getId(),
												t.getFaculty().getHeadmaster().getUser().getUsername(),
												t.getFaculty().getHeadmaster().getUser().getPassword(),
												t.getFaculty().getHeadmaster().getUser().getEmail(),
												new ArrayList<ForumUser>(), 
												new ArrayList<Account>(), headmasterRoles,
												t.getFaculty().getHeadmaster().getUser().getActive()) : null),
								t.getFaculty().getHeadmaster().getFirstName(),
								t.getFaculty().getHeadmaster().getLastName(),
								t.getFaculty().getHeadmaster().getUmcn(),
								t.getFaculty().getHeadmaster().getBiography(),
								null, null, null, null, null,
								t.getFaculty().getHeadmaster().getActive()) : null),
						(t.getFaculty().getUniversity() != null ? 
								new University(t.getFaculty().getUniversity().getId(),
										t.getFaculty().getUniversity().getName(),
										t.getFaculty().getUniversity().getDateEstablished(),
										(t.getFaculty().getUniversity().getAddress() != null ? 
												new Address(t.getFaculty().getUniversity().getAddress().getId(),
														t.getFaculty().getUniversity().getAddress().getStreet(),
														t.getFaculty().getUniversity().getAddress().getHouseNumber(),
														null, 
														t.getFaculty().getAddress().getActive()) : null),
										(t.getFaculty().getHeadmaster() != null ? 
												new Teacher(null, null, null, null, null, null, null, null, null, null, null, null) : null),
										t.getFaculty().getUniversity().getContactDetails(),
										t.getFaculty().getUniversity().getDescription(), null, 
										t.getFaculty().getUniversity().getActive()) : null),
						t.getFaculty().getContactDetails(),
						t.getFaculty().getDescription(), null, null, null, null, 
						t.getFaculty().getActive()),
				true));
		
		if(o == null) {
			return new ResponseEntity<StudentAffairsOfficeDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<StudentServiceStaffDTO> staffDTO = (ArrayList<StudentServiceStaffDTO>) o.getStaff().stream().map(s -> 
											new StudentServiceStaffDTO(s.getId(),
													(s.getRegisteredUser() != null ? 
															new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()) : null),
													s.getFirstName(), s.getLastName(), t, s.getActive()))
				.collect(Collectors.toList());
		
		return new ResponseEntity<StudentAffairsOfficeDTO>(new StudentAffairsOfficeDTO(o.getId(), staffDTO, 
				(o.getFaculty() != null ? new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
						(o.getFaculty().getAddress() != null ? new AddressDTO(o.getFaculty().getAddress().getId(), 
								o.getFaculty().getAddress().getStreet(),
								o.getFaculty().getAddress().getHouseNumber(), null,
								o.getFaculty().getAddress().getActive()) : null),
						(o.getFaculty().getHeadmaster() != null ? new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
								null, o.getFaculty().getHeadmaster().getFirstName(),
									o.getFaculty().getHeadmaster().getLastName(),
									o.getFaculty().getHeadmaster().getUmcn(),
									o.getFaculty().getHeadmaster().getBiography(),
									null, null, null, null, null, 
									o.getFaculty().getHeadmaster().getActive()) : null),
						(o.getFaculty().getUniversity() != null ? 
								new UniversityDTO(o.getFaculty().getUniversity().getId(),
										o.getFaculty().getUniversity().getName(),
										o.getFaculty().getUniversity().getDateEstablished(),
										(o.getFaculty().getUniversity().getAddress() != null ? 
												new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(), 
												o.getFaculty().getUniversity().getAddress().getStreet(),
												o.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
												o.getFaculty().getUniversity().getAddress().getActive()) : null),
										(o.getFaculty().getUniversity().getRector() != null ? 
												new TeacherDTO(o.getFaculty().getUniversity().getRector().getId(),
												null, o.getFaculty().getUniversity().getRector().getFirstName(),
												o.getFaculty().getUniversity().getRector().getLastName(),
												o.getFaculty().getUniversity().getRector().getUmcn(),
												o.getFaculty().getUniversity().getRector().getBiography(),
													null, null, null, null, null, 
													o.getFaculty().getUniversity().getRector().getActive()) : null),
										o.getFaculty().getUniversity().getContactDetails(),
										o.getFaculty().getUniversity().getDescription(), null,
										o.getFaculty().getUniversity().getActive()) : null), 
						o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
						null, null, null, null, o.getFaculty().getActive()) : null),
				o.getActive()), HttpStatus.CREATED);
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
		
		ArrayList<StudentServiceStaffDTO> staffDTO = (ArrayList<StudentServiceStaffDTO>) o.getStaff().stream().map(s -> 
		new StudentServiceStaffDTO(s.getId(),
				(s.getRegisteredUser() != null ? 
						new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()) : null),
				s.getFirstName(), s.getLastName(), t, s.getActive()))
					.collect(Collectors.toList());
		
		
		return new ResponseEntity<StudentAffairsOfficeDTO>(new StudentAffairsOfficeDTO(o.getId(), staffDTO, 
				(o.getFaculty() != null ? new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
						(o.getFaculty().getAddress() != null ? new AddressDTO(o.getFaculty().getAddress().getId(), 
								o.getFaculty().getAddress().getStreet(),
								o.getFaculty().getAddress().getHouseNumber(), null,
								o.getFaculty().getAddress().getActive()) : null),
						(o.getFaculty().getHeadmaster() != null ? new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
								null, o.getFaculty().getHeadmaster().getFirstName(),
									o.getFaculty().getHeadmaster().getLastName(),
									o.getFaculty().getHeadmaster().getUmcn(),
									o.getFaculty().getHeadmaster().getBiography(),
									null, null, null, null, null, 
									o.getFaculty().getHeadmaster().getActive()) : null),
						(o.getFaculty().getUniversity() != null ? 
								new UniversityDTO(o.getFaculty().getUniversity().getId(),
										o.getFaculty().getUniversity().getName(),
										o.getFaculty().getUniversity().getDateEstablished(),
										(o.getFaculty().getUniversity().getAddress() != null ? 
												new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(), 
												o.getFaculty().getUniversity().getAddress().getStreet(),
												o.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
												o.getFaculty().getUniversity().getAddress().getActive()) : null),
										(o.getFaculty().getUniversity().getRector() != null ? 
												new TeacherDTO(o.getFaculty().getUniversity().getRector().getId(),
												null, o.getFaculty().getUniversity().getRector().getFirstName(),
												o.getFaculty().getUniversity().getRector().getLastName(),
												o.getFaculty().getUniversity().getRector().getUmcn(),
												o.getFaculty().getUniversity().getRector().getBiography(),
													null, null, null, null, null, 
													o.getFaculty().getUniversity().getRector().getActive()) : null),
										o.getFaculty().getUniversity().getContactDetails(),
										o.getFaculty().getUniversity().getDescription(), null,
										o.getFaculty().getUniversity().getActive()) : null), 
						o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
						null, null, null, null, o.getFaculty().getActive()) : null),
				o.getActive()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StudentAffairsOfficeDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PatchMapping("/{id}")
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
		
		ArrayList<StudentServiceStaffDTO> staffDTO = (ArrayList<StudentServiceStaffDTO>) o.getStaff().stream().map(s -> 
		new StudentServiceStaffDTO(s.getId(),
				(s.getRegisteredUser() != null ? 
						new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()) : null),
				s.getFirstName(), s.getLastName(), t, s.getActive()))
					.collect(Collectors.toList());
		
		service.softDelete(id);
		
		return new ResponseEntity<StudentAffairsOfficeDTO>(new StudentAffairsOfficeDTO(o.getId(), staffDTO, 
				(o.getFaculty() != null ? new FacultyDTO(o.getFaculty().getId(), o.getFaculty().getName(),
						(o.getFaculty().getAddress() != null ? new AddressDTO(o.getFaculty().getAddress().getId(), 
								o.getFaculty().getAddress().getStreet(),
								o.getFaculty().getAddress().getHouseNumber(), null,
								o.getFaculty().getAddress().getActive()) : null),
						(o.getFaculty().getHeadmaster() != null ? new TeacherDTO(o.getFaculty().getHeadmaster().getId(),
								null, o.getFaculty().getHeadmaster().getFirstName(),
									o.getFaculty().getHeadmaster().getLastName(),
									o.getFaculty().getHeadmaster().getUmcn(),
									o.getFaculty().getHeadmaster().getBiography(),
									null, null, null, null, null, 
									o.getFaculty().getHeadmaster().getActive()) : null),
						(o.getFaculty().getUniversity() != null ? 
								new UniversityDTO(o.getFaculty().getUniversity().getId(),
										o.getFaculty().getUniversity().getName(),
										o.getFaculty().getUniversity().getDateEstablished(),
										(o.getFaculty().getUniversity().getAddress() != null ? 
												new AddressDTO(o.getFaculty().getUniversity().getAddress().getId(), 
												o.getFaculty().getUniversity().getAddress().getStreet(),
												o.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
												o.getFaculty().getUniversity().getAddress().getActive()) : null),
										(o.getFaculty().getUniversity().getRector() != null ? 
												new TeacherDTO(o.getFaculty().getUniversity().getRector().getId(),
												null, o.getFaculty().getUniversity().getRector().getFirstName(),
												o.getFaculty().getUniversity().getRector().getLastName(),
												o.getFaculty().getUniversity().getRector().getUmcn(),
												o.getFaculty().getUniversity().getRector().getBiography(),
													null, null, null, null, null, 
													o.getFaculty().getUniversity().getRector().getActive()) : null),
										o.getFaculty().getUniversity().getContactDetails(),
										o.getFaculty().getUniversity().getDescription(), null,
										o.getFaculty().getUniversity().getActive()) : null), 
						o.getFaculty().getContactDetails(), o.getFaculty().getDescription(),
						null, null, null, null, o.getFaculty().getActive()) : null),
				o.getActive()), HttpStatus.OK);
		}
}
