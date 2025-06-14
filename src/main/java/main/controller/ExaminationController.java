package main.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import main.model.Role;

import org.antlr.v4.runtime.misc.Array2DHashSet;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.EvaluationDTO;
import main.dto.EvaluationInstrumentDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.ExaminationDTO;
import main.dto.NoteDTO;
import main.dto.StudentDTO;
import main.dto.StudentOnYearDTO;
import main.dto.YearOfStudyDTO;
import main.model.Account;
import main.model.Address;
import main.model.Country;
import main.model.Department;
import main.model.Evaluation;
import main.model.EvaluationGrade;
import main.model.EvaluationInstrument;
import main.model.EvaluationType;
import main.model.Examination;
import main.model.Faculty;
import main.model.File;
import main.model.ForumUser;
import main.model.Note;
import main.model.Place;
import main.model.RegisteredUser;
import main.model.Student;
import main.model.StudentAffairsOffice;
import main.model.StudentOnYear;
import main.model.StudentServiceStaff;
import main.model.StudyProgramme;
import main.model.SubjectAttendance;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.TeachingMaterial;
import main.model.Title;
import main.model.University;
import main.model.YearOfStudy;
import main.repository.RoleRepository;
import main.service.ExaminationService;
import main.service.RoleService;
import main.service.StudentOnYearService;

@RestController
@RequestMapping("/api/examinations")
public class ExaminationController implements ControllerInterface<ExaminationDTO> {
    @Autowired
    private ExaminationService service;
    
    @Autowired
    private StudentOnYearService studentOnYearService;
    
    @Autowired
    private RoleService roleService;

    @Override
    @Secured({"ADMIN", "TEACHER", "STUDENT", "STAFF"})
    @GetMapping
    public ResponseEntity<Iterable<ExaminationDTO>> findAll() {
        ArrayList<ExaminationDTO> exams = new ArrayList<>();
        ArrayList<NoteDTO> notes;
        ArrayList<EvaluationDTO> evaluations;

        for (Examination e : service.findAll()) {
            notes = (ArrayList<NoteDTO>) (e.getNotes() != null ? e.getNotes()
                    .stream()
                    .map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive())) : null)
                    .collect(Collectors.toList());

            evaluations = (ArrayList<EvaluationDTO>) (e.getEvaluations() != null ? e.getEvaluations().stream().map(ev ->
                            new EvaluationDTO(ev.getId(),
                                    ev.getStartTime(),
                                    ev.getEndTime(),
                                    ev.getNumberOfPoints(),
                                    (ev.getEvaluationType() != null ? new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
                                            null, ev.getEvaluationType().getActive()) : null),
                                    (ev.getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
                                            null, ev.getEvaluationInstrument().getActive()) : null),
                                    null, null, null,
                                    ev.getActive())) : null)
                    .collect(Collectors.toList());


            exams.add(new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
                    notes, evaluations,
                    (e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
                            (e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
                                    null, e.getStudentOnYear().getStudent().getFirstName(),
                                    e.getStudentOnYear().getStudent().getLastName(),
                                    e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null) : null),
                            e.getStudentOnYear().getIndexNumber(),
                            (e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
                                    e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
                                    null, null) : null),
                            null, null, null) : null),
                    e.getActive()));
        }

        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER", "STUDENT", "STAFF"})
    @GetMapping("/params")
    public ResponseEntity<Page<ExaminationDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size,
                                                        @RequestParam(defaultValue = "id") String sortBy,
                                                        @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Examination> examPage = service.findAll(pageable);

        List<ExaminationDTO> examDTOs = examPage.stream().map(e -> {
        	List<NoteDTO> notes = (ArrayList<NoteDTO>) e.getNotes()
                    .stream()
                    .map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
                    .collect(Collectors.toList());

            List<EvaluationDTO> evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev ->
                            new EvaluationDTO(ev.getId(),
                                    ev.getStartTime(),
                                    ev.getEndTime(),
                                    ev.getNumberOfPoints(),
                                    (ev.getEvaluationType() != null ? new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
                                            null, ev.getEvaluationType().getActive()) : null),
                                    (ev.getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
                                            null, ev.getEvaluationInstrument().getActive()) : null),
                                    null, null, null,
                                    ev.getActive())).collect(Collectors.toList());

            return new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
                    notes, evaluations,
                    (e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
                            (e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
                                    null, e.getStudentOnYear().getStudent().getFirstName(),
                                    e.getStudentOnYear().getStudent().getLastName(),
                                    e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null) : null),
                            e.getStudentOnYear().getIndexNumber(),
                            (e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
                                    e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
                                    null, null) : null),
                            null, null, null) : null),
                    e.getActive());
        }).collect(Collectors.toList());

        Page<ExaminationDTO> resultPage = new PageImpl<>(examDTOs, pageable, examPage.getTotalElements());
        return new ResponseEntity<>(resultPage, HttpStatus.OK);

    }

    @Secured({"ADMIN", "TEACHER", "STUDENT", "STAFF"})
    @GetMapping("/active")
    public ResponseEntity<Iterable<ExaminationDTO>> findAllActive() {
    	ArrayList<ExaminationDTO> exams = new ArrayList<>();
        ArrayList<NoteDTO> notes;
        ArrayList<EvaluationDTO> evaluations;

        for (Examination e : service.findAllActive()) {
            notes = (ArrayList<NoteDTO>) e.getNotes()
                    .stream()
                    .map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
                    .collect(Collectors.toList());

            evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev ->
                            new EvaluationDTO(ev.getId(),
                                    ev.getStartTime(),
                                    ev.getEndTime(),
                                    ev.getNumberOfPoints(),
                                    (ev.getEvaluationType() != null ? new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
                                            null, ev.getEvaluationType().getActive()) : null),
                                    (ev.getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
                                            null, ev.getEvaluationInstrument().getActive()) : null),
                                    null, null, null,
                                    ev.getActive()))
                    .collect(Collectors.toList());


            exams.add(new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
                    notes, evaluations,
                    (e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
                            (e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
                                    null, e.getStudentOnYear().getStudent().getFirstName(),
                                    e.getStudentOnYear().getStudent().getLastName(),
                                    e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null) : null),
                            e.getStudentOnYear().getIndexNumber(),
                            (e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
                                    e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
                                    null, null) : null),
                            null, null, null) : null),
                    e.getActive()));
        }

        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER", "STUDENT", "STAFF"})
    @GetMapping("/{id}")
    public ResponseEntity<ExaminationDTO> findById(@PathVariable("id") Long id) {
        Examination e = service.findById(id).orElse(null);

        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ArrayList<NoteDTO> notes;
        ArrayList<EvaluationDTO> evaluations;

        notes = (ArrayList<NoteDTO>) e.getNotes()
                .stream()
                .map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
                .collect(Collectors.toList());

        evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev ->
				        new EvaluationDTO(ev.getId(),
				                ev.getStartTime(),
				                ev.getEndTime(),
				                ev.getNumberOfPoints(),
				                (ev.getEvaluationType() != null ? 
				                		new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
				                        null, ev.getEvaluationType().getActive()) : null),
				                (ev.getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
				                        null, ev.getEvaluationInstrument().getActive()) : null),
				                null, null, null,
				                ev.getActive())).collect(Collectors.toList());


        return new ResponseEntity<>(new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
                notes, evaluations,
                (e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
                        (e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
                                null, e.getStudentOnYear().getStudent().getFirstName(),
                                e.getStudentOnYear().getStudent().getLastName(),
                                e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null) : null),
                        e.getStudentOnYear().getIndexNumber(),
                        (e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
                                e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
                                null, null) : null),
                        null, null, null) : null),
                e.getActive()), HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @PostMapping
    public ResponseEntity<ExaminationDTO> create(@RequestBody ExaminationDTO t) {
    	HashSet<Role> studentRoles = new HashSet<Role>();
    	HashSet<Role> headmasterRoles = new HashSet<Role>();
    	HashSet<Role> rectorRoles = new HashSet<Role>();
    	ArrayList<Place> places = new ArrayList<Place>();
    	ArrayList<Faculty> faculties = new ArrayList<Faculty>();
    	ArrayList<Note> notes = new ArrayList<Note>();
    	ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
    	
    	notes = (ArrayList<Note>) t.getNotes().stream()
    								.map(n -> new Note(n.getId(), n.getContent(), null))
    								.collect(Collectors.toList());
    	
    	evaluations = (ArrayList<Evaluation>) t.getEvaluations().stream()
    											.map(ev -> new Evaluation(ev.getId(), 
    																		ev.getStartTime(),
    																		ev.getEndTime(),
    																		ev.getNumberOfPoints(),
    																		new EvaluationType(ev.getEvaluationType().getId(),
    																				ev.getEvaluationType().getName(),
    																				new ArrayList<Evaluation>(),
    																				ev.getEvaluationType().getActive()),
    																		new EvaluationInstrument(ev.getEvaluationInstrument().getId(),
    																				ev.getEvaluationInstrument().getName(),
    																				new ArrayList<Evaluation>(),
    																				new File(ev.getEvaluationInstrument().getFile().getId(),
    																						ev.getEvaluationInstrument().getFile().getName(),
    																						ev.getEvaluationInstrument().getFile().getUrl(),
    																						ev.getEvaluationInstrument().getFile().getDescription(),
    																						null, null, null, null, ev.getEvaluationInstrument().getFile().getDocument(),
    																						ev.getEvaluationInstrument().getFile().getActive()),
    																				ev.getEvaluationInstrument().getActive()),
    																		null,
    																		null ,
    																		new ArrayList<EvaluationGrade>(),
    																		ev.getActive()))
    											.collect(Collectors.toList());
    	
    	studentRoles = (HashSet<Role>) t.getStudentOnYear()
    							.getStudent()
    							.getUser()
    							.getRoleNames()
    							.stream().map(r -> roleService.findByName(r))
    							.collect(Collectors.toSet());
    	
    	headmasterRoles = (HashSet<Role>) t.getStudentOnYear()
				.getStudent()
				.getFaculty()
				.getHeadmaster()
				.getUser()
				.getRoleNames()
				.stream().map(r -> roleService.findByName(r))
				.collect(Collectors.toSet());
    	
    	rectorRoles = (HashSet<Role>) t.getStudentOnYear()
				.getStudent()
				.getFaculty()
				.getUniversity()
				.getRector()
				.getUser()
				.getRoleNames()
				.stream().map(r -> roleService.findByName(r))
				.collect(Collectors.toSet());
    	
    	faculties = (ArrayList<Faculty>) t.getStudentOnYear()
											.getStudent()
											.getFaculty()
											.getUniversity()
											.getFaculties()
											.stream()
											.map(f -> 
											new Faculty(f.getId(), f.getName(), 
													new Address(f.getAddress().getId(),
															f.getAddress().getStreet(),
															f.getAddress().getHouseNumber(),
															new Place(f.getAddress().getPlace().getId(),
																	f.getAddress().getPlace().getName(),
																	new Country(f.getAddress().getPlace().getCountry().getId(),
																			f.getAddress().getPlace().getCountry().getName(),
																			new ArrayList<Place>(),
																			f.getAddress().getPlace().getCountry().getActive()),
																	f.getAddress().getPlace().getActive()),
															f.getAddress().getActive()),
													new Teacher(f.getHeadmaster().getId(),
															new RegisteredUser(f.getHeadmaster().getUser().getId(),
																	f.getHeadmaster().getUser().getUsername(),
																	f.getHeadmaster().getUser().getPassword(),
																	f.getHeadmaster().getUser().getEmail(),
																	null, null,
																	new HashSet<Role>(),
																	f.getHeadmaster().getUser().getActive()),
															f.getHeadmaster().getFirstName(),
															f.getHeadmaster().getLastName(),
															f.getHeadmaster().getUmcn(), f.getHeadmaster().getBiography(),
															new ArrayList<Title>(),
															new ArrayList<TeacherOnRealization>(),
															new ArrayList<EvaluationGrade>(),
															null,
															null,
															f.getHeadmaster().getActive()),
													new University(f.getUniversity().getId(),
															f.getUniversity().getName(),
															f.getUniversity().getDateEstablished(),
															new Address(f.getUniversity().getAddress().getId(),
																	f.getUniversity().getAddress().getStreet(),
																	f.getUniversity().getAddress().getHouseNumber(),
																	new Place(f.getUniversity().getAddress().getPlace().getId(),
																			f.getUniversity().getAddress().getPlace().getName(),
																			new Country(f.getUniversity().getAddress().getPlace().getCountry().getId(),
																					f.getUniversity().getAddress().getPlace().getCountry().getName(),
																					new ArrayList<Place>(),
																					f.getUniversity().getAddress().getPlace().getCountry().getActive()),
																			f.getUniversity().getAddress().getPlace().getActive()),
																	f.getUniversity().getAddress().getActive()),
															new Teacher(f.getUniversity().getRector().getId(),
																	new RegisteredUser(f.getUniversity().getRector().getUser().getId(),
																			f.getUniversity().getRector().getUser().getUsername(),
																			f.getUniversity().getRector().getUser().getPassword(),
																			f.getUniversity().getRector().getUser().getEmail(),
																			null, null,
																			new HashSet<Role>(),
																			f.getUniversity().getRector().getUser().getActive()),
																	f.getUniversity().getRector().getFirstName(),
																	f.getUniversity().getRector().getLastName(),
																	f.getUniversity().getRector().getUmcn(),
																	f.getUniversity().getRector().getBiography(),
																	new ArrayList<Title>(),
																	new ArrayList<TeacherOnRealization>(),
																	new ArrayList<EvaluationGrade>(),
																	null,
																	null,
																	f.getUniversity().getRector().getActive()),
															f.getUniversity().getContactDetails(),
															f.getUniversity().getDescription(), 
															new ArrayList<Faculty>(),
															f.getUniversity().getActive()),
													f.getContactDetails(), f.getDescription(),
													new HashSet<Department>(),
													new ArrayList<StudyProgramme>(),
													null, null, null))
											.toList();
    	
    	
    	StudentOnYear student = new StudentOnYear(t.getStudentOnYear().getId(), t.getStudentOnYear().getDateOfApplication(),
    			new Student(t.getStudentOnYear().getStudent().getId(),
    					new RegisteredUser(t.getStudentOnYear().getStudent().getUser().getId(), 
    							t.getStudentOnYear().getStudent().getUser().getUsername(),
    							t.getStudentOnYear().getStudent().getUser().getPassword(),
    							t.getStudentOnYear().getStudent().getUser().getEmail(),
    							null, null, studentRoles ,
    							t.getStudentOnYear().getStudent().getUser().getActive()),
    					t.getStudentOnYear().getStudent().getFirstName(),
    					t.getStudentOnYear().getStudent().getLastName(),
    					t.getStudentOnYear().getStudent().getUmcn(),
    					new Address(t.getStudentOnYear().getStudent().getAddress().getId(),
    							t.getStudentOnYear().getStudent().getAddress().getStreet(), 
    							t.getStudentOnYear().getStudent().getAddress().getHouseNumber(),
    							new Place(t.getStudentOnYear().getStudent().getAddress().getPlace().getId(),
    									t.getStudentOnYear().getStudent().getAddress().getPlace().getName(),
    									new Country(t.getStudentOnYear().getStudent().getAddress().getPlace().getCountry().getId(),
    											t.getStudentOnYear().getStudent().getAddress().getPlace().getCountry().getName(),
    											null, 
    											t.getStudentOnYear().getStudent().getAddress().getPlace().getCountry().getActive()), 
    									t.getStudentOnYear().getStudent().getAddress().getPlace().getActive()), 
    							t.getStudentOnYear().getStudent().getAddress().getActive()),
    					new HashSet<StudentOnYear>(),
    					new ArrayList<SubjectAttendance>(),
    					new Faculty(t.getStudentOnYear().getStudent().getFaculty().getId(),
    							t.getStudentOnYear().getStudent().getFaculty().getName(),
    							new Address(t.getStudentOnYear().getStudent().getFaculty().getAddress().getId(),
    									t.getStudentOnYear().getStudent().getFaculty().getAddress().getStreet(),
    									t.getStudentOnYear().getStudent().getFaculty().getAddress().getHouseNumber(),
    									new Place(t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getId(),
    											t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getName(),
    	    									new Country(t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
    	    											null, 
    	    											t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()), 
    	    									t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getActive()), 
    									t.getStudentOnYear().getStudent().getFaculty().getAddress().getActive()),
    							new Teacher(t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getId(),
    									new RegisteredUser(t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUser().getId(),
    											t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUser().getUsername(),
    											t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUser().getPassword(),
    											t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUser().getEmail(),
    											null, null, headmasterRoles,
    											t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUser().getActive()),
    									t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getFirstName(),
    									t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getLastName(),
    									t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUmcn(),
    									t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getBiography(),
    									new ArrayList<Title>(),
    									new ArrayList<TeacherOnRealization>(),
    									new ArrayList<EvaluationGrade>(),
    									null, null,
    									t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getActive()),
    							new University(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getId(),
    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getName(),
    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getDateEstablished(),
    									new Address(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getId(),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getStreet(),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getHouseNumber(),
    	    									new Place(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getId(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getName(),
    	    	    									new Country(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
    	    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
    	    	    											null, 
    	    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()), 
    	    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getActive()), 
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getActive()), 
    									new Teacher(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getId(),
    	    									new RegisteredUser(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUser().getId(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUser().getUsername(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUser().getPassword(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUser().getEmail(),
    	    											null, null, rectorRoles,
    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUser().getActive()),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getFirstName(),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getLastName(),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUmcn(),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getBiography(),
    	    									new ArrayList<Title>(),
    	    									new ArrayList<TeacherOnRealization>(),
    	    									new ArrayList<EvaluationGrade>(),
    	    									null, null,
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getActive()),
    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getContactDetails(),
    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getDescription(),
    									null, 
    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getActive()),
    							t.getStudentOnYear().getStudent().getFaculty().getContactDetails(),
    							t.getStudentOnYear().getStudent().getFaculty().getDescription(),
    							new HashSet<Department>(),
    							new ArrayList<StudyProgramme>(),
    							new ArrayList<Student>(),
    							new StudentAffairsOffice(t.getStudentOnYear().getStudent().getFaculty().getStudentAffairsOffice().getId(),
    									new ArrayList<StudentServiceStaff>(), null, t.getStudentOnYear().getStudent().getFaculty().getStudentAffairsOffice().getActive()), 
    							t.getStudentOnYear().getStudent().getFaculty().getActive()),
    					t.getStudentOnYear().getStudent().getActive()),
    			t.getStudentOnYear().getIndexNumber(), 
    			new YearOfStudy(t.getStudentOnYear().getYearOfStudy().getId(), 
    							t.getStudentOnYear().getYearOfStudy().getYearOfStudy(), null,
    							t.getStudentOnYear().getYearOfStudy().getActive()),
    			new ArrayList<Examination>(),
    			null,
    			t.getStudentOnYear().getActive());
    	
        Examination e = service.create(new Examination(null, t.getNumberOfPoints(),
                notes,
                evaluations,
                student, true));
    	

        if (e == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ArrayList<NoteDTO> noteDTOs;
        ArrayList<EvaluationDTO> evaluationDTOs;

        noteDTOs = (ArrayList<NoteDTO>) e.getNotes()
                .stream()
                .map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
                .collect(Collectors.toList());

        evaluationDTOs = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev ->
				        new EvaluationDTO(ev.getId(),
				                ev.getStartTime(),
				                ev.getEndTime(),
				                ev.getNumberOfPoints(),
				                (ev.getEvaluationType() != null ? new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
				                        null, ev.getEvaluationType().getActive()) : null),
				                (ev.getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
				                        null, ev.getEvaluationInstrument().getActive()) : null),
				                null, null, null,
				                ev.getActive())).collect(Collectors.toList());


        return new ResponseEntity<ExaminationDTO>(new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
                noteDTOs, evaluationDTOs,
                (e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
                        (e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
                                null, e.getStudentOnYear().getStudent().getFirstName(),
                                e.getStudentOnYear().getStudent().getLastName(),
                                e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null) : null),
                        e.getStudentOnYear().getIndexNumber(),
                        (e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
                                e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
                                null, null) : null),
                        null, null, null) : null),
                e.getActive()), HttpStatus.CREATED);


    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @PutMapping("/{id}")
    public ResponseEntity<ExaminationDTO> update(@RequestBody ExaminationDTO t, @PathVariable("id") Long id) {
        Examination e = service.findById(id).orElse(null);

        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<NoteDTO> notes = (ArrayList<NoteDTO>) e.getNotes()
                .stream()
                .map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
                .collect(Collectors.toList());

        List<EvaluationDTO> evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev ->
								        new EvaluationDTO(ev.getId(),
								                ev.getStartTime(),
								                ev.getEndTime(),
								                ev.getNumberOfPoints(),
								                (ev.getEvaluationType() != null ? new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
								                        null, ev.getEvaluationType().getActive()) : null),
								                (ev.getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
								                        null, ev.getEvaluationInstrument().getActive()) : null),
								                null, null, null,
								                ev.getActive()))
        		.collect(Collectors.toList());

        e.setId(t.getId());
        e.setNumberOfPoints(t.getNumberOfPoints());
        e.setNotes(service.findById(t.getId()).get().getNotes());
        e.setEvaluations(service.findById(t.getId()).get().getEvaluations());
        e.setStudentOnYear(new StudentOnYear(t.getStudentOnYear().getId(), t.getStudentOnYear().getDateOfApplication(),
    			new Student(t.getStudentOnYear().getStudent().getId(),
    					new RegisteredUser(t.getStudentOnYear().getStudent().getUser().getId(), 
    							t.getStudentOnYear().getStudent().getUser().getUsername(),
    							t.getStudentOnYear().getStudent().getUser().getPassword(),
    							t.getStudentOnYear().getStudent().getUser().getEmail(),
    							null, null, new HashSet<Role>() ,
    							t.getStudentOnYear().getStudent().getUser().getActive()),
    					t.getStudentOnYear().getStudent().getFirstName(),
    					t.getStudentOnYear().getStudent().getLastName(),
    					t.getStudentOnYear().getStudent().getUmcn(),
    					new Address(t.getStudentOnYear().getStudent().getAddress().getId(),
    							t.getStudentOnYear().getStudent().getAddress().getStreet(), 
    							t.getStudentOnYear().getStudent().getAddress().getHouseNumber(),
    							new Place(t.getStudentOnYear().getStudent().getAddress().getPlace().getId(),
    									t.getStudentOnYear().getStudent().getAddress().getPlace().getName(),
    									new Country(t.getStudentOnYear().getStudent().getAddress().getPlace().getCountry().getId(),
    											t.getStudentOnYear().getStudent().getAddress().getPlace().getCountry().getName(),
    											null, 
    											t.getStudentOnYear().getStudent().getAddress().getPlace().getCountry().getActive()), 
    									t.getStudentOnYear().getStudent().getAddress().getPlace().getActive()), 
    							t.getStudentOnYear().getStudent().getAddress().getActive()),
    					new HashSet<StudentOnYear>(),
    					new ArrayList<SubjectAttendance>(),
    					new Faculty(t.getStudentOnYear().getStudent().getFaculty().getId(),
    							t.getStudentOnYear().getStudent().getFaculty().getName(),
    							new Address(t.getStudentOnYear().getStudent().getFaculty().getAddress().getId(),
    									t.getStudentOnYear().getStudent().getFaculty().getAddress().getStreet(),
    									t.getStudentOnYear().getStudent().getFaculty().getAddress().getHouseNumber(),
    									new Place(t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getId(),
    											t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getName(),
    	    									new Country(t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
    	    											null, 
    	    											t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()), 
    	    									t.getStudentOnYear().getStudent().getFaculty().getAddress().getPlace().getActive()), 
    									t.getStudentOnYear().getStudent().getFaculty().getAddress().getActive()),
    							new Teacher(t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getId(),
    									new RegisteredUser(t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUser().getId(),
    											t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUser().getUsername(),
    											t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUser().getPassword(),
    											t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUser().getEmail(),
    											null, null, new HashSet<Role>(),
    											t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUser().getActive()),
    									t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getFirstName(),
    									t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getLastName(),
    									t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getUmcn(),
    									t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getBiography(),
    									new ArrayList<Title>(),
    									new ArrayList<TeacherOnRealization>(),
    									new ArrayList<EvaluationGrade>(),
    									null, null,
    									t.getStudentOnYear().getStudent().getFaculty().getHeadmaster().getActive()),
    							new University(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getId(),
    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getName(),
    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getDateEstablished(),
    									new Address(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getId(),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getStreet(),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getHouseNumber(),
    	    									new Place(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getId(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getName(),
    	    	    									new Country(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
    	    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
    	    	    											null, 
    	    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()), 
    	    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getPlace().getActive()), 
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getAddress().getActive()), 
    									new Teacher(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getId(),
    	    									new RegisteredUser(t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUser().getId(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUser().getUsername(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUser().getPassword(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUser().getEmail(),
    	    											null, null, new HashSet<Role>(),
    	    											t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUser().getActive()),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getFirstName(),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getLastName(),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getUmcn(),
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getBiography(),
    	    									new ArrayList<Title>(),
    	    									new ArrayList<TeacherOnRealization>(),
    	    									new ArrayList<EvaluationGrade>(),
    	    									null, null,
    	    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getRector().getActive()),
    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getContactDetails(),
    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getDescription(),
    									null, 
    									t.getStudentOnYear().getStudent().getFaculty().getUniversity().getActive()),
    							t.getStudentOnYear().getStudent().getFaculty().getContactDetails(),
    							t.getStudentOnYear().getStudent().getFaculty().getDescription(),
    							new HashSet<Department>(),
    							new ArrayList<StudyProgramme>(),
    							new ArrayList<Student>(),
    							new StudentAffairsOffice(null, null, null, null), 
    							t.getStudentOnYear().getStudent().getFaculty().getActive()),
    					t.getStudentOnYear().getStudent().getActive()),
    			t.getStudentOnYear().getIndexNumber(), 
    			new YearOfStudy(t.getStudentOnYear().getYearOfStudy().getId(), 
    							t.getStudentOnYear().getYearOfStudy().getYearOfStudy(), null,
    							t.getStudentOnYear().getYearOfStudy().getActive()),
    			new ArrayList<Examination>(),
    			null,
    			t.getStudentOnYear().getActive()));
        e.setActive(t.getActive());

        e = service.update(e);

        return new ResponseEntity<ExaminationDTO>(new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
                notes, evaluations,
                (e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
                        (e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
                                null, e.getStudentOnYear().getStudent().getFirstName(),
                                e.getStudentOnYear().getStudent().getLastName(),
                                e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null) : null),
                        e.getStudentOnYear().getIndexNumber(),
                        (e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
                                e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
                                null, null) : null),
                        null, null, null) : null),
                e.getActive()), HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<ExaminationDTO> delete(Long id) {
        return null;
    }

    @Override
    @Secured({"ADMIN", "TEACHER", "STAFF"})
    @PatchMapping("/{id}")
    public ResponseEntity<ExaminationDTO> softDelete(@PathVariable("id") Long id) {
        Examination e = service.findById(id).orElse(null);

        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ArrayList<NoteDTO> notes;
        ArrayList<EvaluationDTO> evaluations;

        notes = (ArrayList<NoteDTO>) e.getNotes()
                .stream()
                .map(n -> new NoteDTO(n.getId(), n.getContent(), null, n.getActive()))
                .collect(Collectors.toList());

        evaluations = (ArrayList<EvaluationDTO>) e.getEvaluations().stream().map(ev ->
					        new EvaluationDTO(ev.getId(),
					                ev.getStartTime(),
					                ev.getEndTime(),
					                ev.getNumberOfPoints(),
					                (ev.getEvaluationType() != null ? new EvaluationTypeDTO(ev.getEvaluationType().getId(), ev.getEvaluationType().getName(),
					                        null, ev.getEvaluationType().getActive()) : null),
					                (ev.getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(ev.getEvaluationInstrument().getId(), ev.getEvaluationInstrument().getName(),
					                        null, ev.getEvaluationInstrument().getActive()) : null),
					                null, null, null,
					                ev.getActive()))
        		.collect(Collectors.toList());


        service.softDelete(id);

        return new ResponseEntity<ExaminationDTO>(new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
                notes, evaluations,
                (e.getStudentOnYear() != null ? new StudentOnYearDTO(e.getStudentOnYear().getId(), e.getStudentOnYear().getDateOfApplication(),
                        (e.getStudentOnYear().getStudent() != null ? new StudentDTO(e.getStudentOnYear().getStudent().getId(),
                                null, e.getStudentOnYear().getStudent().getFirstName(),
                                e.getStudentOnYear().getStudent().getLastName(),
                                e.getStudentOnYear().getStudent().getUmcn(), null, null, null, null, null) : null),
                        e.getStudentOnYear().getIndexNumber(),
                        (e.getStudentOnYear().getYearOfStudy() != null ? new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
                                e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
                                null, null) : null),
                        null, null, null) : null),
                e.getActive()), HttpStatus.OK);
    }
}
