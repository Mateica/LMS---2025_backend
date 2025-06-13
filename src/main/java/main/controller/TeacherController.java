package main.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.BinaryOperator;
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

import main.dto.EvaluationGradeDTO;
import main.dto.FacultyDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentAffairsOfficeDTO;
import main.dto.StudentServiceStaffDTO;
import main.dto.SubjectDTO;
import main.dto.SubjectRealizationDTO;
import main.dto.TeacherDTO;
import main.dto.TeacherOnRealizationDTO;
import main.dto.TeachingTypeDTO;
import main.dto.TitleDTO;
import main.model.Address;
import main.model.Country;
import main.model.Department;
import main.model.EvaluationGrade;
import main.model.Faculty;
import main.model.File;
import main.model.Place;
import main.model.RegisteredUser;
import main.model.Role;
import main.model.ScientificField;
import main.model.Student;
import main.model.StudentAffairsOffice;
import main.model.StudentOnYear;
import main.model.StudentServiceStaff;
import main.model.StudyProgramme;
import main.model.SubjectAttendance;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.TeachingMaterial;
import main.model.Title;
import main.model.University;
import main.service.EvaluationGradeService;
import main.service.FacultyService;
import main.service.RegisteredUserService;
import main.service.StudentAffairsOfficeService;
import main.service.TeacherOnRealizationService;
import main.service.TeacherService;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController implements ControllerInterface<TeacherDTO> {
	@Autowired
	private TeacherService service;
	
	@Autowired
	private RegisteredUserService userService;
	
	@Autowired
	private TeacherOnRealizationService teacherOnRealizationService;
	
	@Autowired
	private EvaluationGradeService gradeService;
	
	@Autowired
	private StudentAffairsOfficeService officeService;
	
	@Autowired
	private FacultyService facultyService;
	
	@Override
	@GetMapping
	@Secured({"ADMIN", "STAFF", "TEACHER", "STUDENT"})
	public ResponseEntity<Iterable<TeacherDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<TeacherDTO> teachers = new ArrayList<TeacherDTO>();
		ArrayList<TeacherOnRealizationDTO> teachersOnRealization = new ArrayList<TeacherOnRealizationDTO>();
		
		for(Teacher s : service.findAll()) {
			teachersOnRealization = (ArrayList<TeacherOnRealizationDTO>) s.getTeachersOnRealization()
					.stream()
					.map(n -> new TeacherOnRealizationDTO(n.getId(), n.getNumberOfClasses(), null,
							 new SubjectRealizationDTO(n.getSubjectRealization().getId(), null, null, null, 
									 new SubjectDTO(n.getSubjectRealization().getSubject().getId(),
											 n.getSubjectRealization().getSubject().getName(),
											 n.getSubjectRealization().getSubject().getEcts(),
											 n.getSubjectRealization().getSubject().getActive()),
									 n.getSubjectRealization().getActive()),
							 new TeachingTypeDTO(n.getTeachingType().getId(), n.getTeachingType().getName(), n.getTeachingType().getActive()),
							 n.getActive()))
					.collect(Collectors.toList());
			teachers.add(new TeacherDTO(s.getId(), 
					new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()),
					s.getFirstName(), s.getLastName(),  s.getUmcn(), s.getBiography(),
					new ArrayList<TitleDTO>(), teachersOnRealization, null, null, null, s.getActive()));
		}
		return new ResponseEntity<Iterable<TeacherDTO>>(teachers, HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN", "STAFF", "TEACHER", "STUDENT"})
	@GetMapping("/params")
	public ResponseEntity<Page<TeacherDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		// TODO Auto-generated method stub
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Teacher> teacherPage = service.findAll(pageable);

	    List<TeacherDTO> teacherDTOs = teacherPage.stream().map(s ->
	    
	    new TeacherDTO(s.getId(), 
				new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()),
				s.getFirstName(), s.getLastName(), s.getUmcn(),
				s.getBiography(),
				null, null,null,
				null, null, s.getActive()))
	    		.collect(Collectors.toList());

	    Page<TeacherDTO> resultPage = new PageImpl<TeacherDTO>(teacherDTOs, pageable, teacherPage.getTotalElements());

	    return new ResponseEntity<Page<TeacherDTO>>(resultPage, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	@Secured({"ADMIN", "STAFF", "TEACHER", "STUDENT"})
	public ResponseEntity<Iterable<TeacherDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<TeacherDTO> teachers = new ArrayList<TeacherDTO>();
		ArrayList<TeacherOnRealizationDTO> teachersOnRealization = new ArrayList<TeacherOnRealizationDTO>();
		
		for(Teacher s : service.findAllActive()) {
				teachersOnRealization = (ArrayList<TeacherOnRealizationDTO>) s.getTeachersOnRealization()
						.stream()
						.map(n -> {
							SubjectRealization realization = n.getSubjectRealization();
							
							return new TeacherOnRealizationDTO(n.getId(), n.getNumberOfClasses(), null,
								 new SubjectRealizationDTO((realization == null) ? null : realization.getId(), null, null, null, 
										 new SubjectDTO((realization == null) ? null : realization.getSubject().getId(),
												 (realization == null) ? null : realization.getSubject().getName(),
												 (realization == null) ? null : realization.getSubject().getEcts(),
												 (realization == null) ? null : realization.getSubject().getActive()),
										 (realization == null) ? null : realization.getSubject().getActive()),
								 new TeachingTypeDTO(n.getTeachingType().getId(), n.getTeachingType().getName(), n.getTeachingType().getActive()),
								 n.getActive());
						})
						.collect(Collectors.toList());
				teachers.add(new TeacherDTO(s.getId(), 
						new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()),
						s.getFirstName(), s.getLastName(),  s.getUmcn(), s.getBiography(),
						new ArrayList<TitleDTO>(), teachersOnRealization, null, null, null, s.getActive()));
			}
			return new ResponseEntity<Iterable<TeacherDTO>>(teachers, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN", "STAFF", "TEACHER", "STUDENT"})
	public ResponseEntity<TeacherDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Teacher s = service.findById(id).orElse(null);
		
		ArrayList<TeacherOnRealizationDTO> teachersOnRealization = new ArrayList<TeacherOnRealizationDTO>();
		
		if(s == null) {
			return new ResponseEntity<TeacherDTO>(HttpStatus.NOT_FOUND);
		}
		
		teachersOnRealization = (ArrayList<TeacherOnRealizationDTO>) s.getTeachersOnRealization()
				.stream()
				.map(n -> new TeacherOnRealizationDTO(n.getId(), n.getNumberOfClasses(), null,
						 new SubjectRealizationDTO(n.getSubjectRealization().getId(), null, null, null, 
								 new SubjectDTO(n.getSubjectRealization().getSubject().getId(),
										 n.getSubjectRealization().getSubject().getName(),
										 n.getSubjectRealization().getSubject().getEcts(),
										 n.getSubjectRealization().getSubject().getActive()),
								 n.getSubjectRealization().getActive()),
						 new TeachingTypeDTO(n.getTeachingType().getId(), n.getTeachingType().getName(), n.getTeachingType().getActive()),
						 n.getActive()))
				.collect(Collectors.toList());
		
		return new ResponseEntity<TeacherDTO>(new TeacherDTO(s.getId(), 
				new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()),
				s.getFirstName(), s.getLastName(),  s.getUmcn(), s.getBiography(),
				new ArrayList<TitleDTO>(), teachersOnRealization, null, null, null, s.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN"})
	public ResponseEntity<TeacherDTO> create(@RequestBody TeacherDTO t) {
		// TODO Auto-generated method stub
		Teacher s = service.create(new Teacher(null,
				userService.findById(t.getId()).get(),
				t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(),
				new ArrayList<Title>(), new ArrayList<TeacherOnRealization>(), new ArrayList<EvaluationGrade>(),
				new TeachingMaterial(t.getTeachingMaterial().getId(), t.getTeachingMaterial().getName(),
						new ArrayList<Teacher>(),
						t.getTeachingMaterial().getYearOfPublication(), 
						new File(t.getTeachingMaterial().getFile().getId(),
								t.getTeachingMaterial().getFile().getName(),
								t.getTeachingMaterial().getFile().getUrl(),
								t.getTeachingMaterial().getFile().getDescription(), null, null, null,
								new Student(t.getTeachingMaterial().getFile().getStudent().getId(),
										new RegisteredUser(t.getTeachingMaterial().getFile().getStudent().getUser().getId(),
												t.getTeachingMaterial().getFile().getStudent().getUser().getUsername(),
												t.getTeachingMaterial().getFile().getStudent().getUser().getPassword(),
												t.getTeachingMaterial().getFile().getStudent().getUser().getEmail(),
												null,null,
												null, 
												t.getTeachingMaterial().getFile().getStudent().getActive()),
										t.getTeachingMaterial().getFile().getStudent().getFirstName(),
										t.getTeachingMaterial().getFile().getStudent().getLastName(),
										t.getTeachingMaterial().getFile().getStudent().getUmcn(),
										new Address(t.getTeachingMaterial().getFile().getStudent().getAddress().getId(),
												t.getTeachingMaterial().getFile().getStudent().getAddress().getStreet(),
												t.getTeachingMaterial().getFile().getStudent().getAddress().getHouseNumber(),
												new Place(t.getTeachingMaterial().getFile().getStudent().getAddress().getPlace().getId(),
														t.getTeachingMaterial().getFile().getStudent().getAddress().getPlace().getName(),
														new Country(t.getTeachingMaterial().getFile().getStudent().getAddress().getPlace().getCountry().getId(),
																t.getTeachingMaterial().getFile().getStudent().getAddress().getPlace().getCountry().getName(),
																new ArrayList<Place>(),
																t.getTeachingMaterial().getFile().getStudent().getAddress().getPlace().getCountry().getActive()), 
														t.getTeachingMaterial().getFile().getStudent().getAddress().getPlace().getActive()),
												t.getTeachingMaterial().getFile().getStudent().getAddress().getActive()),
										new HashSet<StudentOnYear>(),
										new ArrayList<SubjectAttendance>(),
										new Faculty(t.getTeachingMaterial().getFile().getStudent().getFaculty().getId(),
												t.getTeachingMaterial().getFile().getStudent().getFaculty().getName(),
												new Address(t.getTeachingMaterial().getFile().getStudent().getFaculty().getAddress().getId(),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getAddress().getStreet(),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getAddress().getHouseNumber(),
														new Place(t.getTeachingMaterial().getFile().getStudent().getFaculty().getAddress().getPlace().getId(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getAddress().getPlace().getName(),
																new Country(t.getTeachingMaterial().getFile().getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
																		new ArrayList<Place>(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getAddress().getPlace().getActive()),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getAddress().getActive()),
												new Teacher(t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getId(),
														new RegisteredUser(t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getUser().getId(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getUser().getUsername(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getUser().getPassword(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getUser().getEmail(),
																null, null, new HashSet<Role>(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getUser().getActive()),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getFirstName(),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getLastName(),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getUmcn(),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getBiography(),
														new ArrayList<Title>(),
														new ArrayList<TeacherOnRealization>(),
														new ArrayList<EvaluationGrade>(),
														new TeachingMaterial(t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getId(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getName(),
																new ArrayList<Teacher>(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getYearOfPublication(),
																new File(t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getId(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getName(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getName(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getDescription(),
																		null, null, null,
																		new Student(t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getStudent().getId(),
																				new RegisteredUser(t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getStudent().getUser().getId(),
																						t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getStudent().getUser().getUsername(),
																						t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getStudent().getUser().getPassword(),
																						t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getStudent().getUser().getEmail(),
																						null, null, new HashSet<Role>(),
																						t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getStudent().getUser().getActive()),
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getStudent().getFirstName(),
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getStudent().getLastName(),
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getStudent().getUmcn(),
																				null,
																				new HashSet<StudentOnYear>(), new ArrayList<SubjectAttendance>(), null,
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getStudent().getActive()), 
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getDocument(), 
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getFile().getActive()), 
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getTeachingMaterial().getActive()),
														new Department(t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getDepartment().getId(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getDepartment().getName(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getDepartment().getDescription(),
						null, null, null, null, 
						t.getDepartment().getActive()),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getActive()),
												new University(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getId(),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getName(),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getDateEstablished(),
														new Address(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getAddress().getId(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getAddress().getStreet(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getAddress().getHouseNumber(),
																new Place(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getAddress().getPlace().getId(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getAddress().getPlace().getName(),
																		new Country(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
																				new ArrayList<Place>(),
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getAddress().getPlace().getActive()),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getAddress().getActive()),
														new Teacher(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getId(),
																new RegisteredUser(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getUser().getId(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getUser().getUsername(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getUser().getPassword(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getUser().getEmail(),
																		null, null, new HashSet<Role>(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getUser().getActive()),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getFirstName(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getLastName(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getUmcn(),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getBiography(),
																new ArrayList<Title>(),
																new ArrayList<TeacherOnRealization>(),
																new ArrayList<EvaluationGrade>(),
																new TeachingMaterial(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getId(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getName(),
																		new ArrayList<Teacher>(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getYearOfPublication(),
																		new File(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getId(),
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getName(),
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getName(),
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getDescription(),
																				null, null, null,
																				new Student(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getStudent().getId(),
																						new RegisteredUser(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getStudent().getUser().getId(),
																								t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getStudent().getUser().getUsername(),
																								t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getStudent().getUser().getPassword(),
																								t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getStudent().getUser().getEmail(),
																								null, null, new HashSet<Role>(),
																								t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getStudent().getUser().getActive()),
																						t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getStudent().getFirstName(),
																						t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getStudent().getLastName(),
																						t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getStudent().getUmcn(),
																						null,
																						new HashSet<StudentOnYear>(), new ArrayList<SubjectAttendance>(), null,
																						t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getStudent().getActive()), 
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getDocument(), 
																				t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getFile().getActive()), 
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getTeachingMaterial().getActive()),
																new Department(t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getDepartment().getId(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getDepartment().getName(),
																		t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getDepartment().getDescription(),
								null, null, null, null, 
								t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getRector().getDepartment().getActive()),
																t.getTeachingMaterial().getFile().getStudent().getFaculty().getHeadmaster().getActive()), 
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getContactDetails(),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getDescription(),
														new ArrayList<Faculty>(),
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getUniversity().getActive()),
												t.getTeachingMaterial().getFile().getStudent().getFaculty().getContactDetails(),
												t.getTeachingMaterial().getFile().getStudent().getFaculty().getDescription(),
												new HashSet<Department>(),
												new ArrayList<StudyProgramme>(),
												new ArrayList<Student>(),
												new StudentAffairsOffice(t.getTeachingMaterial().getFile().getStudent().getFaculty().getStudentAffairsOffice().getId(),
														new ArrayList<StudentServiceStaff>(), null, 
														t.getTeachingMaterial().getFile().getStudent().getFaculty().getStudentAffairsOffice().getActive()),
												t.getTeachingMaterial().getFile().getStudent().getFaculty().getActive()), 
										t.getTeachingMaterial().getFile().getStudent().getActive()), 
								t.getTeachingMaterial().getFile().getDocument(),
								t.getTeachingMaterial().getFile().getActive()),
						t.getActive()),
				new Department(t.getDepartment().getId(), t.getDepartment().getName(), t.getDepartment().getDescription(),
						null, null, null, null, 
						t.getDepartment().getActive()), true));
		
		if(s == null) {
			return new ResponseEntity<TeacherDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TeacherDTO>(new TeacherDTO(s.getId(), 
				new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()),
				s.getFirstName(), s.getLastName(),  s.getUmcn(), s.getBiography(),
				new ArrayList<TitleDTO>(), new ArrayList<TeacherOnRealizationDTO>(), null, null, null, s.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<TeacherDTO> update(@RequestBody TeacherDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Teacher s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<TeacherDTO>(HttpStatus.NOT_FOUND);
		}
		
		List<EvaluationGrade> grades = new ArrayList<EvaluationGrade>();
		List<Title> titles = new ArrayList<Title>();
		
		grades = (List<EvaluationGrade>) s.getEvaluationGrades().stream().map(g -> new EvaluationGrade(g.getId(),
				null, null, g.getDateTimeEvaluated(), g.getMark(), g.getActive())).collect(Collectors.toList());
		
		titles = s.getTitles().stream().map(title -> 
		new Title(title.getId(), title.getDateElected(), title.getDateAbolished(),
				new ScientificField(title.getScientificField().getId(),
						title.getScientificField().getName(), new ArrayList<Title>(),
						title.getScientificField().getActive()), null,
				title.getActive()))
				.collect(Collectors.toList());
		
		s.setUser(userService.findById(t.getUser().getId()).get());
		s.setFirstName(t.getFirstName());
		s.setLastName(t.getLastName());
		s.setUmcn(t.getUmcn());
		s.setBiography(t.getBiography());
		s.setTitles(titles);
		s.setTeachersOnRealization(teacherOnRealizationService.findByTeacherId(id));
		s.setEvaluationGrades(grades);
		s.setTeachingMaterial(new TeachingMaterial(s.getTeachingMaterial().getId(), 
				s.getTeachingMaterial().getName(),
				new ArrayList<Teacher>(),
				s.getTeachingMaterial().getYearOfPublication(),
				new File(s.getTeachingMaterial().getFile().getId(),
						s.getTeachingMaterial().getFile().getName(),
						s.getTeachingMaterial().getFile().getUrl(),
						s.getTeachingMaterial().getFile().getDescription(), null, null, null, null,
						s.getTeachingMaterial().getFile().getDocument(), s.getTeachingMaterial().getFile().getActive()),
				s.getTeachingMaterial().getActive()));
		s.setDepartment(new Department(s.getDepartment().getId(),
				s.getDepartment().getName(),
				s.getDepartment().getDescription(), 
				s.getDepartment().getFaculty(),
				new HashSet<Teacher>(), 
				service.findByDepartmentId(s.getDepartment().getId()),
				new HashSet<StudyProgramme>(),
				s.getDepartment().getActive()));
		s.setActive(t.getActive());
		
		s = service.update(s);
		
		if(s == null) {
			return new ResponseEntity<TeacherDTO>(HttpStatus.OK);
		}
		
		
		return new ResponseEntity<TeacherDTO>(new TeacherDTO(s.getId(), 
				new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()),
				s.getFirstName(), s.getLastName(),  s.getUmcn(), s.getBiography(),
				new ArrayList<TitleDTO>(), new ArrayList<TeacherOnRealizationDTO>(), null, null, null, s.getActive()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TeacherDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PatchMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<TeacherDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Teacher s = service.findById(id).orElse(null);
		
		ArrayList<TeacherOnRealizationDTO> teachersOnRealization = new ArrayList<TeacherOnRealizationDTO>();
		
		if(s == null) {
			return new ResponseEntity<TeacherDTO>(HttpStatus.NOT_FOUND);
		}
		
		teachersOnRealization = (ArrayList<TeacherOnRealizationDTO>) s.getTeachersOnRealization()
				.stream()
				.map(n -> new TeacherOnRealizationDTO(n.getId(), n.getNumberOfClasses(), null,
						 new SubjectRealizationDTO(n.getSubjectRealization().getId(), null, null, null, 
								 new SubjectDTO(n.getSubjectRealization().getSubject().getId(),
										 n.getSubjectRealization().getSubject().getName(),
										 n.getSubjectRealization().getSubject().getEcts(),
										 n.getSubjectRealization().getSubject().getActive()),
								 n.getSubjectRealization().getActive()),
						 new TeachingTypeDTO(n.getTeachingType().getId(), n.getTeachingType().getName(), n.getTeachingType().getActive()),
						 n.getActive()))
				.collect(Collectors.toList());
		
		service.softDelete(id);
		
		return new ResponseEntity<TeacherDTO>(new TeacherDTO(s.getId(), 
				new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()),
				s.getFirstName(), s.getLastName(),  s.getUmcn(), s.getBiography(),
				new ArrayList<TitleDTO>(), teachersOnRealization, null, null, null, s.getActive()), HttpStatus.OK);
	}
}
