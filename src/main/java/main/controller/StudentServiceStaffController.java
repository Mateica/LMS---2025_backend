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

import main.dto.FacultyDTO;
import main.dto.RegisteredUserDTO;
import main.dto.RoleDTO;
import main.dto.StudentAffairsOfficeDTO;
import main.dto.StudentServiceStaffDTO;
import main.model.Address;
import main.model.Faculty;
import main.model.RegisteredUser;
import main.model.Role;
import main.model.StudentAffairsOffice;
import main.model.StudentServiceStaff;
import main.model.Teacher;
import main.model.University;
import main.service.ExaminationService;
import main.service.RegisteredUserService;
import main.service.StudentAffairsOfficeService;
import main.service.StudentServiceStaffService;

@RestController
@RequestMapping("/api/studentServiceStaff")
public class StudentServiceStaffController implements ControllerInterface<StudentServiceStaffDTO> {
	@Autowired
	private StudentServiceStaffService service;
	
	@Autowired
	private StudentAffairsOfficeService officeService;
	
	@Autowired
	private RegisteredUserService userService;
	
	@Autowired
	private ExaminationService examService;

	@Override
	@GetMapping
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<Iterable<StudentServiceStaffDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<StudentServiceStaffDTO> staff = new ArrayList<StudentServiceStaffDTO>();
		
		for(StudentServiceStaff s : service.findAll()) {
			staff.add(new StudentServiceStaffDTO(s.getId(), 
					new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()),
					s.getFirstName(), s.getLastName(), 
					new StudentAffairsOfficeDTO(s.getStudentAffairsOffice().getId(), null, 
							new FacultyDTO(s.getStudentAffairsOffice().getFaculty().getId(),
									s.getStudentAffairsOffice().getFaculty().getName(),
									null, null, null, null, null, null, null, null, null,
									s.getStudentAffairsOffice().getFaculty().getActive()),
							s.getStudentAffairsOffice().getFaculty().getActive()), s.getActive()));
		}
		return new ResponseEntity<Iterable<StudentServiceStaffDTO>>(staff, HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN", "STAFF"})
	@GetMapping("/params")
	public ResponseEntity<Page<StudentServiceStaffDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		// TODO Auto-generated method stub
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<StudentServiceStaff> staffPage = service.findAll(pageable);

	    List<StudentServiceStaffDTO> staffDTOs = staffPage.stream().map(s ->
	    new StudentServiceStaffDTO(s.getId(), 
				new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()),
				s.getFirstName(), s.getLastName(), 
				new StudentAffairsOfficeDTO(s.getStudentAffairsOffice().getId(), null, 
						new FacultyDTO(s.getStudentAffairsOffice().getFaculty().getId(),
								s.getStudentAffairsOffice().getFaculty().getName(),
								null, null, null, null, null, null, null, null, null,
								s.getStudentAffairsOffice().getFaculty().getActive()),
						s.getStudentAffairsOffice().getFaculty().getActive()), s.getActive()))
	    		.collect(Collectors.toList());

	    Page<StudentServiceStaffDTO> resultPage = new PageImpl<StudentServiceStaffDTO>(staffDTOs, pageable, staffPage.getTotalElements());

	    return new ResponseEntity<Page<StudentServiceStaffDTO>>(resultPage, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<Iterable<StudentServiceStaffDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<StudentServiceStaffDTO> staff = new ArrayList<StudentServiceStaffDTO>();
		
		for(StudentServiceStaff s : service.findAllActive()) {
			staff.add(new StudentServiceStaffDTO(s.getId(), 
					new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()),
					s.getFirstName(), s.getLastName(), 
					new StudentAffairsOfficeDTO(s.getStudentAffairsOffice().getId(), null, 
							new FacultyDTO(s.getStudentAffairsOffice().getFaculty().getId(),
									s.getStudentAffairsOffice().getFaculty().getName(),
									null, null, null, null, null, null, null, null, null,
									s.getStudentAffairsOffice().getFaculty().getActive()),
							s.getStudentAffairsOffice().getFaculty().getActive()), s.getActive()));
		}
		return new ResponseEntity<Iterable<StudentServiceStaffDTO>>(staff, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<StudentServiceStaffDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudentServiceStaff s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<StudentServiceStaffDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<StudentServiceStaffDTO>(new StudentServiceStaffDTO(s.getId(), 
					new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()),
					s.getFirstName(), s.getLastName(), 
					new StudentAffairsOfficeDTO(s.getStudentAffairsOffice().getId(), null, 
							new FacultyDTO(s.getStudentAffairsOffice().getFaculty().getId(),
									s.getStudentAffairsOffice().getFaculty().getName(),
									null, null, null, null, null, null, null, null, null,
									s.getStudentAffairsOffice().getFaculty().getActive()),
							s.getStudentAffairsOffice().getFaculty().getActive()), s.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN"})
	public ResponseEntity<StudentServiceStaffDTO> create(@RequestBody StudentServiceStaffDTO t) {
		// TODO Auto-generated method stub
		StudentServiceStaff s = service.create(new StudentServiceStaff(null, 
				new RegisteredUser(t.getRegisteredUser().getId(), t.getRegisteredUser().getUsername(),
						t.getRegisteredUser().getPassword(),
						t.getRegisteredUser().getEmail(),
						null, null, null, t.getRegisteredUser().getActive()),
				t.getFirstName(), t.getLastName(),
				new StudentAffairsOffice(t.getStudentAffairsOffice().getId(), 
						new ArrayList<StudentServiceStaff>(),
						new Faculty(t.getStudentAffairsOffice().getFaculty().getId(),
								t.getStudentAffairsOffice().getFaculty().getName(),
								new Address(t.getStudentAffairsOffice().getFaculty().getAddress().getId(), 
										t.getStudentAffairsOffice().getFaculty().getAddress().getStreet(),
										t.getStudentAffairsOffice().getFaculty().getAddress().getHouseNumber(),
									null, t.getStudentAffairsOffice().getFaculty().getAddress().getActive()),
								new Teacher(t.getStudentAffairsOffice().getFaculty().getHeadmaster().getId(),
										new RegisteredUser(t.getStudentAffairsOffice().getFaculty().getHeadmaster().getUser().getId(),
												t.getStudentAffairsOffice().getFaculty().getHeadmaster().getUser().getUsername(), null, null, null, null, null, null),
										t.getStudentAffairsOffice().getFaculty().getHeadmaster().getFirstName(),
										t.getStudentAffairsOffice().getFaculty().getHeadmaster().getLastName(),
										t.getStudentAffairsOffice().getFaculty().getHeadmaster().getUmcn(),
										t.getStudentAffairsOffice().getFaculty().getHeadmaster().getBiography(),
										null, null, null, null, null,
										t.getStudentAffairsOffice().getFaculty().getHeadmaster().getActive()),
								new University(null, null, null, null, null, null, null, null, null),
								t.getStudentAffairsOffice().getFaculty().getContactDetails(),
								t.getStudentAffairsOffice().getFaculty().getDescription(),
								null, null, null, null, t.getStudentAffairsOffice().getFaculty().getActive()),
						t.getStudentAffairsOffice().getActive()), true));
		
		if(s == null) {
			return new ResponseEntity<StudentServiceStaffDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<StudentServiceStaffDTO>(new StudentServiceStaffDTO(s.getId(), 
					new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()),
					s.getFirstName(), s.getLastName(), 
					new StudentAffairsOfficeDTO(s.getStudentAffairsOffice().getId(), null, 
							new FacultyDTO(s.getStudentAffairsOffice().getFaculty().getId(),
									s.getStudentAffairsOffice().getFaculty().getName(),
									null, null, null, null, null, null, null, null, null,
									s.getStudentAffairsOffice().getFaculty().getActive()),
							s.getStudentAffairsOffice().getFaculty().getActive()), s.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<StudentServiceStaffDTO> update(@RequestBody StudentServiceStaffDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudentServiceStaff s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<StudentServiceStaffDTO>(HttpStatus.NOT_FOUND);
		}
		
		
		
		s.setRegisteredUser(userService.findById(t.getRegisteredUser().getId()).get());
		s.setFirstName(t.getFirstName());
		s.setLastName(t.getLastName());
		s.setStudentAffairsOffice(officeService.findById(t.getStudentAffairsOffice().getId()).get());
		s.setActive(t.getActive());
		
		s = service.update(s);
		
		if(s == null) {
			return new ResponseEntity<StudentServiceStaffDTO>(HttpStatus.OK);
		}
		
		
		return new ResponseEntity<StudentServiceStaffDTO>(new StudentServiceStaffDTO(s.getId(), 
					new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()),
					s.getFirstName(), s.getLastName(), 
					new StudentAffairsOfficeDTO(s.getStudentAffairsOffice().getId(), null, 
							new FacultyDTO(s.getStudentAffairsOffice().getFaculty().getId(),
									s.getStudentAffairsOffice().getFaculty().getName(),
									null, null, null, null, null, null, null, null, null,
									s.getStudentAffairsOffice().getFaculty().getActive()),
							s.getStudentAffairsOffice().getFaculty().getActive()), s.getActive()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StudentServiceStaffDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<StudentServiceStaffDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudentServiceStaff s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<StudentServiceStaffDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.softDelete(id);
		return new ResponseEntity<StudentServiceStaffDTO>(new StudentServiceStaffDTO(s.getId(), 
					new RegisteredUserDTO(s.getRegisteredUser().getUsername(), null, s.getRegisteredUser().getEmail()),
					s.getFirstName(), s.getLastName(), 
					new StudentAffairsOfficeDTO(s.getStudentAffairsOffice().getId(), null, 
							new FacultyDTO(s.getStudentAffairsOffice().getFaculty().getId(),
									s.getStudentAffairsOffice().getFaculty().getName(),
									null, null, null, null, null, null, null, null, null,
									s.getStudentAffairsOffice().getFaculty().getActive()),
							s.getStudentAffairsOffice().getFaculty().getActive()), s.getActive()), HttpStatus.OK);
	}
	
	
	
}
