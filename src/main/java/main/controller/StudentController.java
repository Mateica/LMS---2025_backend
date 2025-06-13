package main.controller;

import java.util.ArrayList;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
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
import main.dto.AnnouncementDTO;
import main.dto.CountryDTO;
import main.dto.DepartmentDTO;
import main.dto.EvaluationDTO;
import main.dto.EvaluationGradeDTO;
import main.dto.EvaluationInstrumentDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.ExaminationDTO;
import main.dto.FacultyDTO;
import main.dto.FileDTO;
import main.dto.NoteDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.dto.RoleDTO;
import main.dto.StudentAffairsOfficeDTO;
import main.dto.StudentDTO;
import main.dto.StudentOnYearDTO;
import main.dto.StudyProgrammeDTO;
import main.dto.SubjectAttendanceDTO;
import main.dto.SubjectDTO;
import main.dto.SubjectRealizationDTO;
import main.dto.TeacherDTO;
import main.dto.TeacherOnRealizationDTO;
import main.dto.TitleDTO;
import main.dto.UniversityDTO;
import main.dto.YearOfStudyDTO;
import main.model.Account;
import main.model.Address;
import main.model.Announcement;
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
import main.model.Outcome;
import main.model.Place;
import main.model.RegisteredUser;
import main.model.Role;
import main.model.Student;
import main.model.StudentAffairsOffice;
import main.model.StudentOnYear;
import main.model.StudentServiceStaff;
import main.model.StudyProgramme;
import main.model.Subject;
import main.model.SubjectAttendance;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.Title;
import main.model.University;
import main.model.YearOfStudy;
import main.repository.RegisteredUserRepository;
import main.service.AccountService;
import main.service.ExaminationService;
import main.service.FacultyService;
import main.service.RegisteredUserService;
import main.service.RoleService;
import main.service.StudentOnYearService;
import main.service.StudentService;
import main.service.UniversityService;

@RestController
@RequestMapping("/api/students")
public class StudentController implements ControllerInterface<StudentDTO> {
	@Autowired
	private StudentService service;
	
	@Autowired
	private StudentOnYearService studentOnYearService;
	
	@Autowired
	private RegisteredUserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UniversityService universityService;
	
	@Autowired
	private ExaminationService examService;
	
	@Autowired
	private FacultyService facultyService;

	@Override
	@GetMapping
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Iterable<StudentDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<StudentDTO> students = new ArrayList<StudentDTO>();
		Set<StudentOnYearDTO> studentOnYears = new HashSet<StudentOnYearDTO>();
		ArrayList<SubjectAttendanceDTO> courses = new ArrayList<SubjectAttendanceDTO>();
		ArrayList<ExaminationDTO> exams = new ArrayList<ExaminationDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		ArrayList<EvaluationGradeDTO> grades = new ArrayList<EvaluationGradeDTO>();
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		
		for(Student s : service.findAll()) {
			for(StudentOnYear soy : s.getStudentsOnYear()) {
				for(Examination e : soy.getExaminations()) {
					for(Evaluation eval : e.getEvaluations()) {
						for(EvaluationGrade g : eval.getEvaluationGrades()) {
							grades.add(new EvaluationGradeDTO(g.getId(), 
									(g.getEvaluation() != null ? new EvaluationDTO(g.getEvaluation().getId(),
											g.getEvaluation().getStartTime(),
											g.getEvaluation().getEndTime(),
											g.getEvaluation().getNumberOfPoints(),
											(g.getEvaluation().getEvaluationType() != null ? new EvaluationTypeDTO(g.getEvaluation().getEvaluationType().getId(),
													g.getEvaluation().getEvaluationType().getName(),
													null,
													g.getEvaluation().getEvaluationType().getActive()) : null),
											(g.getEvaluation().getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(g.getEvaluation().getEvaluationInstrument().getId(),
													g.getEvaluation().getEvaluationInstrument().getName(),
												(g.getEvaluation().getEvaluationInstrument().getFile() != null ? new FileDTO(g.getEvaluation().getEvaluationInstrument().getFile().getId(),
														g.getEvaluation().getEvaluationInstrument().getFile().getUrl(),
														g.getEvaluation().getEvaluationInstrument().getFile().getDescription(),
														null, null, null, null, null, null,
														g.getEvaluation().getEvaluationInstrument().getFile().getDocument(),
														g.getEvaluation().getEvaluationInstrument().getFile().getActive()) : null), 
												g.getEvaluation().getEvaluationInstrument().getActive()) : null),
											(g.getEvaluation().getExamination() != null ? 
													new ExaminationDTO(g.getEvaluation().getExamination().getId(),
													g.getEvaluation().getExamination().getNumberOfPoints(),
													null, null, null, 
													g.getEvaluation().getExamination().getActive()) : null),
											(g.getEvaluation().getSubjectRealization() != null ? new SubjectRealizationDTO(g.getEvaluation().getSubjectRealization().getId(),
													null, null, null,
													(g.getEvaluation().getSubjectRealization().getSubject() != null ? new SubjectDTO(g.getEvaluation().getSubjectRealization().getSubject().getId(),
															g.getEvaluation().getSubjectRealization().getSubject().getName(),
															g.getEvaluation().getSubjectRealization().getSubject().getEcts(),
															g.getEvaluation().getSubjectRealization().getSubject().getActive()) : null),
													g.getEvaluation().getSubjectRealization().getActive()) : null),
											new ArrayList<EvaluationGradeDTO>(), g.getEvaluation().getActive()) : null),
									(g.getTeacher() != null ? new TeacherDTO(g.getTeacher().getId(), 
											(g.getTeacher().getUser() != null ? new RegisteredUserDTO(g.getTeacher().getUser().getUsername(), null,g.getTeacher().getUser().getEmail()) : null),
											g.getTeacher().getFirstName(), g.getTeacher().getLastName(), 
											g.getTeacher().getUmcn(), g.getTeacher().getBiography(), 
											null, null, grades, null, null,
											g.getTeacher().getActive()) : null),
									g.getDateTimeEvaluated(),
									g.getMark(), g.getActive()));
						}
						
						evaluations.add(new EvaluationDTO(eval.getId(), eval.getStartTime(), eval.getEndTime(),
								eval.getNumberOfPoints(),
								(eval.getEvaluationType() != null ? new EvaluationTypeDTO(eval.getEvaluationType().getId(),
										eval.getEvaluationType().getName(),
										new ArrayList<EvaluationDTO>(), eval.getEvaluationType().getActive()) : null),
								null,null, null, grades,
								eval.getActive()));
					}
					
					notes = (e.getNotes() != null ? (ArrayList<NoteDTO>) e.getNotes()
							.stream()
							.map(n -> new NoteDTO(n.getId(), n.getContent(), 
									null, n.getActive())).collect(Collectors.toList()) : null);
					
					exams.add(new ExaminationDTO(e.getId(), e.getNumberOfPoints(), notes, evaluations, null, e.getActive()));
				}
				
				studentOnYears.add(new StudentOnYearDTO(soy.getId(), soy.getDateOfApplication(), null, soy.getIndexNumber(),
						(soy.getYearOfStudy() != null ? new YearOfStudyDTO(soy.getYearOfStudy().getId(), soy.getYearOfStudy().getYearOfStudy(), 
								new ArrayList<SubjectDTO>(), soy.getYearOfStudy().getActive()) : null),
						exams, null, soy.getActive()));
			}
			students.add(new StudentDTO(s.getId(), 
					(s.getUser() != null ? new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()) : null),
					s.getFirstName(), s.getLastName(), s.getUmcn(), 
					(s.getAddress() != null ? new AddressDTO(s.getAddress().getId(), s.getAddress().getStreet(),
							s.getAddress().getHouseNumber(), 
							new PlaceDTO(s.getAddress().getPlace().getId(),
									s.getAddress().getPlace().getName(),
									new CountryDTO(s.getAddress().getPlace().getCountry().getId(),
											s.getAddress().getPlace().getCountry().getName(),
											new ArrayList<PlaceDTO>(),
											s.getAddress().getPlace().getCountry().getActive()),
									s.getAddress().getPlace().getActive()),
							s.getAddress().getActive()) : null),
					studentOnYears, courses, 
					(s.getFaculty() != null ? new FacultyDTO(s.getFaculty().getId(),
							s.getFaculty().getName(),
							(s.getFaculty().getAddress() != null ? new AddressDTO(s.getFaculty().getAddress().getId(),
									s.getFaculty().getAddress().getStreet(), s.getFaculty().getAddress().getHouseNumber(),
									new PlaceDTO(s.getFaculty().getAddress().getPlace().getId(),
											s.getFaculty().getAddress().getPlace().getName(),
											new CountryDTO(s.getFaculty().getAddress().getPlace().getCountry().getId(),
													s.getFaculty().getAddress().getPlace().getCountry().getName(),
													new ArrayList<PlaceDTO>(), 
													s.getFaculty().getAddress().getPlace().getCountry().getActive()), 
											s.getFaculty().getAddress().getPlace().getActive()),
									s.getFaculty().getAddress().getActive()) : null),
							(s.getFaculty().getHeadmaster() != null ? new TeacherDTO(s.getFaculty().getHeadmaster().getId(),
									new RegisteredUserDTO(s.getFaculty().getHeadmaster().getUser().getUsername(), null,
											s.getFaculty().getHeadmaster().getUser().getEmail()),
									s.getFaculty().getHeadmaster().getFirstName(),
									s.getFaculty().getHeadmaster().getLastName(),
									s.getFaculty().getHeadmaster().getUmcn(), 
									s.getFaculty().getHeadmaster().getBiography(),
									new ArrayList<TitleDTO>(), 
									new ArrayList<TeacherOnRealizationDTO>(), 
									new ArrayList<EvaluationGradeDTO>(), null, null,
									s.getFaculty().getHeadmaster().getActive()) : null), 
							(s.getFaculty().getUniversity() != null ? new UniversityDTO(s.getFaculty().getUniversity().getId(),
									s.getFaculty().getUniversity().getName(),
									s.getFaculty().getUniversity().getDateEstablished(),
									(s.getFaculty().getUniversity() != null ? new AddressDTO(s.getFaculty().getUniversity().getAddress().getId(),
											s.getFaculty().getUniversity().getAddress().getStreet(),
											s.getFaculty().getUniversity().getAddress().getHouseNumber(),
									(s.getFaculty().getUniversity().getAddress().getPlace() != null ? new PlaceDTO(s.getFaculty().getUniversity().getAddress().getPlace().getId(),
											s.getFaculty().getUniversity().getAddress().getPlace().getName(),
											(s.getFaculty().getUniversity().getAddress().getPlace().getCountry() != null ? new CountryDTO(s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
													s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
													new ArrayList<PlaceDTO>(), 
													s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()) : null), 
											s.getFaculty().getUniversity().getAddress().getPlace().getActive()) : null),
									s.getFaculty().getUniversity().getAddress().getActive()) : null),
									(s.getFaculty().getUniversity().getRector() != null ? new TeacherDTO(s.getFaculty().getUniversity().getRector().getId(),
											(s.getFaculty().getUniversity().getRector() != null ? new RegisteredUserDTO(s.getFaculty().getUniversity().getRector().getUser().getUsername(), null,
													s.getFaculty().getUniversity().getRector().getUser().getEmail()) : null),
											s.getFaculty().getUniversity().getRector().getFirstName(),
											s.getFaculty().getUniversity().getRector().getLastName(),
											s.getFaculty().getUniversity().getRector().getUmcn(), 
											s.getFaculty().getUniversity().getRector().getBiography(),
											new ArrayList<TitleDTO>(), new ArrayList<TeacherOnRealizationDTO>(), 
											new ArrayList<EvaluationGradeDTO>(), null, null,
											s.getFaculty().getUniversity().getRector().getActive()) : null),
									s.getFaculty().getUniversity().getContactDetails(),
									s.getFaculty().getUniversity().getDescription(), 
									new ArrayList<FacultyDTO>(),
									s.getFaculty().getUniversity().getActive()) : null),
							s.getFaculty().getContactDetails(), s.getFaculty().getDescription(),
							new HashSet<DepartmentDTO>(),
							new ArrayList<StudyProgrammeDTO>(), 
							new ArrayList<StudentDTO>(),
							null, s.getFaculty().getActive()) : null),
					s.getActive()));
		}
		
		return new ResponseEntity<Iterable<StudentDTO>>(students, HttpStatus.OK);
	}

	@Override
	@GetMapping("/params")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Page<StudentDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		// TODO Auto-generated method stub
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Student> studentPage = service.findAll(pageable);
	    
	    List<StudentDTO> studentDTOs = new ArrayList<StudentDTO>();
	    
	    Set<StudentOnYearDTO> studentOnYears = new HashSet<StudentOnYearDTO>();
		ArrayList<SubjectAttendanceDTO> courses = new ArrayList<SubjectAttendanceDTO>();
		ArrayList<ExaminationDTO> exams = new ArrayList<ExaminationDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		ArrayList<EvaluationGradeDTO> grades = new ArrayList<EvaluationGradeDTO>();
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		
		for(Student s : studentPage) {
			for(StudentOnYear soy : s.getStudentsOnYear()) {
				for(Examination e : soy.getExaminations()) {
					for(Evaluation eval : e.getEvaluations()) {
						for(EvaluationGrade g : eval.getEvaluationGrades()) {
							grades.add(new EvaluationGradeDTO(g.getId(), 
									(g.getEvaluation() != null ? new EvaluationDTO(g.getEvaluation().getId(),
											g.getEvaluation().getStartTime(),
											g.getEvaluation().getEndTime(),
											g.getEvaluation().getNumberOfPoints(),
											(g.getEvaluation().getEvaluationType() != null ? new EvaluationTypeDTO(g.getEvaluation().getEvaluationType().getId(),
													g.getEvaluation().getEvaluationType().getName(),
													new ArrayList<EvaluationDTO>(),
													g.getEvaluation().getEvaluationType().getActive()) : null),
											(g.getEvaluation().getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(g.getEvaluation().getEvaluationInstrument().getId(),
													g.getEvaluation().getEvaluationInstrument().getName(),
												(g.getEvaluation().getEvaluationInstrument().getFile() != null ? new FileDTO(g.getEvaluation().getEvaluationInstrument().getFile().getId(),
														g.getEvaluation().getEvaluationInstrument().getFile().getUrl(),
														g.getEvaluation().getEvaluationInstrument().getFile().getDescription(),
														null, null, null, null, null, null,
														g.getEvaluation().getEvaluationInstrument().getFile().getDocument(),
														g.getEvaluation().getEvaluationInstrument().getFile().getActive()) : null), 
												g.getEvaluation().getEvaluationInstrument().getActive()) : null),
											(g.getEvaluation().getExamination() != null ? new ExaminationDTO(g.getEvaluation().getExamination().getId(),
													g.getEvaluation().getExamination().getNumberOfPoints(),
													null, new ArrayList<EvaluationDTO>(), null, 
													g.getEvaluation().getExamination().getActive()) : null),
											(g.getEvaluation().getSubjectRealization() != null ? new SubjectRealizationDTO(g.getEvaluation().getSubjectRealization().getId(),
													new ArrayList<EvaluationDTO>(), null, null,
													(g.getEvaluation().getSubjectRealization().getSubject() != null ? new SubjectDTO(g.getEvaluation().getSubjectRealization().getSubject().getId(),
															g.getEvaluation().getSubjectRealization().getSubject().getName(),
															g.getEvaluation().getSubjectRealization().getSubject().getEcts(),
															g.getEvaluation().getSubjectRealization().getSubject().getActive()) : null),
													g.getEvaluation().getSubjectRealization().getActive()) : null),
											new ArrayList<EvaluationGradeDTO>(), g.getEvaluation().getActive()) : null),
									(g.getTeacher() != null ? new TeacherDTO(g.getTeacher().getId(), 
											(g.getTeacher().getUser() != null ? new RegisteredUserDTO(g.getTeacher().getUser().getUsername(), null,g.getTeacher().getUser().getEmail()) : null),
											g.getTeacher().getFirstName(), g.getTeacher().getLastName(), 
											g.getTeacher().getUmcn(), g.getTeacher().getBiography(), 
											null, null, grades, null, null,
											g.getTeacher().getActive()) : null),
									g.getDateTimeEvaluated(),
									g.getMark(), g.getActive()));
						}
						
						evaluations.add(new EvaluationDTO(eval.getId(), eval.getStartTime(), eval.getEndTime(),
								eval.getNumberOfPoints(),
								(eval.getEvaluationType() != null ? new EvaluationTypeDTO(eval.getEvaluationType().getId(),
										eval.getEvaluationType().getName(),
										new ArrayList<EvaluationDTO>(), eval.getEvaluationType().getActive()) : null),
								null,null, null, grades,
								eval.getEvaluationType().getActive()));
					}
					
					notes = (ArrayList<NoteDTO>) e.getNotes()
							.stream()
							.map(n -> new NoteDTO(n.getId(), n.getContent(), 
									null, n.getActive())).collect(Collectors.toList());
					
					exams.add(new ExaminationDTO(e.getId(), e.getNumberOfPoints(), notes, evaluations, null, e.getActive()));
				}
				
				studentOnYears.add(new StudentOnYearDTO(soy.getId(), soy.getDateOfApplication(), null, soy.getIndexNumber(),
						(soy.getYearOfStudy() != null ? new YearOfStudyDTO(soy.getYearOfStudy().getId(), soy.getYearOfStudy().getYearOfStudy(), 
								new ArrayList<SubjectDTO>(), soy.getYearOfStudy().getActive()) : null),
						exams, null, soy.getActive()));
			}
			studentDTOs.add(new StudentDTO(s.getId(), 
					(s.getUser() != null ? new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()) : null),
					s.getFirstName(), s.getLastName(), s.getUmcn(), 
					(s.getAddress() != null ? new AddressDTO(s.getAddress().getId(), s.getAddress().getStreet(),
							s.getAddress().getHouseNumber(), 
							(s.getAddress().getPlace() != null ? new PlaceDTO(s.getAddress().getPlace().getId(),
									s.getAddress().getPlace().getName(),
									(s.getAddress().getPlace().getCountry() != null ?  new CountryDTO(s.getAddress().getPlace().getCountry().getId(),
											s.getAddress().getPlace().getCountry().getName(),
											new ArrayList<PlaceDTO>(),
											s.getAddress().getPlace().getCountry().getActive()) : null),
									s.getAddress().getPlace().getActive()) : null),
							s.getAddress().getActive()) : null),
					studentOnYears, courses, 
					new FacultyDTO(s.getFaculty().getId(),
							s.getFaculty().getName(),
							(s.getFaculty().getAddress() != null ? new AddressDTO(s.getFaculty().getAddress().getId(),
									s.getFaculty().getAddress().getStreet(), s.getFaculty().getAddress().getHouseNumber(),
									(s.getFaculty().getAddress() != null ? new PlaceDTO(s.getFaculty().getAddress().getPlace().getId(),
											s.getFaculty().getAddress().getPlace().getName(),
											(s.getFaculty().getAddress().getPlace().getCountry() != null ? new CountryDTO(s.getFaculty().getAddress().getPlace().getCountry().getId(),
													s.getFaculty().getAddress().getPlace().getCountry().getName(),
													new ArrayList<PlaceDTO>(), 
													s.getFaculty().getAddress().getPlace().getCountry().getActive()) : null), 
											s.getFaculty().getAddress().getPlace().getActive()) : null),
									s.getFaculty().getAddress().getActive()) : null),
							(s.getFaculty().getHeadmaster() != null ? new TeacherDTO(s.getFaculty().getHeadmaster().getId(),
									(s.getFaculty().getHeadmaster().getUser() != null ? new RegisteredUserDTO(s.getFaculty().getHeadmaster().getUser().getUsername(), null,
											s.getFaculty().getHeadmaster().getUser().getEmail()) : null),
									s.getFaculty().getHeadmaster().getFirstName(),
									s.getFaculty().getHeadmaster().getLastName(),
									s.getFaculty().getHeadmaster().getUmcn(), 
									s.getFaculty().getHeadmaster().getBiography(),
									new ArrayList<TitleDTO>(), 
									new ArrayList<TeacherOnRealizationDTO>(), 
									new ArrayList<EvaluationGradeDTO>(), null, null,
									s.getFaculty().getHeadmaster().getActive()) : null), 
							(s.getFaculty().getUniversity() != null ? new UniversityDTO(s.getFaculty().getUniversity().getId(),
									s.getFaculty().getUniversity().getName(),
									s.getFaculty().getUniversity().getDateEstablished(),
									(s.getFaculty().getUniversity().getAddress() != null ? new AddressDTO(s.getFaculty().getUniversity().getAddress().getId(),
											s.getFaculty().getUniversity().getAddress().getStreet(),
											s.getFaculty().getUniversity().getAddress().getHouseNumber(),
									(s.getFaculty().getUniversity().getAddress().getPlace() != null ? new PlaceDTO(s.getFaculty().getUniversity().getAddress().getPlace().getId(),
											s.getFaculty().getUniversity().getAddress().getPlace().getName(),
										(s.getFaculty().getUniversity().getAddress().getPlace().getCountry() != null ? 	new CountryDTO(s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
												s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
												new ArrayList<PlaceDTO>(), 
												s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()) : null), 
											s.getFaculty().getUniversity().getAddress().getPlace().getActive()) : null),
									s.getFaculty().getUniversity().getAddress().getActive()) : null),
									(s.getFaculty().getUniversity().getRector() != null ? new TeacherDTO(s.getFaculty().getUniversity().getRector().getId(),
											(s.getFaculty().getUniversity().getRector().getUser() != null ? new RegisteredUserDTO(s.getFaculty().getUniversity().getRector().getUser().getUsername(), null,
													s.getFaculty().getUniversity().getRector().getUser().getEmail()) : null),
											s.getFaculty().getUniversity().getRector().getFirstName(),
											s.getFaculty().getUniversity().getRector().getLastName(),
											s.getFaculty().getUniversity().getRector().getUmcn(), 
											s.getFaculty().getUniversity().getRector().getBiography(),
											new ArrayList<TitleDTO>(), 
											new ArrayList<TeacherOnRealizationDTO>(), 
											new ArrayList<EvaluationGradeDTO>(), null, null,
											s.getFaculty().getUniversity().getRector().getActive()) : null),
									s.getFaculty().getUniversity().getContactDetails(),
									s.getFaculty().getUniversity().getDescription(), 
									new ArrayList<FacultyDTO>(),
									s.getFaculty().getUniversity().getActive()) : null),
							s.getFaculty().getContactDetails(), s.getFaculty().getDescription(),
							new HashSet<DepartmentDTO>(),
							new ArrayList<StudyProgrammeDTO>(), 
							new ArrayList<StudentDTO>(),
							null, s.getFaculty().getActive()),
					s.getActive()));
		}
		
		
	    
	    Page<StudentDTO> resultPage = new PageImpl<StudentDTO>(studentDTOs, pageable, studentPage.getTotalElements());
	    
		return new ResponseEntity<Page<StudentDTO>>(resultPage, HttpStatus.OK);
	}
	
	// DONE
	@GetMapping("/active")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Iterable<StudentDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<StudentDTO> students = new ArrayList<StudentDTO>();
		Set<StudentOnYearDTO> studentOnYears = new HashSet<StudentOnYearDTO>();
		ArrayList<SubjectAttendanceDTO> courses = new ArrayList<SubjectAttendanceDTO>();
		ArrayList<ExaminationDTO> exams = new ArrayList<ExaminationDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		ArrayList<EvaluationGradeDTO> grades = new ArrayList<EvaluationGradeDTO>();
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		
		for(Student s : service.findAllActive()) {
			for(StudentOnYear soy : s.getStudentsOnYear()) {
				for(Examination e : soy.getExaminations()) {
					for(Evaluation eval : e.getEvaluations()) {
						for(EvaluationGrade g : eval.getEvaluationGrades()) {
							grades.add(new EvaluationGradeDTO(g.getId(), 
									(g.getEvaluation() != null ? new EvaluationDTO(g.getEvaluation().getId(),
											g.getEvaluation().getStartTime(),
											g.getEvaluation().getEndTime(),
											g.getEvaluation().getNumberOfPoints(),
											(g.getEvaluation().getEvaluationType() != null ? new EvaluationTypeDTO(g.getEvaluation().getEvaluationType().getId(),
													g.getEvaluation().getEvaluationType().getName(),
													null,
													g.getEvaluation().getEvaluationType().getActive()) : null),
											(g.getEvaluation().getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(g.getEvaluation().getEvaluationInstrument().getId(),
													g.getEvaluation().getEvaluationInstrument().getName(),
												(g.getEvaluation().getEvaluationInstrument().getFile() != null ? new FileDTO(g.getEvaluation().getEvaluationInstrument().getFile().getId(),
														g.getEvaluation().getEvaluationInstrument().getFile().getUrl(),
														g.getEvaluation().getEvaluationInstrument().getFile().getDescription(),
														null, null, null, null, null, null,
														g.getEvaluation().getEvaluationInstrument().getFile().getDocument(),
														g.getEvaluation().getEvaluationInstrument().getFile().getActive()) : null), 
												g.getEvaluation().getEvaluationInstrument().getActive()) : null),
											(g.getEvaluation().getExamination() != null ? 
													new ExaminationDTO(g.getEvaluation().getExamination().getId(),
													g.getEvaluation().getExamination().getNumberOfPoints(),
													null, null, null, 
													g.getEvaluation().getExamination().getActive()) : null),
											(g.getEvaluation().getSubjectRealization() != null ? new SubjectRealizationDTO(g.getEvaluation().getSubjectRealization().getId(),
													null, null, null,
													(g.getEvaluation().getSubjectRealization().getSubject() != null ? new SubjectDTO(g.getEvaluation().getSubjectRealization().getSubject().getId(),
															g.getEvaluation().getSubjectRealization().getSubject().getName(),
															g.getEvaluation().getSubjectRealization().getSubject().getEcts(),
															g.getEvaluation().getSubjectRealization().getSubject().getActive()) : null),
													g.getEvaluation().getSubjectRealization().getActive()) : null),
											new ArrayList<EvaluationGradeDTO>(), g.getEvaluation().getActive()) : null),
									(g.getTeacher() != null ? new TeacherDTO(g.getTeacher().getId(), 
											(g.getTeacher().getUser() != null ? new RegisteredUserDTO(g.getTeacher().getUser().getUsername(), null,g.getTeacher().getUser().getEmail()) : null),
											g.getTeacher().getFirstName(), g.getTeacher().getLastName(), 
											g.getTeacher().getUmcn(), g.getTeacher().getBiography(), 
											null, null, grades, null, null,
											g.getTeacher().getActive()) : null),
									g.getDateTimeEvaluated(),
									g.getMark(), g.getActive()));
						}
						
						evaluations.add(new EvaluationDTO(eval.getId(), eval.getStartTime(), eval.getEndTime(),
								eval.getNumberOfPoints(),
								(eval.getEvaluationType() != null ? new EvaluationTypeDTO(eval.getEvaluationType().getId(),
										eval.getEvaluationType().getName(),
										new ArrayList<EvaluationDTO>(), eval.getEvaluationType().getActive()) : null),
								null,null, null, grades,
								eval.getActive()));
					}
					
					notes = (e.getNotes() != null ? (ArrayList<NoteDTO>) e.getNotes()
							.stream()
							.map(n -> new NoteDTO(n.getId(), n.getContent(), 
									null, n.getActive())).collect(Collectors.toList()) : null);
					
					exams.add(new ExaminationDTO(e.getId(), e.getNumberOfPoints(), notes, evaluations, null, e.getActive()));
				}
				
				studentOnYears.add(new StudentOnYearDTO(soy.getId(), soy.getDateOfApplication(), null, soy.getIndexNumber(),
						(soy.getYearOfStudy() != null ? new YearOfStudyDTO(soy.getYearOfStudy().getId(), soy.getYearOfStudy().getYearOfStudy(), 
								new ArrayList<SubjectDTO>(), soy.getYearOfStudy().getActive()) : null),
						exams, null, soy.getActive()));
			}
			students.add(new StudentDTO(s.getId(), 
					(s.getUser() != null ? new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()) : null),
					s.getFirstName(), s.getLastName(), s.getUmcn(), 
					(s.getAddress() != null ? new AddressDTO(s.getAddress().getId(), s.getAddress().getStreet(),
							s.getAddress().getHouseNumber(), 
							new PlaceDTO(s.getAddress().getPlace().getId(),
									s.getAddress().getPlace().getName(),
									new CountryDTO(s.getAddress().getPlace().getCountry().getId(),
											s.getAddress().getPlace().getCountry().getName(),
											new ArrayList<PlaceDTO>(),
											s.getAddress().getPlace().getCountry().getActive()),
									s.getAddress().getPlace().getActive()),
							s.getAddress().getActive()) : null),
					studentOnYears, courses, 
					(s.getFaculty() != null ? new FacultyDTO(s.getFaculty().getId(),
							s.getFaculty().getName(),
							(s.getFaculty().getAddress() != null ? new AddressDTO(s.getFaculty().getAddress().getId(),
									s.getFaculty().getAddress().getStreet(), s.getFaculty().getAddress().getHouseNumber(),
									new PlaceDTO(s.getFaculty().getAddress().getPlace().getId(),
											s.getFaculty().getAddress().getPlace().getName(),
											new CountryDTO(s.getFaculty().getAddress().getPlace().getCountry().getId(),
													s.getFaculty().getAddress().getPlace().getCountry().getName(),
													new ArrayList<PlaceDTO>(), 
													s.getFaculty().getAddress().getPlace().getCountry().getActive()), 
											s.getFaculty().getAddress().getPlace().getActive()),
									s.getFaculty().getAddress().getActive()) : null),
							(s.getFaculty().getHeadmaster() != null ? new TeacherDTO(s.getFaculty().getHeadmaster().getId(),
									new RegisteredUserDTO(s.getFaculty().getHeadmaster().getUser().getUsername(), null,
											s.getFaculty().getHeadmaster().getUser().getEmail()),
									s.getFaculty().getHeadmaster().getFirstName(),
									s.getFaculty().getHeadmaster().getLastName(),
									s.getFaculty().getHeadmaster().getUmcn(), 
									s.getFaculty().getHeadmaster().getBiography(),
									new ArrayList<TitleDTO>(), 
									new ArrayList<TeacherOnRealizationDTO>(), 
									new ArrayList<EvaluationGradeDTO>(), null, null,
									s.getFaculty().getHeadmaster().getActive()) : null), 
							(s.getFaculty().getUniversity() != null ? new UniversityDTO(s.getFaculty().getUniversity().getId(),
									s.getFaculty().getUniversity().getName(),
									s.getFaculty().getUniversity().getDateEstablished(),
									(s.getFaculty().getUniversity() != null ? new AddressDTO(s.getFaculty().getUniversity().getAddress().getId(),
											s.getFaculty().getUniversity().getAddress().getStreet(),
											s.getFaculty().getUniversity().getAddress().getHouseNumber(),
									(s.getFaculty().getUniversity().getAddress().getPlace() != null ? new PlaceDTO(s.getFaculty().getUniversity().getAddress().getPlace().getId(),
											s.getFaculty().getUniversity().getAddress().getPlace().getName(),
											(s.getFaculty().getUniversity().getAddress().getPlace().getCountry() != null ? new CountryDTO(s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
													s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
													new ArrayList<PlaceDTO>(), 
													s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()) : null), 
											s.getFaculty().getUniversity().getAddress().getPlace().getActive()) : null),
									s.getFaculty().getUniversity().getAddress().getActive()) : null),
									(s.getFaculty().getUniversity().getRector() != null ? new TeacherDTO(s.getFaculty().getUniversity().getRector().getId(),
											(s.getFaculty().getUniversity().getRector() != null ? new RegisteredUserDTO(s.getFaculty().getUniversity().getRector().getUser().getUsername(), null,
													s.getFaculty().getUniversity().getRector().getUser().getEmail()) : null),
											s.getFaculty().getUniversity().getRector().getFirstName(),
											s.getFaculty().getUniversity().getRector().getLastName(),
											s.getFaculty().getUniversity().getRector().getUmcn(), 
											s.getFaculty().getUniversity().getRector().getBiography(),
											new ArrayList<TitleDTO>(), new ArrayList<TeacherOnRealizationDTO>(), 
											new ArrayList<EvaluationGradeDTO>(), null, null,
											s.getFaculty().getUniversity().getRector().getActive()) : null),
									s.getFaculty().getUniversity().getContactDetails(),
									s.getFaculty().getUniversity().getDescription(), 
									new ArrayList<FacultyDTO>(),
									s.getFaculty().getUniversity().getActive()) : null),
							s.getFaculty().getContactDetails(), s.getFaculty().getDescription(),
							new HashSet<DepartmentDTO>(),
							new ArrayList<StudyProgrammeDTO>(), 
							new ArrayList<StudentDTO>(),
							null, s.getFaculty().getActive()) : null),
					s.getActive()));
		}
		
		return new ResponseEntity<Iterable<StudentDTO>>(students, HttpStatus.OK);
	}
	
	

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<StudentDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Student s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
		}
		
		Set<StudentOnYearDTO> studentOnYears = new HashSet<StudentOnYearDTO>();
		ArrayList<SubjectAttendanceDTO> courses = new ArrayList<SubjectAttendanceDTO>();
		ArrayList<ExaminationDTO> exams = new ArrayList<ExaminationDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		ArrayList<EvaluationGradeDTO> grades = new ArrayList<EvaluationGradeDTO>();
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		
		
		for(StudentOnYear soy : s.getStudentsOnYear()) {
			for(Examination e : soy.getExaminations()) {
				for(Evaluation eval : e.getEvaluations()) {
					for(EvaluationGrade g : eval.getEvaluationGrades()) {
						grades.add(new EvaluationGradeDTO(g.getId(), 
								(g.getEvaluation() != null ? new EvaluationDTO(g.getEvaluation().getId(),
										g.getEvaluation().getStartTime(),
										g.getEvaluation().getEndTime(),
										g.getEvaluation().getNumberOfPoints(),
										(g.getEvaluation().getEvaluationType() != null ? new EvaluationTypeDTO(g.getEvaluation().getEvaluationType().getId(),
												g.getEvaluation().getEvaluationType().getName(),
												null,
												g.getEvaluation().getEvaluationType().getActive()) : null),
										(g.getEvaluation().getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(g.getEvaluation().getEvaluationInstrument().getId(),
												g.getEvaluation().getEvaluationInstrument().getName(),
											(g.getEvaluation().getEvaluationInstrument().getFile() != null ? new FileDTO(g.getEvaluation().getEvaluationInstrument().getFile().getId(),
													g.getEvaluation().getEvaluationInstrument().getFile().getUrl(),
													g.getEvaluation().getEvaluationInstrument().getFile().getDescription(),
													null, null, null, null, null, null,
													g.getEvaluation().getEvaluationInstrument().getFile().getDocument(),
													g.getEvaluation().getEvaluationInstrument().getFile().getActive()) : null), 
											g.getEvaluation().getEvaluationInstrument().getActive()) : null),
										(g.getEvaluation().getExamination() != null ? 
												new ExaminationDTO(g.getEvaluation().getExamination().getId(),
												g.getEvaluation().getExamination().getNumberOfPoints(),
												null, null, null, 
												g.getEvaluation().getExamination().getActive()) : null),
										(g.getEvaluation().getSubjectRealization() != null ? new SubjectRealizationDTO(g.getEvaluation().getSubjectRealization().getId(),
												null, null, null,
												(g.getEvaluation().getSubjectRealization().getSubject() != null ? new SubjectDTO(g.getEvaluation().getSubjectRealization().getSubject().getId(),
														g.getEvaluation().getSubjectRealization().getSubject().getName(),
														g.getEvaluation().getSubjectRealization().getSubject().getEcts(),
														g.getEvaluation().getSubjectRealization().getSubject().getActive()) : null),
												g.getEvaluation().getSubjectRealization().getActive()) : null),
										new ArrayList<EvaluationGradeDTO>(), g.getEvaluation().getActive()) : null),
								(g.getTeacher() != null ? new TeacherDTO(g.getTeacher().getId(), 
										(g.getTeacher().getUser() != null ? new RegisteredUserDTO(g.getTeacher().getUser().getUsername(), null,g.getTeacher().getUser().getEmail()) : null),
										g.getTeacher().getFirstName(), g.getTeacher().getLastName(), 
										g.getTeacher().getUmcn(), g.getTeacher().getBiography(), 
										null, null, grades, null, null,
										g.getTeacher().getActive()) : null),
								g.getDateTimeEvaluated(),
								g.getMark(), g.getActive()));
					}
					
					evaluations.add(new EvaluationDTO(eval.getId(), eval.getStartTime(), eval.getEndTime(),
							eval.getNumberOfPoints(),
							(eval.getEvaluationType() != null ? new EvaluationTypeDTO(eval.getEvaluationType().getId(),
									eval.getEvaluationType().getName(),
									new ArrayList<EvaluationDTO>(), eval.getEvaluationType().getActive()) : null),
							null,null, null, grades,
							eval.getActive()));
				}
				
				notes = (e.getNotes() != null ? (ArrayList<NoteDTO>) e.getNotes()
						.stream()
						.map(n -> new NoteDTO(n.getId(), n.getContent(), 
								null, n.getActive())).collect(Collectors.toList()) : null);
				
				exams.add(new ExaminationDTO(e.getId(), e.getNumberOfPoints(), notes, evaluations, null, e.getActive()));
			}
			
			studentOnYears.add(new StudentOnYearDTO(soy.getId(), soy.getDateOfApplication(), null, soy.getIndexNumber(),
					(soy.getYearOfStudy() != null ? new YearOfStudyDTO(soy.getYearOfStudy().getId(), soy.getYearOfStudy().getYearOfStudy(), 
							new ArrayList<SubjectDTO>(), soy.getYearOfStudy().getActive()) : null),
					exams, null, soy.getActive()));
		}
		
		return new ResponseEntity<StudentDTO>(new StudentDTO(s.getId(), 
				(s.getUser() != null ? new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()) : null),
				s.getFirstName(), s.getLastName(), s.getUmcn(), 
				(s.getAddress() != null ? new AddressDTO(s.getAddress().getId(), s.getAddress().getStreet(),
						s.getAddress().getHouseNumber(), 
						new PlaceDTO(s.getAddress().getPlace().getId(),
								s.getAddress().getPlace().getName(),
								new CountryDTO(s.getAddress().getPlace().getCountry().getId(),
										s.getAddress().getPlace().getCountry().getName(),
										new ArrayList<PlaceDTO>(),
										s.getAddress().getPlace().getCountry().getActive()),
								s.getAddress().getPlace().getActive()),
						s.getAddress().getActive()) : null),
				studentOnYears, courses, 
				(s.getFaculty() != null ? new FacultyDTO(s.getFaculty().getId(),
						s.getFaculty().getName(),
						(s.getFaculty().getAddress() != null ? new AddressDTO(s.getFaculty().getAddress().getId(),
								s.getFaculty().getAddress().getStreet(), s.getFaculty().getAddress().getHouseNumber(),
								new PlaceDTO(s.getFaculty().getAddress().getPlace().getId(),
										s.getFaculty().getAddress().getPlace().getName(),
										new CountryDTO(s.getFaculty().getAddress().getPlace().getCountry().getId(),
												s.getFaculty().getAddress().getPlace().getCountry().getName(),
												new ArrayList<PlaceDTO>(), 
												s.getFaculty().getAddress().getPlace().getCountry().getActive()), 
										s.getFaculty().getAddress().getPlace().getActive()),
								s.getFaculty().getAddress().getActive()) : null),
						(s.getFaculty().getHeadmaster() != null ? new TeacherDTO(s.getFaculty().getHeadmaster().getId(),
								new RegisteredUserDTO(s.getFaculty().getHeadmaster().getUser().getUsername(), null,
										s.getFaculty().getHeadmaster().getUser().getEmail()),
								s.getFaculty().getHeadmaster().getFirstName(),
								s.getFaculty().getHeadmaster().getLastName(),
								s.getFaculty().getHeadmaster().getUmcn(), 
								s.getFaculty().getHeadmaster().getBiography(),
								new ArrayList<TitleDTO>(), 
								new ArrayList<TeacherOnRealizationDTO>(), 
								new ArrayList<EvaluationGradeDTO>(), null, null,
								s.getFaculty().getHeadmaster().getActive()) : null), 
						(s.getFaculty().getUniversity() != null ? new UniversityDTO(s.getFaculty().getUniversity().getId(),
								s.getFaculty().getUniversity().getName(),
								s.getFaculty().getUniversity().getDateEstablished(),
								(s.getFaculty().getUniversity() != null ? new AddressDTO(s.getFaculty().getUniversity().getAddress().getId(),
										s.getFaculty().getUniversity().getAddress().getStreet(),
										s.getFaculty().getUniversity().getAddress().getHouseNumber(),
								(s.getFaculty().getUniversity().getAddress().getPlace() != null ? new PlaceDTO(s.getFaculty().getUniversity().getAddress().getPlace().getId(),
										s.getFaculty().getUniversity().getAddress().getPlace().getName(),
										(s.getFaculty().getUniversity().getAddress().getPlace().getCountry() != null ? new CountryDTO(s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
												s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
												new ArrayList<PlaceDTO>(), 
												s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()) : null), 
										s.getFaculty().getUniversity().getAddress().getPlace().getActive()) : null),
								s.getFaculty().getUniversity().getAddress().getActive()) : null),
								(s.getFaculty().getUniversity().getRector() != null ? new TeacherDTO(s.getFaculty().getUniversity().getRector().getId(),
										(s.getFaculty().getUniversity().getRector() != null ? new RegisteredUserDTO(s.getFaculty().getUniversity().getRector().getUser().getUsername(), null,
												s.getFaculty().getUniversity().getRector().getUser().getEmail()) : null),
										s.getFaculty().getUniversity().getRector().getFirstName(),
										s.getFaculty().getUniversity().getRector().getLastName(),
										s.getFaculty().getUniversity().getRector().getUmcn(), 
										s.getFaculty().getUniversity().getRector().getBiography(),
										new ArrayList<TitleDTO>(), new ArrayList<TeacherOnRealizationDTO>(), 
										new ArrayList<EvaluationGradeDTO>(), null, null,
										s.getFaculty().getUniversity().getRector().getActive()) : null),
								s.getFaculty().getUniversity().getContactDetails(),
								s.getFaculty().getUniversity().getDescription(), 
								new ArrayList<FacultyDTO>(),
								s.getFaculty().getUniversity().getActive()) : null),
						s.getFaculty().getContactDetails(), s.getFaculty().getDescription(),
						new HashSet<DepartmentDTO>(),
						new ArrayList<StudyProgrammeDTO>(), 
						new ArrayList<StudentDTO>(),
						null, s.getFaculty().getActive()) : null),
				s.getActive()), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/exams")
	public ResponseEntity<Iterable<ExaminationDTO>> findAllExamsForStudent(@PathVariable("id") Long id, @PathVariable("studentOnYearId") Long studentOnYearId){
		Student s = service.findById(id).orElse(null);
	    StudentOnYear soy = studentOnYearService.findById(studentOnYearId).orElse(null);

	    if (s == null || soy == null) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }

	    List<ExaminationDTO> exams = soy.getExaminations().stream().map(e -> {
	        List<EvaluationDTO> evaluations = e.getEvaluations().stream()
	            .map(ev -> new EvaluationDTO(
	                    e.getId(),
	                    ev.getStartTime(),
	                    ev.getEndTime(),
	                    ev.getNumberOfPoints(),
	                    ev.getEvaluationType() != null
	                        ? new EvaluationTypeDTO(
	                            ev.getEvaluationType().getId(),
	                            ev.getEvaluationType().getName(),
	                            null,
	                            ev.getEvaluationType().getActive())
	                        : null,
	                    null, null, null, null,
	                    ev.getActive()))
	            .collect(Collectors.toList());

	        StudentDTO studentDTO = new StudentDTO(
	                s.getId(),
	                s.getUser() != null
	                    ? new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail())
	                    : null,
	                s.getFirstName(),
	                s.getLastName(),
	                s.getUmcn(),
	                null, null, null, null,
	                s.getActive());

	        StudentOnYearDTO soyDTO = new StudentOnYearDTO(
	                soy.getId(),
	                soy.getDateOfApplication(),
	                studentDTO,
	                soy.getIndexNumber(),
	                new YearOfStudyDTO(
	                        soy.getYearOfStudy().getId(),
	                        soy.getYearOfStudy().getYearOfStudy(),
	                        null,
	                        soy.getYearOfStudy().getActive()),
	                null, null,
	                soy.getActive());

	        return new ExaminationDTO(
	                e.getId(),
	                e.getNumberOfPoints(),
	                null,
	                evaluations,
	                soyDTO,
	                e.getActive());
	    }).collect(Collectors.toList());

	    return new ResponseEntity<Iterable<ExaminationDTO>>(exams, HttpStatus.OK);
	}
	


	@Override
	@PostMapping
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO t) {
		// TODO Auto-generated method stub
		HashSet<Role> studentRoles = (HashSet<Role>) t.getUser().getRoleNames().stream()
														.map(r -> 
																new Role(roleService.findByName(r).getId(),
																		roleService.findByName(r).getName(),
																		roleService.findByName(r).getActive()))
														.collect(Collectors.toSet());
		Student s = service.create(new Student(null, 
				(t.getUser() != null ? new RegisteredUser(t.getUser().getId(), t.getUser().getUsername(), t.getUser().getPassword(),
						t.getUser().getEmail(), null,
						null, 
						studentRoles, t.getUser().getActive()) : null),
				t.getFirstName(), t.getLastName(), t.getUmcn(), null, null, null,null, true));
		
		ArrayList<StudentDTO> students = new ArrayList<StudentDTO>();
		Set<StudentOnYearDTO> studentOnYears = new HashSet<StudentOnYearDTO>();
		ArrayList<SubjectAttendanceDTO> courses = new ArrayList<SubjectAttendanceDTO>();
		ArrayList<ExaminationDTO> exams = new ArrayList<ExaminationDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		ArrayList<EvaluationGradeDTO> grades = new ArrayList<EvaluationGradeDTO>();
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		
		if(s == null) {
			return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
		}
		
		for(StudentOnYear soy : s.getStudentsOnYear()) {
			for(Examination e : soy.getExaminations()) {
				for(Evaluation eval : e.getEvaluations()) {
					for(EvaluationGrade g : eval.getEvaluationGrades()) {
						grades.add(new EvaluationGradeDTO(g.getId(), 
								(g.getEvaluation() != null ? new EvaluationDTO(g.getEvaluation().getId(),
										g.getEvaluation().getStartTime(),
										g.getEvaluation().getEndTime(),
										g.getEvaluation().getNumberOfPoints(),
										(g.getEvaluation().getEvaluationType() != null ? new EvaluationTypeDTO(g.getEvaluation().getEvaluationType().getId(),
												g.getEvaluation().getEvaluationType().getName(),
												null,
												g.getEvaluation().getEvaluationType().getActive()) : null),
										(g.getEvaluation().getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(g.getEvaluation().getEvaluationInstrument().getId(),
												g.getEvaluation().getEvaluationInstrument().getName(),
											(g.getEvaluation().getEvaluationInstrument().getFile() != null ? new FileDTO(g.getEvaluation().getEvaluationInstrument().getFile().getId(),
													g.getEvaluation().getEvaluationInstrument().getFile().getUrl(),
													g.getEvaluation().getEvaluationInstrument().getFile().getDescription(),
													null, null, null, null, null, null,
													g.getEvaluation().getEvaluationInstrument().getFile().getDocument(),
													g.getEvaluation().getEvaluationInstrument().getFile().getActive()) : null), 
											g.getEvaluation().getEvaluationInstrument().getActive()) : null),
										(g.getEvaluation().getExamination() != null ? 
												new ExaminationDTO(g.getEvaluation().getExamination().getId(),
												g.getEvaluation().getExamination().getNumberOfPoints(),
												null, null, null, 
												g.getEvaluation().getExamination().getActive()) : null),
										(g.getEvaluation().getSubjectRealization() != null ? new SubjectRealizationDTO(g.getEvaluation().getSubjectRealization().getId(),
												null, null, null,
												(g.getEvaluation().getSubjectRealization().getSubject() != null ? new SubjectDTO(g.getEvaluation().getSubjectRealization().getSubject().getId(),
														g.getEvaluation().getSubjectRealization().getSubject().getName(),
														g.getEvaluation().getSubjectRealization().getSubject().getEcts(),
														g.getEvaluation().getSubjectRealization().getSubject().getActive()) : null),
												g.getEvaluation().getSubjectRealization().getActive()) : null),
										new ArrayList<EvaluationGradeDTO>(), g.getEvaluation().getActive()) : null),
								(g.getTeacher() != null ? new TeacherDTO(g.getTeacher().getId(), 
										(g.getTeacher().getUser() != null ? new RegisteredUserDTO(g.getTeacher().getUser().getUsername(), null,g.getTeacher().getUser().getEmail()) : null),
										g.getTeacher().getFirstName(), g.getTeacher().getLastName(), 
										g.getTeacher().getUmcn(), g.getTeacher().getBiography(), 
										null, null, grades, null, null,
										g.getTeacher().getActive()) : null),
								g.getDateTimeEvaluated(),
								g.getMark(), g.getActive()));
					}
					
					evaluations.add(new EvaluationDTO(eval.getId(), eval.getStartTime(), eval.getEndTime(),
							eval.getNumberOfPoints(),
							(eval.getEvaluationType() != null ? new EvaluationTypeDTO(eval.getEvaluationType().getId(),
									eval.getEvaluationType().getName(),
									new ArrayList<EvaluationDTO>(), eval.getEvaluationType().getActive()) : null),
							null,null, null, grades,
							eval.getActive()));
				}
				
				notes = (e.getNotes() != null ? (ArrayList<NoteDTO>) e.getNotes()
						.stream()
						.map(n -> new NoteDTO(n.getId(), n.getContent(), 
								null, n.getActive())).collect(Collectors.toList()) : null);
				
				exams.add(new ExaminationDTO(e.getId(), e.getNumberOfPoints(), notes, evaluations, null, e.getActive()));
			}
			
			studentOnYears.add(new StudentOnYearDTO(soy.getId(), soy.getDateOfApplication(), null, soy.getIndexNumber(),
					(soy.getYearOfStudy() != null ? new YearOfStudyDTO(soy.getYearOfStudy().getId(), soy.getYearOfStudy().getYearOfStudy(), 
							new ArrayList<SubjectDTO>(), soy.getYearOfStudy().getActive()) : null),
					exams, null, soy.getActive()));
		}
		
		return new ResponseEntity<StudentDTO>(new StudentDTO(s.getId(), 
				(s.getUser() != null ? new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()) : null),
				s.getFirstName(), s.getLastName(), s.getUmcn(), 
				(s.getAddress() != null ? new AddressDTO(s.getAddress().getId(), s.getAddress().getStreet(),
						s.getAddress().getHouseNumber(), 
						new PlaceDTO(s.getAddress().getPlace().getId(),
								s.getAddress().getPlace().getName(),
								new CountryDTO(s.getAddress().getPlace().getCountry().getId(),
										s.getAddress().getPlace().getCountry().getName(),
										new ArrayList<PlaceDTO>(),
										s.getAddress().getPlace().getCountry().getActive()),
								s.getAddress().getPlace().getActive()),
						s.getAddress().getActive()) : null),
				studentOnYears, courses, 
				(s.getFaculty() != null ? new FacultyDTO(s.getFaculty().getId(),
						s.getFaculty().getName(),
						(s.getFaculty().getAddress() != null ? new AddressDTO(s.getFaculty().getAddress().getId(),
								s.getFaculty().getAddress().getStreet(), s.getFaculty().getAddress().getHouseNumber(),
								new PlaceDTO(s.getFaculty().getAddress().getPlace().getId(),
										s.getFaculty().getAddress().getPlace().getName(),
										new CountryDTO(s.getFaculty().getAddress().getPlace().getCountry().getId(),
												s.getFaculty().getAddress().getPlace().getCountry().getName(),
												new ArrayList<PlaceDTO>(), 
												s.getFaculty().getAddress().getPlace().getCountry().getActive()), 
										s.getFaculty().getAddress().getPlace().getActive()),
								s.getFaculty().getAddress().getActive()) : null),
						(s.getFaculty().getHeadmaster() != null ? new TeacherDTO(s.getFaculty().getHeadmaster().getId(),
								new RegisteredUserDTO(s.getFaculty().getHeadmaster().getUser().getUsername(), null,
										s.getFaculty().getHeadmaster().getUser().getEmail()),
								s.getFaculty().getHeadmaster().getFirstName(),
								s.getFaculty().getHeadmaster().getLastName(),
								s.getFaculty().getHeadmaster().getUmcn(), 
								s.getFaculty().getHeadmaster().getBiography(),
								new ArrayList<TitleDTO>(), 
								new ArrayList<TeacherOnRealizationDTO>(), 
								new ArrayList<EvaluationGradeDTO>(), null, null,
								s.getFaculty().getHeadmaster().getActive()) : null), 
						(s.getFaculty().getUniversity() != null ? new UniversityDTO(s.getFaculty().getUniversity().getId(),
								s.getFaculty().getUniversity().getName(),
								s.getFaculty().getUniversity().getDateEstablished(),
								(s.getFaculty().getUniversity() != null ? new AddressDTO(s.getFaculty().getUniversity().getAddress().getId(),
										s.getFaculty().getUniversity().getAddress().getStreet(),
										s.getFaculty().getUniversity().getAddress().getHouseNumber(),
								(s.getFaculty().getUniversity().getAddress().getPlace() != null ? new PlaceDTO(s.getFaculty().getUniversity().getAddress().getPlace().getId(),
										s.getFaculty().getUniversity().getAddress().getPlace().getName(),
										(s.getFaculty().getUniversity().getAddress().getPlace().getCountry() != null ? new CountryDTO(s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
												s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
												new ArrayList<PlaceDTO>(), 
												s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()) : null), 
										s.getFaculty().getUniversity().getAddress().getPlace().getActive()) : null),
								s.getFaculty().getUniversity().getAddress().getActive()) : null),
								(s.getFaculty().getUniversity().getRector() != null ? new TeacherDTO(s.getFaculty().getUniversity().getRector().getId(),
										(s.getFaculty().getUniversity().getRector() != null ? new RegisteredUserDTO(s.getFaculty().getUniversity().getRector().getUser().getUsername(), null,
												s.getFaculty().getUniversity().getRector().getUser().getEmail()) : null),
										s.getFaculty().getUniversity().getRector().getFirstName(),
										s.getFaculty().getUniversity().getRector().getLastName(),
										s.getFaculty().getUniversity().getRector().getUmcn(), 
										s.getFaculty().getUniversity().getRector().getBiography(),
										new ArrayList<TitleDTO>(), new ArrayList<TeacherOnRealizationDTO>(), 
										new ArrayList<EvaluationGradeDTO>(), null, null,
										s.getFaculty().getUniversity().getRector().getActive()) : null),
								s.getFaculty().getUniversity().getContactDetails(),
								s.getFaculty().getUniversity().getDescription(), 
								new ArrayList<FacultyDTO>(),
								s.getFaculty().getUniversity().getActive()) : null),
						s.getFaculty().getContactDetails(), s.getFaculty().getDescription(),
						new HashSet<DepartmentDTO>(),
						new ArrayList<StudyProgrammeDTO>(), 
						new ArrayList<StudentDTO>(),
						null, s.getFaculty().getActive()) : null),
				s.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<StudentDTO> update(@RequestBody StudentDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Student s = service.findById(id).orElse(null);
		
		Set<StudentOnYearDTO> studentOnYearsDTO = new HashSet<StudentOnYearDTO>();
		ArrayList<SubjectAttendanceDTO> coursesDTO = new ArrayList<SubjectAttendanceDTO>();
		ArrayList<ExaminationDTO> examsDTO = new ArrayList<ExaminationDTO>();
		ArrayList<EvaluationDTO> evaluationsDTO = new ArrayList<EvaluationDTO>();
		ArrayList<EvaluationGradeDTO> gradesDTO = new ArrayList<EvaluationGradeDTO>();
		ArrayList<NoteDTO> notesDTO = new ArrayList<NoteDTO>();
		HashSet<RoleDTO> roleDTOs = new HashSet<RoleDTO>();
		
		
		Set<StudentOnYear> studentOnYears = new HashSet<StudentOnYear>();
		ArrayList<SubjectAttendance> courses = new ArrayList<SubjectAttendance>();
		ArrayList<Examination> exams = new ArrayList<Examination>();
		ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
		ArrayList<EvaluationGrade> grades = new ArrayList<EvaluationGrade>();
		ArrayList<Note> notes = new ArrayList<Note>();
		HashSet<Role> roles = new HashSet<Role>();
		
		if(s == null) {
			return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
		}
		
		for(StudentOnYear soy : s.getStudentsOnYear()) {
			for(Examination e : soy.getExaminations()) {
				for(Evaluation eval : e.getEvaluations()) {
					for(EvaluationGrade g : eval.getEvaluationGrades()) {
						gradesDTO.add(new EvaluationGradeDTO(g.getId(), 
								(g.getEvaluation() != null ? new EvaluationDTO(g.getEvaluation().getId(),
										g.getEvaluation().getStartTime(),
										g.getEvaluation().getEndTime(),
										g.getEvaluation().getNumberOfPoints(),
										(g.getEvaluation().getEvaluationType() != null ? new EvaluationTypeDTO(g.getEvaluation().getEvaluationType().getId(),
												g.getEvaluation().getEvaluationType().getName(),
												null,
												g.getEvaluation().getEvaluationType().getActive()) : null),
										(g.getEvaluation().getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(g.getEvaluation().getEvaluationInstrument().getId(),
												g.getEvaluation().getEvaluationInstrument().getName(),
											(g.getEvaluation().getEvaluationInstrument().getFile() != null ? new FileDTO(g.getEvaluation().getEvaluationInstrument().getFile().getId(),
													g.getEvaluation().getEvaluationInstrument().getFile().getUrl(),
													g.getEvaluation().getEvaluationInstrument().getFile().getDescription(),
													null, null, null, null, null, null,
													g.getEvaluation().getEvaluationInstrument().getFile().getDocument(),
													g.getEvaluation().getEvaluationInstrument().getFile().getActive()) : null), 
											g.getEvaluation().getEvaluationInstrument().getActive()) : null),
										(g.getEvaluation().getExamination() != null ? new ExaminationDTO(g.getEvaluation().getExamination().getId(),
												g.getEvaluation().getExamination().getNumberOfPoints(),
												null, null, null, 
												g.getEvaluation().getExamination().getActive()) : null),
										(g.getEvaluation().getSubjectRealization() != null ? new SubjectRealizationDTO(g.getEvaluation().getSubjectRealization().getId(),
												null, null, null,
												(g.getEvaluation().getSubjectRealization().getSubject() != null ? new SubjectDTO(g.getEvaluation().getSubjectRealization().getSubject().getId(),
														g.getEvaluation().getSubjectRealization().getSubject().getName(),
														g.getEvaluation().getSubjectRealization().getSubject().getEcts(),
														g.getEvaluation().getSubjectRealization().getSubject().getActive()) : null),
												g.getEvaluation().getSubjectRealization().getActive()) : null),
										new ArrayList<EvaluationGradeDTO>(), g.getEvaluation().getActive()) : null),
								(g.getTeacher() != null ? new TeacherDTO(g.getTeacher().getId(), 
										(g.getTeacher().getUser() != null ? new RegisteredUserDTO(g.getTeacher().getUser().getUsername(), null,g.getTeacher().getUser().getEmail()) : null),
										g.getTeacher().getFirstName(), g.getTeacher().getLastName(), 
										g.getTeacher().getUmcn(), g.getTeacher().getBiography(), 
										null, null, new ArrayList<EvaluationGradeDTO>(), null, null,
										g.getTeacher().getActive()) : null),
								g.getDateTimeEvaluated(),
								g.getMark(), g.getActive()));
					}
					
					evaluationsDTO.add(new EvaluationDTO(eval.getId(), eval.getStartTime(), eval.getEndTime(),
							eval.getNumberOfPoints(),
							new EvaluationTypeDTO(eval.getEvaluationType().getId(),
									eval.getEvaluationType().getName(),
									new ArrayList<EvaluationDTO>(), eval.getEvaluationType().getActive()),
							null,null, null, gradesDTO,
							eval.getEvaluationType().getActive()));
				}
				
				notesDTO = (ArrayList<NoteDTO>) e.getNotes()
						.stream()
						.map(n -> new NoteDTO(n.getId(), n.getContent(), 
								null, n.getActive())).collect(Collectors.toList());
				
				examsDTO.add(new ExaminationDTO(e.getId(), e.getNumberOfPoints(), notesDTO, evaluationsDTO, null, e.getActive()));
			}
			
			studentOnYearsDTO.add(new StudentOnYearDTO(soy.getId(), soy.getDateOfApplication(), null, soy.getIndexNumber(),
					(soy.getYearOfStudy() != null ? new YearOfStudyDTO(soy.getYearOfStudy().getId(), soy.getYearOfStudy().getYearOfStudy(), 
							new ArrayList<SubjectDTO>(), soy.getYearOfStudy().getActive()) : null),
					examsDTO, null, soy.getActive()));
		}
		
		for(StudentOnYearDTO soy : studentOnYearsDTO) {
			for(ExaminationDTO e : examsDTO) {
				for(EvaluationDTO eval : evaluationsDTO) {
					for(EvaluationGradeDTO g : gradesDTO) {
						grades.add(new EvaluationGrade(g.getId(), 
								new Evaluation(g.getEvaluation().getId(),
										g.getEvaluation().getStartTime(),
										g.getEvaluation().getEndTime(),
										g.getEvaluation().getNumberOfPoints(),
										new EvaluationType(g.getEvaluation().getEvaluationType().getId(),
												g.getEvaluation().getEvaluationType().getName(),
												evaluations,
												g.getEvaluation().getEvaluationType().getActive()),
										new EvaluationInstrument(g.getEvaluation().getEvaluationInstrument().getId(),
												g.getEvaluation().getEvaluationInstrument().getName(),
											evaluations, 
											new File(g.getEvaluation().getEvaluationInstrument().getFile().getId(),
													g.getEvaluation().getEvaluationInstrument().getFile().getUrl(),
													g.getEvaluation().getEvaluationInstrument().getFile().getDescription(),
													null, null, null, null,
													null,
													g.getEvaluation().getEvaluationInstrument().getFile().getDocument(),
													g.getEvaluation().getEvaluationInstrument().getFile().getActive()), 
											g.getEvaluation().getEvaluationInstrument().getActive()),
										new Examination(g.getEvaluation().getExamination().getId(),
												g.getEvaluation().getExamination().getNumberOfPoints(),
												null, evaluations, null, 
												g.getEvaluation().getExamination().getActive()),
										new SubjectRealization(),
										new ArrayList<EvaluationGrade>(), g.getEvaluation().getActive()),
								new Teacher(g.getTeacher().getId(), 
										new RegisteredUser(g.getTeacher().getUser().getId(),
												g.getTeacher().getUser().getUsername(),
												g.getTeacher().getUser().getPassword(),
												g.getTeacher().getUser().getEmail(),
												null, new ArrayList<Account>(),
												new HashSet<Role>(),
												g.getTeacher().getUser().getActive()),
										g.getTeacher().getFirstName(), g.getTeacher().getLastName(), 
										g.getTeacher().getUmcn(), g.getTeacher().getBiography(), 
										null, null, grades, null, null,
										g.getTeacher().getActive()),
								g.getDateTimeEvaluated(),
								g.getMark(), g.getActive()));
					}
					
					evaluations.add(new Evaluation(eval.getId(), eval.getStartTime(), eval.getEndTime(),
							eval.getNumberOfPoints(),
							new EvaluationType(eval.getEvaluationType().getId(),
									eval.getEvaluationType().getName(),
									new ArrayList<Evaluation>(), eval.getEvaluationType().getActive()),
							null,null, null, grades,
							eval.getEvaluationType().getActive()));
				}
				
				notes = (ArrayList<Note>) e.getNotes()
						.stream()
						.map(n -> new Note(n.getId(), n.getContent(), 
								null)).collect(Collectors.toList());
				
				exams.add(new Examination(e.getId(), e.getNumberOfPoints(), notes, evaluations, null, e.getActive()));
			}
			
			studentOnYears.add(new StudentOnYear(soy.getId(), soy.getDateOfApplication(),
					null, soy.getIndexNumber(),
					new YearOfStudy(soy.getYearOfStudy().getId(), soy.getYearOfStudy().getYearOfStudy(), 
							new ArrayList<Subject>(), soy.getYearOfStudy().getActive()),
					exams, null, soy.getActive()));
		}
		
		
		for(SubjectAttendance c : s.getSubjectAttendances()) {
			coursesDTO.add(new SubjectAttendanceDTO(c.getId(), c.getFinalGrade(), 
					new SubjectRealizationDTO(c.getSubjectRealization().getId(), evaluationsDTO,
							new HashSet<TeacherOnRealizationDTO>(),
							new ArrayList<AnnouncementDTO>(),
							new SubjectDTO(c.getSubjectRealization().getSubject().getId(),
									c.getSubjectRealization().getSubject().getName(),
									c.getSubjectRealization().getSubject().getEcts(), 
									c.getSubjectRealization().getSubject().getActive()),
							c.getSubjectRealization().getActive()),
					t, c.getActive()));
		}
		
		for(SubjectAttendanceDTO dto : coursesDTO) {
			courses.add(new SubjectAttendance(dto.getId(), dto.getFinalGrade(), 
					new SubjectRealization(dto.getSubjectRealization().getId(), evaluations,
							new HashSet<TeacherOnRealization>(),
							new ArrayList<Announcement>(),
							new Subject(dto.getSubjectRealization().getSubject().getId(),
									dto.getSubjectRealization().getSubject().getName(),
									dto.getSubjectRealization().getSubject().getEcts(), 
									dto.getSubjectRealization().getSubject().isCompulsory(),
									dto.getSubjectRealization().getSubject().getNumberOfClasses(),
									dto.getSubjectRealization().getSubject().getNumberOfPractices(),
									dto.getSubjectRealization().getSubject().getOtherTypesOfClasses(),
									dto.getSubjectRealization().getSubject().getResearchWork(),
									dto.getSubjectRealization().getSubject().getClassesLeft(),
									dto.getSubjectRealization().getSubject().getNumberOfSemesters(),
									new YearOfStudy(dto.getSubjectRealization().getSubject().getYearOfStudy().getId(),
											dto.getSubjectRealization().getSubject().getYearOfStudy().getYearOfStudy(),
											new ArrayList<Subject>(),
											dto.getSubjectRealization().getSubject().getYearOfStudy().getActive()),
									new ArrayList<Outcome>(),
									new ArrayList<SubjectRealization>(),
									null, 
									dto.getSubjectRealization().getSubject().getActive()),
							dto.getSubjectRealization().getActive()),
					new Student(dto.getStudent().getId(),
							new RegisteredUser(dto.getStudent().getUser().getId(),
									dto.getStudent().getUser().getUsername(),
									dto.getStudent().getUser().getPassword(),
									dto.getStudent().getUser().getEmail(),
									new ArrayList<ForumUser>(),
									new ArrayList<Account>(), 
									new HashSet<Role>(),
									dto.getStudent().getUser().getActive()),
							dto.getStudent().getFirstName(), dto.getStudent().getLastName(),
							dto.getStudent().getUmcn(),
							new Address(dto.getStudent().getAddress().getId(),
									dto.getStudent().getAddress().getStreet(),
									dto.getStudent().getAddress().getHouseNumber(),
									new Place(dto.getStudent().getAddress().getPlace().getId(),
											dto.getStudent().getAddress().getPlace().getName(),
											new Country(dto.getStudent().getAddress().getPlace().getCountry().getId(),
													dto.getStudent().getAddress().getPlace().getCountry().getName(),
													new ArrayList<Place>(), 
													dto.getStudent().getAddress().getPlace().getCountry().getActive()),
											dto.getStudent().getAddress().getPlace().getActive()),
									dto.getStudent().getAddress().getActive()),
							studentOnYears, courses, 
							new Faculty(dto.getStudent().getFaculty().getId(),
									dto.getStudent().getFaculty().getName(),
									new Address(dto.getStudent().getFaculty().getAddress().getId(),
											dto.getStudent().getFaculty().getAddress().getStreet(),
											dto.getStudent().getFaculty().getAddress().getHouseNumber(),
											new Place(dto.getStudent().getFaculty().getAddress().getPlace().getId(),
													dto.getStudent().getFaculty().getAddress().getPlace().getName(),
													new Country(dto.getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
															dto.getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
															new ArrayList<Place>(),
															dto.getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()),
													dto.getStudent().getFaculty().getAddress().getPlace().getActive()), 
											dto.getStudent().getFaculty().getAddress().getActive()),
									new Teacher(dto.getStudent().getFaculty().getHeadmaster().getId(),
											new RegisteredUser(dto.getStudent().getFaculty().getHeadmaster().getUser().getId(),
													dto.getStudent().getFaculty().getHeadmaster().getUser().getUsername(),
													dto.getStudent().getFaculty().getHeadmaster().getUser().getPassword(),
													dto.getStudent().getFaculty().getHeadmaster().getUser().getEmail(),
													new ArrayList<ForumUser>(),
													new ArrayList<Account>(),
													new HashSet<Role>(),
													dto.getStudent().getFaculty().getHeadmaster().getUser().getActive()),
											dto.getStudent().getFaculty().getHeadmaster().getFirstName(),
											dto.getStudent().getFaculty().getHeadmaster().getLastName(),
											dto.getStudent().getFaculty().getHeadmaster().getUmcn(),
											dto.getStudent().getFaculty().getHeadmaster().getBiography(),
											new ArrayList<Title>(),
											new ArrayList<TeacherOnRealization>(), 
											new ArrayList<EvaluationGrade>(), null, null,
											dto.getStudent().getFaculty().getHeadmaster().getActive()),
									new University(dto.getStudent().getFaculty().getUniversity().getId(),
											dto.getStudent().getFaculty().getUniversity().getName(),
											dto.getStudent().getFaculty().getUniversity().getDateEstablished(),
											new Address(dto.getStudent().getFaculty().getUniversity().getAddress().getId(),
													dto.getStudent().getFaculty().getUniversity().getAddress().getStreet(),
													dto.getStudent().getFaculty().getUniversity().getAddress().getHouseNumber(), null,
													dto.getStudent().getFaculty().getUniversity().getAddress().getActive()),
											new Teacher(dto.getStudent().getFaculty().getUniversity().getRector().getId(),
													new RegisteredUser(dto.getStudent().getFaculty().getUniversity().getRector().getUser().getId(),
															dto.getStudent().getFaculty().getUniversity().getRector().getUser().getUsername(),
															dto.getStudent().getFaculty().getUniversity().getRector().getUser().getPassword(),
															dto.getStudent().getFaculty().getUniversity().getRector().getUser().getEmail(), null, null, 
															new HashSet<Role>(),
															dto.getStudent().getFaculty().getUniversity().getRector().getUser().getActive()),
													dto.getStudent().getFaculty().getUniversity().getRector().getFirstName(),
													dto.getStudent().getFaculty().getUniversity().getRector().getLastName(),
													dto.getStudent().getFaculty().getUniversity().getRector().getUmcn(),
													dto.getStudent().getFaculty().getUniversity().getRector().getBiography(), null, null, 
													new ArrayList<EvaluationGrade>(), null, null,
													dto.getStudent().getFaculty().getUniversity().getRector().getActive()),
											dto.getStudent().getFaculty().getUniversity().getContactDetails(),
											dto.getStudent().getFaculty().getUniversity().getDescription(), null, 
											dto.getStudent().getFaculty().getUniversity().getActive()),
									dto.getStudent().getFaculty().getContactDetails(),
									dto.getStudent().getFaculty().getDescription(),
									new HashSet<Department>(),
									new ArrayList<StudyProgramme>(),
									new ArrayList<Student>(),
									new StudentAffairsOffice(dto.getStudent().getFaculty().getStudentAffairsOffice().getId(),
											new ArrayList<StudentServiceStaff>(), null, dto.getStudent().getFaculty().getStudentAffairsOffice().getActive()),
									dto.getStudent().getFaculty().getActive()),
							dto.getStudent().getActive()),
					dto.getActive()));
		}
		
		roleDTOs = (HashSet<RoleDTO>) s.getUser().getRoles()
										.stream()
										.map(r -> new RoleDTO(r.getId(), r.getName(), r.getActive()))
										.collect(Collectors.toSet());
		roles = (HashSet<Role>) roleDTOs
				.stream()
				.map(r -> new Role(r.getId(), r.getName(), r.getActive()))
				.collect(Collectors.toSet());
		
		
		
		s.setUser(new RegisteredUser(t.getUser().getId(),
						t.getUser().getUsername(),
						t.getUser().getPassword(),
						t.getUser().getEmail(),
						null, null, roles,
						t.getUser().getActive()));
		
		s.setFirstName(t.getFirstName());
		s.setLastName(t.getLastName());
		s.setUmcn(t.getUmcn());
		s.setAddress(new Address(t.getAddress().getId(),
				t.getAddress().getStreet(), t.getAddress().getHouseNumber(), 
				new Place(t.getAddress().getPlace().getId(),
						t.getAddress().getPlace().getName(),
						new Country(t.getAddress().getPlace().getCountry().getId(),
								t.getAddress().getPlace().getCountry().getName(),
								new ArrayList<Place>(),
								t.getAddress().getPlace().getCountry().getActive()),
						t.getAddress().getPlace().getActive()),
				t.getAddress().getActive()));
		s.setStudentsOnYear(studentOnYears);
		s.setSubjectAttendances(courses);
		s.setFaculty(new Faculty(t.getFaculty().getId(), t.getFaculty().getName(),
				null, null, null, null, null, null, null, null, null,
				t.getFaculty().getActive()));
		
		s = service.update(s);
		
		return new ResponseEntity<StudentDTO>(new StudentDTO(s.getId(), 
				new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()),
				s.getFirstName(), s.getLastName(), s.getUmcn(), 
				new AddressDTO(s.getAddress().getId(), s.getAddress().getStreet(),
						s.getAddress().getHouseNumber(), 
						new PlaceDTO(s.getAddress().getPlace().getId(),
								s.getAddress().getPlace().getName(),
								new CountryDTO(s.getAddress().getPlace().getCountry().getId(),
										s.getAddress().getPlace().getCountry().getName(),
										new ArrayList<PlaceDTO>(),
										s.getAddress().getPlace().getCountry().getActive()),
								s.getAddress().getPlace().getActive()),
						s.getAddress().getActive()),
				studentOnYearsDTO, coursesDTO, 
				new FacultyDTO(s.getFaculty().getId(),
						s.getFaculty().getName(),
						new AddressDTO(s.getFaculty().getAddress().getId(),
								s.getFaculty().getAddress().getStreet(), s.getFaculty().getAddress().getHouseNumber(),
								new PlaceDTO(s.getFaculty().getAddress().getPlace().getId(),
										s.getFaculty().getAddress().getPlace().getName(),
										new CountryDTO(s.getFaculty().getAddress().getPlace().getCountry().getId(),
												s.getFaculty().getAddress().getPlace().getCountry().getName(),
												new ArrayList<PlaceDTO>(), 
												s.getFaculty().getAddress().getPlace().getCountry().getActive()), 
										s.getFaculty().getAddress().getPlace().getActive()),
								s.getFaculty().getAddress().getActive()),
						new TeacherDTO(s.getFaculty().getHeadmaster().getId(),
								new RegisteredUserDTO(s.getFaculty().getHeadmaster().getUser().getUsername(), null,
										s.getFaculty().getHeadmaster().getUser().getEmail()),
								s.getFaculty().getHeadmaster().getFirstName(),
								s.getFaculty().getHeadmaster().getLastName(),
								s.getFaculty().getHeadmaster().getUmcn(), 
								s.getFaculty().getHeadmaster().getBiography(),
								new ArrayList<TitleDTO>(), 
								new ArrayList<TeacherOnRealizationDTO>(), 
								new ArrayList<EvaluationGradeDTO>(), null, null,
								s.getFaculty().getHeadmaster().getActive()), 
						new UniversityDTO(s.getFaculty().getUniversity().getId(),
								s.getFaculty().getUniversity().getName(),
								s.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(s.getFaculty().getUniversity().getAddress().getId(),
										s.getFaculty().getUniversity().getAddress().getStreet(),
										s.getFaculty().getUniversity().getAddress().getHouseNumber(),
								new PlaceDTO(s.getFaculty().getUniversity().getAddress().getPlace().getId(),
										s.getFaculty().getUniversity().getAddress().getPlace().getName(),
										new CountryDTO(s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
												s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
												new ArrayList<PlaceDTO>(), 
												s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()), 
										s.getFaculty().getUniversity().getAddress().getPlace().getActive()),
								s.getFaculty().getUniversity().getAddress().getActive()),
								new TeacherDTO(s.getFaculty().getUniversity().getRector().getId(),
										new RegisteredUserDTO(s.getFaculty().getUniversity().getRector().getUser().getUsername(), null,
												s.getFaculty().getUniversity().getRector().getUser().getEmail()),
										s.getFaculty().getUniversity().getRector().getFirstName(),
										s.getFaculty().getUniversity().getRector().getLastName(),
										s.getFaculty().getUniversity().getRector().getUmcn(), 
										s.getFaculty().getUniversity().getRector().getBiography(),
										new ArrayList<TitleDTO>(), new ArrayList<TeacherOnRealizationDTO>(), 
										new ArrayList<EvaluationGradeDTO>(), null, null,
										s.getFaculty().getUniversity().getRector().getActive()),
								s.getFaculty().getUniversity().getContactDetails(),
								s.getFaculty().getUniversity().getDescription(), 
								new ArrayList<FacultyDTO>(),
								s.getFaculty().getUniversity().getActive()),
						s.getFaculty().getContactDetails(), s.getFaculty().getDescription(),
						new HashSet<DepartmentDTO>(),
						new ArrayList<StudyProgrammeDTO>(), 
						new ArrayList<StudentDTO>(),
						null, s.getFaculty().getActive()),
				s.getActive()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StudentDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PatchMapping("/{id}")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<StudentDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
Student s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
		}
		
		Set<StudentOnYearDTO> studentOnYears = new HashSet<StudentOnYearDTO>();
		ArrayList<SubjectAttendanceDTO> courses = new ArrayList<SubjectAttendanceDTO>();
		ArrayList<ExaminationDTO> exams = new ArrayList<ExaminationDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		ArrayList<EvaluationGradeDTO> grades = new ArrayList<EvaluationGradeDTO>();
		ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
		
		
		for(StudentOnYear soy : s.getStudentsOnYear()) {
			for(Examination e : soy.getExaminations()) {
				for(Evaluation eval : e.getEvaluations()) {
					for(EvaluationGrade g : eval.getEvaluationGrades()) {
						grades.add(new EvaluationGradeDTO(g.getId(), 
								(g.getEvaluation() != null ? new EvaluationDTO(g.getEvaluation().getId(),
										g.getEvaluation().getStartTime(),
										g.getEvaluation().getEndTime(),
										g.getEvaluation().getNumberOfPoints(),
										(g.getEvaluation().getEvaluationType() != null ? new EvaluationTypeDTO(g.getEvaluation().getEvaluationType().getId(),
												g.getEvaluation().getEvaluationType().getName(),
												null,
												g.getEvaluation().getEvaluationType().getActive()) : null),
										(g.getEvaluation().getEvaluationInstrument() != null ? new EvaluationInstrumentDTO(g.getEvaluation().getEvaluationInstrument().getId(),
												g.getEvaluation().getEvaluationInstrument().getName(),
											(g.getEvaluation().getEvaluationInstrument().getFile() != null ? new FileDTO(g.getEvaluation().getEvaluationInstrument().getFile().getId(),
													g.getEvaluation().getEvaluationInstrument().getFile().getUrl(),
													g.getEvaluation().getEvaluationInstrument().getFile().getDescription(),
													null, null, null, null, null, null,
													g.getEvaluation().getEvaluationInstrument().getFile().getDocument(),
													g.getEvaluation().getEvaluationInstrument().getFile().getActive()) : null), 
											g.getEvaluation().getEvaluationInstrument().getActive()) : null),
										(g.getEvaluation().getExamination() != null ? 
												new ExaminationDTO(g.getEvaluation().getExamination().getId(),
												g.getEvaluation().getExamination().getNumberOfPoints(),
												null, null, null, 
												g.getEvaluation().getExamination().getActive()) : null),
										(g.getEvaluation().getSubjectRealization() != null ? new SubjectRealizationDTO(g.getEvaluation().getSubjectRealization().getId(),
												null, null, null,
												(g.getEvaluation().getSubjectRealization().getSubject() != null ? new SubjectDTO(g.getEvaluation().getSubjectRealization().getSubject().getId(),
														g.getEvaluation().getSubjectRealization().getSubject().getName(),
														g.getEvaluation().getSubjectRealization().getSubject().getEcts(),
														g.getEvaluation().getSubjectRealization().getSubject().getActive()) : null),
												g.getEvaluation().getSubjectRealization().getActive()) : null),
										new ArrayList<EvaluationGradeDTO>(), g.getEvaluation().getActive()) : null),
								(g.getTeacher() != null ? new TeacherDTO(g.getTeacher().getId(), 
										(g.getTeacher().getUser() != null ? new RegisteredUserDTO(g.getTeacher().getUser().getUsername(), null,g.getTeacher().getUser().getEmail()) : null),
										g.getTeacher().getFirstName(), g.getTeacher().getLastName(), 
										g.getTeacher().getUmcn(), g.getTeacher().getBiography(), 
										null, null, grades, null, null,
										g.getTeacher().getActive()) : null),
								g.getDateTimeEvaluated(),
								g.getMark(), g.getActive()));
					}
					
					evaluations.add(new EvaluationDTO(eval.getId(), eval.getStartTime(), eval.getEndTime(),
							eval.getNumberOfPoints(),
							(eval.getEvaluationType() != null ? new EvaluationTypeDTO(eval.getEvaluationType().getId(),
									eval.getEvaluationType().getName(),
									new ArrayList<EvaluationDTO>(), eval.getEvaluationType().getActive()) : null),
							null,null, null, grades,
							eval.getActive()));
				}
				
				notes = (e.getNotes() != null ? (ArrayList<NoteDTO>) e.getNotes()
						.stream()
						.map(n -> new NoteDTO(n.getId(), n.getContent(), 
								null, n.getActive())).collect(Collectors.toList()) : null);
				
				exams.add(new ExaminationDTO(e.getId(), e.getNumberOfPoints(), notes, evaluations, null, e.getActive()));
			}
			
			studentOnYears.add(new StudentOnYearDTO(soy.getId(), soy.getDateOfApplication(), null, soy.getIndexNumber(),
					(soy.getYearOfStudy() != null ? new YearOfStudyDTO(soy.getYearOfStudy().getId(), soy.getYearOfStudy().getYearOfStudy(), 
							new ArrayList<SubjectDTO>(), soy.getYearOfStudy().getActive()) : null),
					exams, null, soy.getActive()));
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<StudentDTO>(new StudentDTO(s.getId(), 
				new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()),
				s.getFirstName(), s.getLastName(), s.getUmcn(), 
				new AddressDTO(s.getAddress().getId(), s.getAddress().getStreet(),
						s.getAddress().getHouseNumber(), 
						new PlaceDTO(s.getAddress().getPlace().getId(),
								s.getAddress().getPlace().getName(),
								new CountryDTO(s.getAddress().getPlace().getCountry().getId(),
										s.getAddress().getPlace().getCountry().getName(),
										new ArrayList<PlaceDTO>(),
										s.getAddress().getPlace().getCountry().getActive()),
								s.getAddress().getPlace().getActive()),
						s.getAddress().getActive()),
				studentOnYears, courses, 
				new FacultyDTO(s.getFaculty().getId(),
						s.getFaculty().getName(),
						new AddressDTO(s.getFaculty().getAddress().getId(),
								s.getFaculty().getAddress().getStreet(), s.getFaculty().getAddress().getHouseNumber(),
								new PlaceDTO(s.getFaculty().getAddress().getPlace().getId(),
										s.getFaculty().getAddress().getPlace().getName(),
										new CountryDTO(s.getFaculty().getAddress().getPlace().getCountry().getId(),
												s.getFaculty().getAddress().getPlace().getCountry().getName(),
												new ArrayList<PlaceDTO>(), 
												s.getFaculty().getAddress().getPlace().getCountry().getActive()), 
										s.getFaculty().getAddress().getPlace().getActive()),
								s.getFaculty().getAddress().getActive()),
						new TeacherDTO(s.getFaculty().getHeadmaster().getId(),
								new RegisteredUserDTO(s.getFaculty().getHeadmaster().getUser().getUsername(), null,
										s.getFaculty().getHeadmaster().getUser().getEmail()),
								s.getFaculty().getHeadmaster().getFirstName(),
								s.getFaculty().getHeadmaster().getLastName(),
								s.getFaculty().getHeadmaster().getUmcn(), 
								s.getFaculty().getHeadmaster().getBiography(),
								new ArrayList<TitleDTO>(), 
								new ArrayList<TeacherOnRealizationDTO>(), 
								new ArrayList<EvaluationGradeDTO>(), null, null,
								s.getFaculty().getHeadmaster().getActive()), 
						new UniversityDTO(s.getFaculty().getUniversity().getId(),
								s.getFaculty().getUniversity().getName(),
								s.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(s.getFaculty().getUniversity().getAddress().getId(),
										s.getFaculty().getUniversity().getAddress().getStreet(),
										s.getFaculty().getUniversity().getAddress().getHouseNumber(),
								new PlaceDTO(s.getFaculty().getUniversity().getAddress().getPlace().getId(),
										s.getFaculty().getUniversity().getAddress().getPlace().getName(),
										new CountryDTO(s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
												s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
												new ArrayList<PlaceDTO>(), 
												s.getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()), 
										s.getFaculty().getUniversity().getAddress().getPlace().getActive()),
								s.getFaculty().getUniversity().getAddress().getActive()),
								new TeacherDTO(s.getFaculty().getUniversity().getRector().getId(),
										new RegisteredUserDTO(s.getFaculty().getUniversity().getRector().getUser().getUsername(), null,
												s.getFaculty().getUniversity().getRector().getUser().getEmail()),
										s.getFaculty().getUniversity().getRector().getFirstName(),
										s.getFaculty().getUniversity().getRector().getLastName(),
										s.getFaculty().getUniversity().getRector().getUmcn(), 
										s.getFaculty().getUniversity().getRector().getBiography(),
										new ArrayList<TitleDTO>(), new ArrayList<TeacherOnRealizationDTO>(), 
										new ArrayList<EvaluationGradeDTO>(), null, null,
										s.getFaculty().getUniversity().getRector().getActive()),
								s.getFaculty().getUniversity().getContactDetails(),
								s.getFaculty().getUniversity().getDescription(), 
								new ArrayList<FacultyDTO>(),
								s.getFaculty().getUniversity().getActive()),
						s.getFaculty().getContactDetails(), s.getFaculty().getDescription(),
						new HashSet<DepartmentDTO>(),
						new ArrayList<StudyProgrammeDTO>(), 
						new ArrayList<StudentDTO>(),
						null, s.getFaculty().getActive()),
				s.getActive()), HttpStatus.OK);
	}
	
	
}
