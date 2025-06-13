package main.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import main.dto.EvaluationDTO;
import main.dto.EvaluationInstrumentDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.ExaminationDTO;
import main.dto.FacultyDTO;
import main.dto.FileDTO;
import main.dto.NoteDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentAffairsOfficeDTO;
import main.dto.StudentDTO;
import main.dto.StudentOnYearDTO;
import main.dto.SubjectAttendanceDTO;
import main.dto.SubjectDTO;
import main.dto.YearOfStudyDTO;
import main.model.Address;
import main.model.Country;
import main.model.Evaluation;
import main.model.Examination;
import main.model.Faculty;
import main.model.Note;
import main.model.Place;
import main.model.RegisteredUser;
import main.model.Role;
import main.model.Student;
import main.model.StudentAffairsOffice;
import main.model.StudentOnYear;
import main.model.Subject;
import main.model.SubjectAttendance;
import main.model.YearOfStudy;
import main.service.ExaminationService;
import main.service.RoleService;
import main.service.StudentOnYearService;
import main.service.StudentService;
import main.service.YearOfStudyService;

@RestController
@RequestMapping("/api/studentsOnYear")
public class StudentOnYearController implements ControllerInterface<StudentOnYearDTO> {
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentOnYearService service;
	
	@Autowired
	private YearOfStudyService yearOfStudyService;
	
	@Autowired
	private ExaminationService examService;
	
	@Autowired
	private RoleService roleService;
	
	@Override
	@GetMapping
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Iterable<StudentOnYearDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<StudentOnYearDTO> studentsOnYear = new ArrayList<StudentOnYearDTO>(); 
		
		service.findAll().forEach(s->{
			List<ExaminationDTO> exams = (ArrayList<ExaminationDTO>) s.getExaminations()
						.stream()
						.map(e -> 
						new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
								new ArrayList<NoteDTO>(),
								new ArrayList<EvaluationDTO>(),
								(e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(),
										e.getStudentOnYear().getDateOfApplication(),
										(e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
												 (e.getStudentOnYear().getStudent().getUser() != null ? new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
														null, e.getStudentOnYear().getStudent().getUser().getEmail()) : null),
												e.getStudentOnYear().getStudent().getFirstName(),
												e.getStudentOnYear().getStudent().getLastName(),
												e.getStudentOnYear().getStudent().getUmcn(),
												null, null, null, null,
												e.getStudentOnYear().getStudent().getActive()) : null),
										e.getStudentOnYear().getIndexNumber(),
										(e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
												e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
												new ArrayList<SubjectDTO>(),
												e.getStudentOnYear().getYearOfStudy().getActive()) : null),
										null, null,
										e.getStudentOnYear().getActive()) : null),
								e.getActive()))
						.collect(Collectors.toList()); 
			
			StudentDTO student = new StudentDTO(s.getStudent().getId(), 
												(s.getStudent().getUser() != null ? new RegisteredUserDTO(s.getStudent().getUser().getUsername(), null, s.getStudent().getUser().getEmail()) : null),
												s.getStudent().getFirstName(),
												s.getStudent().getLastName(),
												null,
												(s.getStudent().getAddress() != null ? new AddressDTO(s.getStudent().getAddress().getId(),
																										s.getStudent().getAddress().getStreet(),
																										s.getStudent().getAddress().getHouseNumber(), null,
																										s.getStudent().getAddress().getActive()) : null),
												new HashSet<StudentOnYearDTO>(),
												new ArrayList<SubjectAttendanceDTO>(),
												null,
												s.getStudent().getActive());
			
			studentsOnYear.add(new StudentOnYearDTO(s.getId(), s.getDateOfApplication(),
								(s.getStudent() != null ? student : null), s.getIndexNumber(),
								(s.getYearOfStudy() != null ? new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, s.getYearOfStudy().getActive()) : null), exams, null, s.getActive()));
		});
		return new ResponseEntity<Iterable<StudentOnYearDTO>>(studentsOnYear, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Iterable<StudentOnYearDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<StudentOnYearDTO> studentsOnYear = new ArrayList<StudentOnYearDTO>(); 
		
		service.findAllActive().forEach(s->{
			List<ExaminationDTO> exams = (ArrayList<ExaminationDTO>) s.getExaminations()
						.stream()
						.map(e -> 
						new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
								new ArrayList<NoteDTO>(),
								new ArrayList<EvaluationDTO>(),
								(e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(),
										e.getStudentOnYear().getDateOfApplication(),
										(e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
												 (e.getStudentOnYear().getStudent().getUser() != null ? new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
														null, e.getStudentOnYear().getStudent().getUser().getEmail()) : null),
												e.getStudentOnYear().getStudent().getFirstName(),
												e.getStudentOnYear().getStudent().getLastName(),
												e.getStudentOnYear().getStudent().getUmcn(),
												null, null, null, null,
												e.getStudentOnYear().getStudent().getActive()) : null),
										e.getStudentOnYear().getIndexNumber(),
										(e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
												e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
												new ArrayList<SubjectDTO>(),
												e.getStudentOnYear().getYearOfStudy().getActive()) : null),
										null, null,
										e.getStudentOnYear().getActive()) : null),
								e.getActive()))
						.collect(Collectors.toList()); 
			
			StudentDTO student = new StudentDTO(s.getStudent().getId(), 
												(s.getStudent().getUser() != null ? new RegisteredUserDTO(s.getStudent().getUser().getUsername(), null, s.getStudent().getUser().getEmail()) : null),
												s.getStudent().getFirstName(),
												s.getStudent().getLastName(),
												null,
												(s.getStudent().getAddress() != null ? new AddressDTO(s.getStudent().getAddress().getId(),
																										s.getStudent().getAddress().getStreet(),
																										s.getStudent().getAddress().getHouseNumber(), null,
																										s.getStudent().getAddress().getActive()) : null),
												new HashSet<StudentOnYearDTO>(),
												new ArrayList<SubjectAttendanceDTO>(),
												null,
												s.getStudent().getActive());
			
			studentsOnYear.add(new StudentOnYearDTO(s.getId(), s.getDateOfApplication(),
								(s.getStudent() != null ? student : null), s.getIndexNumber(),
								(s.getYearOfStudy() != null ? new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, s.getYearOfStudy().getActive()) : null), exams, null, s.getActive()));
		});
		return new ResponseEntity<Iterable<StudentOnYearDTO>>(studentsOnYear, HttpStatus.OK);
	}

	@Override
	@GetMapping("/params")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Page<StudentOnYearDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		// TODO Auto-generated method stub
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<StudentOnYear> studentPage = service.findAll(pageable);
	    
	    List<StudentOnYearDTO> studentDTOs = studentPage.stream().map(s ->{
	    	List<ExaminationDTO> exams = (ArrayList<ExaminationDTO>) s.getExaminations()
					.stream()
					.map(e -> 
					new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
							new ArrayList<NoteDTO>(),
							new ArrayList<EvaluationDTO>(),
							(e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(),
									e.getStudentOnYear().getDateOfApplication(),
									(e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
											 (e.getStudentOnYear().getStudent().getUser() != null ? new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
													null, e.getStudentOnYear().getStudent().getUser().getEmail()) : null),
											e.getStudentOnYear().getStudent().getFirstName(),
											e.getStudentOnYear().getStudent().getLastName(),
											e.getStudentOnYear().getStudent().getUmcn(),
											null, null, null, null,
											e.getStudentOnYear().getStudent().getActive()) : null),
									e.getStudentOnYear().getIndexNumber(),
									(e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
											e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
											new ArrayList<SubjectDTO>(),
											e.getStudentOnYear().getYearOfStudy().getActive()) : null),
									null, null,
									e.getStudentOnYear().getActive()) : null),
							e.getActive()))
					.collect(Collectors.toList()); 
	    	
	    	StudentDTO student = new StudentDTO(s.getStudent().getId(), 
					(s.getStudent().getUser() != null ? new RegisteredUserDTO(s.getStudent().getUser().getUsername(), null, s.getStudent().getUser().getEmail()) : null),
					s.getStudent().getFirstName(),
					s.getStudent().getLastName(),
					null,
					(s.getStudent().getAddress() != null ? new AddressDTO(s.getStudent().getAddress().getId(),
																			s.getStudent().getAddress().getStreet(),
																			s.getStudent().getAddress().getHouseNumber(), null,
																			s.getStudent().getAddress().getActive()) : null),
					new HashSet<StudentOnYearDTO>(),
					new ArrayList<SubjectAttendanceDTO>(),
					null,
					s.getStudent().getActive());
	    	
	    	return new StudentOnYearDTO(s.getId(), s.getDateOfApplication(),
					(s.getStudent() != null ? student : null), s.getIndexNumber(),
					(s.getYearOfStudy() != null ? new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, s.getYearOfStudy().getActive()) : null), exams, null, s.getActive());
	    	
	    }).collect(Collectors.toList());
	    
	    Page<StudentOnYearDTO> resultPage = new PageImpl<>(studentDTOs, pageable, studentPage.getTotalElements());
		
		return new ResponseEntity<Page<StudentOnYearDTO>>(resultPage, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<StudentOnYearDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudentOnYear s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<StudentOnYearDTO>(HttpStatus.NOT_FOUND);
		}
		
		List<ExaminationDTO> exams = (ArrayList<ExaminationDTO>) s.getExaminations()
				.stream()
				.map(e -> 
				new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
						new ArrayList<NoteDTO>(),
						new ArrayList<EvaluationDTO>(),
						(e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(),
								e.getStudentOnYear().getDateOfApplication(),
								(e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
										 (e.getStudentOnYear().getStudent().getUser() != null ? new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
												null, e.getStudentOnYear().getStudent().getUser().getEmail()) : null),
										e.getStudentOnYear().getStudent().getFirstName(),
										e.getStudentOnYear().getStudent().getLastName(),
										e.getStudentOnYear().getStudent().getUmcn(),
										null, null, null, null,
										e.getStudentOnYear().getStudent().getActive()) : null),
								e.getStudentOnYear().getIndexNumber(),
								(e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
										e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
										new ArrayList<SubjectDTO>(),
										e.getStudentOnYear().getYearOfStudy().getActive()) : null),
								null, null,
								e.getStudentOnYear().getActive()) : null),
						e.getActive()))
				.collect(Collectors.toList());  
    	
    	StudentDTO student = new StudentDTO(s.getStudent().getId(), 
				(s.getStudent().getUser() != null ? new RegisteredUserDTO(s.getStudent().getUser().getUsername(), null, s.getStudent().getUser().getEmail()) : null),
				s.getStudent().getFirstName(),
				s.getStudent().getLastName(),
				null,
				(s.getStudent().getAddress() != null ? new AddressDTO(s.getStudent().getAddress().getId(),
																		s.getStudent().getAddress().getStreet(),
																		s.getStudent().getAddress().getHouseNumber(), null,
																		s.getStudent().getAddress().getActive()) : null),
				new HashSet<StudentOnYearDTO>(),
				new ArrayList<SubjectAttendanceDTO>(),
				null,
				s.getStudent().getActive());
		
		return new ResponseEntity<StudentOnYearDTO>(new StudentOnYearDTO(s.getId(), s.getDateOfApplication(),
				(s.getStudent() != null ? student : null), s.getIndexNumber(),
				(s.getYearOfStudy() != null ? new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, s.getYearOfStudy().getActive()) : null), exams, null, s.getActive()), HttpStatus.OK);
	}
	
	@PostMapping("/{id}/register-exam")
	public ResponseEntity<ExaminationDTO> registerExam(@RequestBody ExaminationDTO t, @PathVariable("id") Long id){
		StudentOnYear s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<ExaminationDTO>(HttpStatus.NOT_FOUND);
		}
		
		Examination e = examService.findById(t.getId()).get();
		
		if(e == null) {
			return new ResponseEntity<ExaminationDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.registerStudentForExam(e, s);
		
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		
		evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(p -> 
							new EvaluationDTO(p.getId(),
									p.getStartTime(), p.getEndTime(), p.getNumberOfPoints(),
									(p.getEvaluationType() != null ? new EvaluationTypeDTO(p.getEvaluationType().getId(), p.getEvaluationType().getName(), new ArrayList<EvaluationDTO>(), p.getEvaluationType().getActive()) : null),
									(p.getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(p.getEvaluationInstrument().getId(), p.getEvaluationInstrument().getName(),
											(p.getEvaluationInstrument().getFile() != null ? 
													new FileDTO(p.getEvaluationInstrument().getFile().getId(),
															p.getEvaluationInstrument().getFile().getName(), 
															p.getEvaluationInstrument().getFile().getUrl(),
															p.getEvaluationInstrument().getFile().getDescription(), null, null, null, null, null, 
															p.getEvaluationInstrument().getFile().getDocument(), 
															p.getEvaluationInstrument().getFile().getActive()) : null),
											p.getEvaluationInstrument().getActive()) : null), t, null, null, p.getActive()))
							.collect(Collectors.toList());
		StudentOnYear soy = e.getStudentOnYear();
		ExaminationDTO result = null;
		
		List<ExaminationDTO> exams = new ArrayList<ExaminationDTO>();
		
		if(soy == null) {
			result = new ExaminationDTO(e.getId(), e.getNumberOfPoints(), 
					new ArrayList<NoteDTO>(),
					evaluations, 
					null, e.getActive());
		}else {
			soy.getExaminations().add(e);
			
			exams = (List<ExaminationDTO>) soy.getExaminations().stream().map(p -> 
						new ExaminationDTO(p.getId(), p.getNumberOfPoints(), new ArrayList<NoteDTO>(), new ArrayList<EvaluationDTO>(),
								new StudentOnYearDTO(p.getStudentOnYear().getId(), null, null,
										p.getStudentOnYear().getIndexNumber(), (p.getStudentOnYear().getYearOfStudy() != null ? 
												new YearOfStudyDTO(p.getStudentOnYear().getYearOfStudy().getId(), p.getStudentOnYear().getYearOfStudy().getYearOfStudy(), null, p.getStudentOnYear().getYearOfStudy().getActive()) : null), null, null, null),
								p.getActive())).collect(Collectors.toList());
			
			result = new ExaminationDTO(e.getId(), e.getNumberOfPoints(), 
					new ArrayList<NoteDTO>(),
					evaluations, 
					new StudentOnYearDTO(soy.getId(), soy.getDateOfApplication(), null, soy.getIndexNumber(),
							(soy.getYearOfStudy() != null ? new YearOfStudyDTO(soy.getYearOfStudy().getId(), soy.getYearOfStudy().getYearOfStudy(),
									new ArrayList<SubjectDTO>(), soy.getYearOfStudy().getActive()) : null),
							exams, null, soy.getActive()), e.getActive());
		}
		
		return new ResponseEntity<ExaminationDTO>(result, HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<StudentOnYearDTO> create(@RequestBody StudentOnYearDTO t) {
		// TODO Auto-generated method stub
		 Set<Role> studentRoles = t.getStudent().getUser().getRoleNames().stream()
		            .map(r -> {
		                Role role = roleService.findByName(r);
		                return new Role(role.getId(), role.getName(), role.getActive());
		            })
		            .collect(Collectors.toSet());

		    List<Examination> exams = t.getExaminations().stream()
		            .map(e -> {
		                List<Note> notes = e.getNotes().stream()
		                        .map(n -> new Note(n.getId(), n.getContent(), null))
		                        .collect(Collectors.toList());

		                List<Evaluation> evaluations = new ArrayList<>(); 

		                return new Examination(e.getId(), e.getNumberOfPoints(), notes, evaluations, null, e.getActive());
		            })
		            .collect(Collectors.toList());

		    StudentOnYear s = service.create(new StudentOnYear(
		            null,
		            t.getDateOfApplication(),
		            new Student(
		                    t.getStudent().getId(),
		                    new RegisteredUser(
		                            t.getStudent().getUser().getId(),
		                            t.getStudent().getUser().getUsername(),
		                            t.getStudent().getUser().getPassword(),
		                            t.getStudent().getUser().getEmail(),
		                            null, null,
		                            studentRoles,
		                            t.getStudent().getUser().getActive()),
		                    null, null, null, null, null, null, null,
		                    t.getStudent().getActive()),
		            t.getIndexNumber(),
		            new YearOfStudy(
		                    t.getYearOfStudy().getId(),
		                    t.getYearOfStudy().getYearOfStudy(),
		                    new ArrayList<>(),
		                    t.getYearOfStudy().getActive()),
		            null,
		            null,
		            true));

		    if (s == null) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }

		    List<ExaminationDTO> examDTOs = s.getExaminations().stream()
		            .map(e -> new ExaminationDTO(
		                    e.getId(),
		                    e.getNumberOfPoints(),
		                    new ArrayList<NoteDTO>(),
		                    new ArrayList<EvaluationDTO>(),
		                    e.getStudentOnYear() != null ? new StudentOnYearDTO(
		                            e.getStudentOnYear().getId(),
		                            e.getStudentOnYear().getDateOfApplication(),
		                            e.getStudentOnYear().getStudent() != null ? new StudentDTO(
		                                    e.getStudentOnYear().getStudent().getId(),
		                                    e.getStudentOnYear().getStudent().getUser() != null
		                                            ? new RegisteredUserDTO(
		                                            e.getStudentOnYear().getStudent().getUser().getUsername(),
		                                            null,
		                                            e.getStudentOnYear().getStudent().getUser().getEmail())
		                                            : null,
		                                    e.getStudentOnYear().getStudent().getFirstName(),
		                                    e.getStudentOnYear().getStudent().getLastName(),
		                                    e.getStudentOnYear().getStudent().getUmcn(),
		                                    null, null, null, null,
		                                    e.getStudentOnYear().getStudent().getActive())
		                                    : null,
		                            e.getStudentOnYear().getIndexNumber(),
		                            e.getStudentOnYear().getYearOfStudy() != null
		                                    ? new YearOfStudyDTO(
		                                    e.getStudentOnYear().getYearOfStudy().getId(),
		                                    e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
		                                    new ArrayList<>(),
		                                    e.getStudentOnYear().getYearOfStudy().getActive())
		                                    : null,
		                            null, null,
		                            e.getStudentOnYear().getActive())
		                            : null,
		                    e.getActive()))
		            .collect(Collectors.toList());

		    StudentDTO student = new StudentDTO(
		            s.getStudent().getId(),
		            s.getStudent().getUser() != null
		                    ? new RegisteredUserDTO(
		                    s.getStudent().getUser().getUsername(), null,
		                    s.getStudent().getUser().getEmail())
		                    : null,
		            s.getStudent().getFirstName(),
		            s.getStudent().getLastName(),
		            null,
		            s.getStudent().getAddress() != null
		                    ? new AddressDTO(
		                    s.getStudent().getAddress().getId(),
		                    s.getStudent().getAddress().getStreet(),
		                    s.getStudent().getAddress().getHouseNumber(),
		                    null,
		                    s.getStudent().getAddress().getActive())
		                    : null,
		            new HashSet<>(),
		            new ArrayList<>(),
		            null,
		            s.getStudent().getActive());

		    StudentOnYearDTO responseDTO = new StudentOnYearDTO(
		            s.getId(),
		            s.getDateOfApplication(),
		            s.getStudent() != null ? student : null,
		            s.getIndexNumber(),
		            s.getYearOfStudy() != null
		                    ? new YearOfStudyDTO(
		                    s.getYearOfStudy().getId(),
		                    s.getYearOfStudy().getYearOfStudy(),
		                    null,
		                    s.getYearOfStudy().getActive())
		                    : null,
		            examDTOs,
		            null,
		            s.getActive());

		    return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<StudentOnYearDTO> update(@RequestBody StudentOnYearDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudentOnYear s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<StudentOnYearDTO>(HttpStatus.NOT_FOUND);
		}
		
		HashSet<Role> studentRoles = (HashSet<Role>) t.getStudent().getUser().getRoleNames().stream()
				.map(r -> 
						new Role(roleService.findByName(r).getId(),
								roleService.findByName(r).getName(),
								roleService.findByName(r).getActive()))
				.collect(Collectors.toSet());
		
		List<ExaminationDTO> examDTOs = (ArrayList<ExaminationDTO>) s.getExaminations()
				.stream()
				.map(e -> 
				new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
						new ArrayList<NoteDTO>(),
						new ArrayList<EvaluationDTO>(),
						(e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(),
								e.getStudentOnYear().getDateOfApplication(),
								(e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
										 (e.getStudentOnYear().getStudent().getUser() != null ? new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
												null, e.getStudentOnYear().getStudent().getUser().getEmail()) : null),
										e.getStudentOnYear().getStudent().getFirstName(),
										e.getStudentOnYear().getStudent().getLastName(),
										e.getStudentOnYear().getStudent().getUmcn(),
										null, null, null, null,
										e.getStudentOnYear().getStudent().getActive()) : null),
								e.getStudentOnYear().getIndexNumber(),
								(e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
										e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
										new ArrayList<SubjectDTO>(),
										e.getStudentOnYear().getYearOfStudy().getActive()) : null),
								null, null,
								e.getStudentOnYear().getActive()) : null),
						e.getActive()))
				.collect(Collectors.toList()); 
		
		List<Examination> exams = (ArrayList<Examination>) s.getExaminations()
				.stream()
				.map(e -> 
				new Examination(e.getId(), e.getNumberOfPoints(),
						new ArrayList<Note>(),
						new ArrayList<Evaluation>(),
						new StudentOnYear(e.getStudentOnYear().getId(),
								e.getStudentOnYear().getDateOfApplication(),
								new Student(e.getStudentOnYear().getStudent().getId(),
										new RegisteredUser(e.getStudentOnYear().getStudent().getUser().getId(),
												e.getStudentOnYear().getStudent().getUser().getUsername(),
												null, e.getStudentOnYear().getStudent().getUser().getEmail(), null, null, null, null),
										e.getStudentOnYear().getStudent().getFirstName(),
										e.getStudentOnYear().getStudent().getLastName(),
										e.getStudentOnYear().getStudent().getUmcn(),
										null, null, null, null,
										e.getStudentOnYear().getStudent().getActive()),
								e.getStudentOnYear().getIndexNumber(),
								new YearOfStudy(e.getStudentOnYear().getYearOfStudy().getId(),
										e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
										null,
										e.getStudentOnYear().getYearOfStudy().getActive()),
								null, null,
								e.getStudentOnYear().getActive()),
						e.getActive()))
				.collect(Collectors.toList());
		
		s.setDateOfApplication(t.getDateOfApplication());
		s.setStudent(new Student(t.getStudent().getId(),
				new RegisteredUser(t.getStudent().getUser().getId(), t.getStudent().getUser().getUsername(), 
						t.getStudent().getUser().getPassword(),
						t.getStudent().getUser().getEmail(),
						null, null, studentRoles, t.getStudent().getUser().getActive()),
				t.getStudent().getFirstName(), 
				t.getStudent().getLastName(),
				t.getStudent().getUmcn(),
				new Address(t.getStudent().getAddress().getId(),
						t.getStudent().getAddress().getStreet(), t.getStudent().getAddress().getHouseNumber(), 
						new Place(t.getStudent().getAddress().getPlace().getId(),
								t.getStudent().getAddress().getPlace().getName(),
								new Country(t.getStudent().getAddress().getPlace().getCountry().getId(),
										t.getStudent().getAddress().getPlace().getCountry().getName(),
										null, t.getStudent().getAddress().getPlace().getCountry().getActive()),
								t.getStudent().getAddress().getPlace().getActive()),
						t.getStudent().getAddress().getActive()),
				null, new ArrayList<SubjectAttendance>(), 
				new Faculty(t.getStudent().getFaculty().getId(),
						t.getStudent().getFaculty().getName(),
							new Address(t.getStudent().getFaculty().getAddress().getId(),
						t.getStudent().getFaculty().getAddress().getStreet(),
						t.getStudent().getFaculty().getAddress().getHouseNumber(), 
						new Place(t.getStudent().getFaculty().getAddress().getPlace().getId(),
								t.getStudent().getFaculty().getAddress().getPlace().getName(),
								new Country(t.getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
										t.getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
										null, t.getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()),
								t.getStudent().getFaculty().getAddress().getPlace().getActive()),
						t.getStudent().getAddress().getActive()), null, null, null, null, null, null, null, null, 
							t.getStudent().getFaculty().getActive()),
				t.getStudent().getUser().getActive()));
		s.setIndexNumber(t.getIndexNumber());
		s.setYearOfStudy(new YearOfStudy(t.getYearOfStudy().getId(), t.getYearOfStudy().getYearOfStudy(), 
				new ArrayList<Subject>(), t.getYearOfStudy().getActive()));
		s.setExaminations(exams);
		
		
		s = service.update(s);
		
		StudentDTO student = new StudentDTO(s.getStudent().getId(), 
				(s.getStudent().getUser() != null ? new RegisteredUserDTO(s.getStudent().getUser().getUsername(), null, s.getStudent().getUser().getEmail()) : null),
				s.getStudent().getFirstName(),
				s.getStudent().getLastName(),
				null,
				(s.getStudent().getAddress() != null ? new AddressDTO(s.getStudent().getAddress().getId(),
																		s.getStudent().getAddress().getStreet(),
																		s.getStudent().getAddress().getHouseNumber(), null,
																		s.getStudent().getAddress().getActive()) : null),
				new HashSet<StudentOnYearDTO>(),
				new ArrayList<SubjectAttendanceDTO>(),
				null,
				s.getStudent().getActive());
		
		return new ResponseEntity<StudentOnYearDTO>(new StudentOnYearDTO(s.getId(),
														s.getDateOfApplication(), student, s.getIndexNumber(),
														(s.getYearOfStudy() != null ? new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, s.getYearOfStudy().getActive()) : null), examDTOs, null, s.getActive()), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<StudentOnYearDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PatchMapping("/{id}")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<StudentOnYearDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudentOnYear s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<StudentOnYearDTO>(HttpStatus.NOT_FOUND);
		}
		
		List<ExaminationDTO> exams = (ArrayList<ExaminationDTO>) s.getExaminations()
				.stream()
				.map(e -> 
				new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
						new ArrayList<NoteDTO>(),
						new ArrayList<EvaluationDTO>(),
						(e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(),
								e.getStudentOnYear().getDateOfApplication(),
								(e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
										 (e.getStudentOnYear().getStudent().getUser() != null ? new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
												null, e.getStudentOnYear().getStudent().getUser().getEmail()) : null),
										e.getStudentOnYear().getStudent().getFirstName(),
										e.getStudentOnYear().getStudent().getLastName(),
										e.getStudentOnYear().getStudent().getUmcn(),
										null, null, null, null,
										e.getStudentOnYear().getStudent().getActive()) : null),
								e.getStudentOnYear().getIndexNumber(),
								(e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
										e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
										new ArrayList<SubjectDTO>(),
										e.getStudentOnYear().getYearOfStudy().getActive()) : null),
								null, null,
								e.getStudentOnYear().getActive()) : null),
						e.getActive()))
				.collect(Collectors.toList()); 
		
		service.softDelete(id);
		
		StudentDTO student = new StudentDTO(s.getStudent().getId(), 
				(s.getStudent().getUser() != null ? new RegisteredUserDTO(s.getStudent().getUser().getUsername(), null, s.getStudent().getUser().getEmail()) : null),
				s.getStudent().getFirstName(),
				s.getStudent().getLastName(),
				null,
				(s.getStudent().getAddress() != null ? new AddressDTO(s.getStudent().getAddress().getId(),
																		s.getStudent().getAddress().getStreet(),
																		s.getStudent().getAddress().getHouseNumber(), null,
																		s.getStudent().getAddress().getActive()) : null),
				new HashSet<StudentOnYearDTO>(),
				new ArrayList<SubjectAttendanceDTO>(),
				null,
				s.getStudent().getActive());
		
		return new ResponseEntity<StudentOnYearDTO>(new StudentOnYearDTO(s.getId(),
														s.getDateOfApplication(), student, s.getIndexNumber(),
														(s.getYearOfStudy() != null ? new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, s.getYearOfStudy().getActive()) : null), exams, null, s.getActive()), HttpStatus.OK);
	}
}
