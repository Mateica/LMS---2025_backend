package main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import main.dto.EvaluationDTO;
import main.dto.ExaminationDTO;
import main.dto.FacultyDTO;
import main.dto.NoteDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentDTO;
import main.dto.StudentOnYearDTO;
import main.dto.SubjectDTO;
import main.dto.YearOfStudyDTO;
import main.model.Evaluation;
import main.model.Examination;
import main.model.Note;
import main.model.RegisteredUser;
import main.model.Student;
import main.model.StudentOnYear;
import main.model.YearOfStudy;
import main.service.StudentOnYearService;
import main.service.StudentService;

@RestController
@RequestMapping("/api/studentsOnYear")
public class StudentOnYearController implements ControllerInterface<StudentOnYearDTO> {
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentOnYearService service;

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
								new StudentOnYearDTO(e.getStudentOnYear().getId(),
										e.getStudentOnYear().getDateOfApplication(),
										new StudentDTO(e.getStudentOnYear().getStudent().getId(),
												new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
														null, e.getStudentOnYear().getStudent().getUser().getEmail()),
												e.getStudentOnYear().getStudent().getFirstName(),
												e.getStudentOnYear().getStudent().getLastName(),
												e.getStudentOnYear().getStudent().getUmcn(),
												null, null, null, null,
												e.getStudentOnYear().getStudent().getActive()),
										e.getStudentOnYear().getIndexNumber(),
										new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
												e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
												new ArrayList<SubjectDTO>(),
												e.getStudentOnYear().getYearOfStudy().getActive()),
										null, null,
										e.getStudentOnYear().getActive()),
								e.getActive()))
						.collect(Collectors.toList());
			
			studentsOnYear.add(new StudentOnYearDTO(s.getId(),
					s.getDateOfApplication(),
					new StudentDTO(s.getStudent().getId(),
							new RegisteredUserDTO(s.getStudent().getUser().getUsername(),
									null, s.getStudent().getUser().getEmail()),
							s.getStudent().getFirstName(),
							s.getStudent().getLastName(),
							s.getStudent().getUmcn(),
							new AddressDTO(s.getStudent().getAddress().getId(),
									s.getStudent().getAddress().getStreet(),
									s.getStudent().getAddress().getHouseNumber(),
									new PlaceDTO(s.getStudent().getAddress().getPlace().getId(),
											s.getStudent().getAddress().getPlace().getName(),
											new CountryDTO(s.getStudent().getAddress().getPlace().getCountry().getId(),
													s.getStudent().getAddress().getPlace().getCountry().getName(),
													null, s.getStudent().getAddress().getPlace().getCountry().getActive()),
											s.getStudent().getAddress().getPlace().getActive()),
									s.getStudent().getAddress().getActive()),
							null, null, 
							new FacultyDTO(s.getStudent().getFaculty().getId(),
									s.getStudent().getFaculty().getName(),
									new AddressDTO(s.getStudent().getFaculty().getAddress().getId(),
											s.getStudent().getFaculty().getAddress().getStreet(),
											s.getStudent().getFaculty().getAddress().getHouseNumber(),
											new PlaceDTO(s.getStudent().getFaculty().getAddress().getPlace().getId(),
													s.getStudent().getFaculty().getAddress().getPlace().getName(),
													new CountryDTO(s.getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
															s.getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
															null, s.getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()),
													s.getStudent().getFaculty().getAddress().getPlace().getActive()),
											s.getStudent().getFaculty().getAddress().getActive()), null, null, null, null, null, null, null, null, null),
							s.getStudent().getFaculty().getActive()),
					s.getIndexNumber(),
					new YearOfStudyDTO(s.getYearOfStudy().getId(),
							s.getYearOfStudy().getYearOfStudy(),
							new ArrayList<SubjectDTO>(),
							s.getYearOfStudy().getActive()),
					exams, null,
					s.getActive()));
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
								new StudentOnYearDTO(e.getStudentOnYear().getId(),
										e.getStudentOnYear().getDateOfApplication(),
										new StudentDTO(e.getStudentOnYear().getStudent().getId(),
												new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
														null, e.getStudentOnYear().getStudent().getUser().getEmail()),
												e.getStudentOnYear().getStudent().getFirstName(),
												e.getStudentOnYear().getStudent().getLastName(),
												e.getStudentOnYear().getStudent().getUmcn(),
												null, null, null, null,
												e.getStudentOnYear().getStudent().getActive()),
										e.getStudentOnYear().getIndexNumber(),
										new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
												e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
												new ArrayList<SubjectDTO>(),
												e.getStudentOnYear().getYearOfStudy().getActive()),
										null, null,
										e.getStudentOnYear().getActive()),
								e.getActive()))
						.collect(Collectors.toList());
			
			studentsOnYear.add(new StudentOnYearDTO(s.getId(),
					s.getDateOfApplication(),
					new StudentDTO(s.getStudent().getId(),
							new RegisteredUserDTO(s.getStudent().getUser().getUsername(),
									null, s.getStudent().getUser().getEmail()),
							s.getStudent().getFirstName(),
							s.getStudent().getLastName(),
							s.getStudent().getUmcn(),
							new AddressDTO(s.getStudent().getAddress().getId(),
									s.getStudent().getAddress().getStreet(),
									s.getStudent().getAddress().getHouseNumber(),
									new PlaceDTO(s.getStudent().getAddress().getPlace().getId(),
											s.getStudent().getAddress().getPlace().getName(),
											new CountryDTO(s.getStudent().getAddress().getPlace().getCountry().getId(),
													s.getStudent().getAddress().getPlace().getCountry().getName(),
													null, s.getStudent().getAddress().getPlace().getCountry().getActive()),
											s.getStudent().getAddress().getPlace().getActive()),
									s.getStudent().getAddress().getActive()),
							null, null, 
							new FacultyDTO(s.getStudent().getFaculty().getId(),
									s.getStudent().getFaculty().getName(),
									new AddressDTO(s.getStudent().getFaculty().getAddress().getId(),
											s.getStudent().getFaculty().getAddress().getStreet(),
											s.getStudent().getFaculty().getAddress().getHouseNumber(),
											new PlaceDTO(s.getStudent().getFaculty().getAddress().getPlace().getId(),
													s.getStudent().getFaculty().getAddress().getPlace().getName(),
													new CountryDTO(s.getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
															s.getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
															null, s.getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()),
													s.getStudent().getFaculty().getAddress().getPlace().getActive()),
											s.getStudent().getFaculty().getAddress().getActive()), null, null, null, null, null, null, null, null, null),
							s.getStudent().getFaculty().getActive()),
					s.getIndexNumber(),
					new YearOfStudyDTO(s.getYearOfStudy().getId(),
							s.getYearOfStudy().getYearOfStudy(),
							new ArrayList<SubjectDTO>(),
							s.getYearOfStudy().getActive()),
					exams, null,
					s.getActive()));
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
		return null;
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
						new StudentOnYearDTO(e.getStudentOnYear().getId(),
								e.getStudentOnYear().getDateOfApplication(),
								new StudentDTO(e.getStudentOnYear().getStudent().getId(),
										new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
												null, e.getStudentOnYear().getStudent().getUser().getEmail()),
										e.getStudentOnYear().getStudent().getFirstName(),
										e.getStudentOnYear().getStudent().getLastName(),
										e.getStudentOnYear().getStudent().getUmcn(),
										null, null, null, null,
										e.getStudentOnYear().getStudent().getActive()),
								e.getStudentOnYear().getIndexNumber(),
								new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
										e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
										new ArrayList<SubjectDTO>(),
										e.getStudentOnYear().getYearOfStudy().getActive()),
								null, null,
								e.getStudentOnYear().getActive()),
						e.getActive()))
				.collect(Collectors.toList());
		
		return new ResponseEntity<StudentOnYearDTO>(new StudentOnYearDTO(s.getId(),
					s.getDateOfApplication(),
					new StudentDTO(s.getStudent().getId(),
							new RegisteredUserDTO(s.getStudent().getUser().getUsername(),
									null, s.getStudent().getUser().getEmail()),
							s.getStudent().getFirstName(),
							s.getStudent().getLastName(),
							s.getStudent().getUmcn(),
							new AddressDTO(s.getStudent().getAddress().getId(),
									s.getStudent().getAddress().getStreet(),
									s.getStudent().getAddress().getHouseNumber(),
									new PlaceDTO(s.getStudent().getAddress().getPlace().getId(),
											s.getStudent().getAddress().getPlace().getName(),
											new CountryDTO(s.getStudent().getAddress().getPlace().getCountry().getId(),
													s.getStudent().getAddress().getPlace().getCountry().getName(),
													null, s.getStudent().getAddress().getPlace().getCountry().getActive()),
											s.getStudent().getAddress().getPlace().getActive()),
									s.getStudent().getAddress().getActive()),
							null, null, 
							new FacultyDTO(s.getStudent().getFaculty().getId(),
									s.getStudent().getFaculty().getName(),
									new AddressDTO(s.getStudent().getFaculty().getAddress().getId(),
											s.getStudent().getFaculty().getAddress().getStreet(),
											s.getStudent().getFaculty().getAddress().getHouseNumber(),
											new PlaceDTO(s.getStudent().getFaculty().getAddress().getPlace().getId(),
													s.getStudent().getFaculty().getAddress().getPlace().getName(),
													new CountryDTO(s.getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
															s.getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
															null, s.getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()),
													s.getStudent().getFaculty().getAddress().getPlace().getActive()),
											s.getStudent().getFaculty().getAddress().getActive()), null, null, null, null, null, null, null, null, null),
							s.getStudent().getFaculty().getActive()),
					s.getIndexNumber(),
					new YearOfStudyDTO(s.getYearOfStudy().getId(),
							s.getYearOfStudy().getYearOfStudy(),
							new ArrayList<SubjectDTO>(),
							s.getYearOfStudy().getActive()),
					exams, null,
					s.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<StudentOnYearDTO> create(@RequestBody StudentOnYearDTO t) {
		// TODO Auto-generated method stub
		StudentOnYear s = service.create(new StudentOnYear(null, t.getDateOfApplication(),
				new Student(null, null, null, null, null, null, null, null, null, null),
				t.getIndexNumber(), 
				new YearOfStudy(null, null, null, null),
				new ArrayList<Examination>(), null, true));
		
		if(s == null) {
			return new ResponseEntity<StudentOnYearDTO>(HttpStatus.NOT_FOUND);
		}
		
		List<ExaminationDTO> exams = (ArrayList<ExaminationDTO>) s.getExaminations()
				.stream()
				.map(e -> 
				new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
						new ArrayList<NoteDTO>(),
						new ArrayList<EvaluationDTO>(),
						new StudentOnYearDTO(e.getStudentOnYear().getId(),
								e.getStudentOnYear().getDateOfApplication(),
								new StudentDTO(e.getStudentOnYear().getStudent().getId(),
										new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
												null, e.getStudentOnYear().getStudent().getUser().getEmail()),
										e.getStudentOnYear().getStudent().getFirstName(),
										e.getStudentOnYear().getStudent().getLastName(),
										e.getStudentOnYear().getStudent().getUmcn(),
										null, null, null, null,
										e.getStudentOnYear().getStudent().getActive()),
								e.getStudentOnYear().getIndexNumber(),
								new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
										e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
										new ArrayList<SubjectDTO>(),
										e.getStudentOnYear().getYearOfStudy().getActive()),
								null, null,
								e.getStudentOnYear().getActive()),
						e.getActive()))
				.collect(Collectors.toList());
		
		
		return new ResponseEntity<StudentOnYearDTO>(new StudentOnYearDTO(s.getId(),
				s.getDateOfApplication(),
				new StudentDTO(s.getStudent().getId(),
						new RegisteredUserDTO(s.getStudent().getUser().getUsername(),
								null, s.getStudent().getUser().getEmail()),
						s.getStudent().getFirstName(),
						s.getStudent().getLastName(),
						s.getStudent().getUmcn(),
						new AddressDTO(s.getStudent().getAddress().getId(),
								s.getStudent().getAddress().getStreet(),
								s.getStudent().getAddress().getHouseNumber(),
								new PlaceDTO(s.getStudent().getAddress().getPlace().getId(),
										s.getStudent().getAddress().getPlace().getName(),
										new CountryDTO(s.getStudent().getAddress().getPlace().getCountry().getId(),
												s.getStudent().getAddress().getPlace().getCountry().getName(),
												null, s.getStudent().getAddress().getPlace().getCountry().getActive()),
										s.getStudent().getAddress().getPlace().getActive()),
								s.getStudent().getAddress().getActive()),
						null, null, 
						new FacultyDTO(s.getStudent().getFaculty().getId(),
								s.getStudent().getFaculty().getName(),
								new AddressDTO(s.getStudent().getFaculty().getAddress().getId(),
										s.getStudent().getFaculty().getAddress().getStreet(),
										s.getStudent().getFaculty().getAddress().getHouseNumber(),
										new PlaceDTO(s.getStudent().getFaculty().getAddress().getPlace().getId(),
												s.getStudent().getFaculty().getAddress().getPlace().getName(),
												new CountryDTO(s.getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
														s.getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
														null, s.getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()),
												s.getStudent().getFaculty().getAddress().getPlace().getActive()),
										s.getStudent().getFaculty().getAddress().getActive()), null, null, null, null, null, null, null, null, null),
						s.getStudent().getFaculty().getActive()),
				s.getIndexNumber(),
				new YearOfStudyDTO(s.getYearOfStudy().getId(),
						s.getYearOfStudy().getYearOfStudy(),
						new ArrayList<SubjectDTO>(),
						s.getYearOfStudy().getActive()),
				exams, null,
				s.getActive()), HttpStatus.CREATED);
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
		
		List<ExaminationDTO> examDTOs = (ArrayList<ExaminationDTO>) s.getExaminations()
				.stream()
				.map(e -> 
				new ExaminationDTO(e.getId(), e.getNumberOfPoints(),
						new ArrayList<NoteDTO>(),
						new ArrayList<EvaluationDTO>(),
						new StudentOnYearDTO(e.getStudentOnYear().getId(),
								e.getStudentOnYear().getDateOfApplication(),
								new StudentDTO(e.getStudentOnYear().getStudent().getId(),
										new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
												null, e.getStudentOnYear().getStudent().getUser().getEmail()),
										e.getStudentOnYear().getStudent().getFirstName(),
										e.getStudentOnYear().getStudent().getLastName(),
										e.getStudentOnYear().getStudent().getUmcn(),
										null, null, null, null,
										e.getStudentOnYear().getStudent().getActive()),
								e.getStudentOnYear().getIndexNumber(),
								new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
										e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
										new ArrayList<SubjectDTO>(),
										e.getStudentOnYear().getYearOfStudy().getActive()),
								null, null,
								e.getStudentOnYear().getActive()),
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
		s.setStudent();
		s.setIndexNumber(t.getIndexNumber());
		s.setYearOfStudy();
		s.setExaminations(exams);
		
		
		s = service.update(s);
		
		return new ResponseEntity<StudentOnYearDTO>(new StudentOnYearDTO(s.getId(),
					s.getDateOfApplication(),
					new StudentDTO(s.getStudent().getId(),
							new RegisteredUserDTO(s.getStudent().getUser().getUsername(),
									null, s.getStudent().getUser().getEmail()),
							s.getStudent().getFirstName(),
							s.getStudent().getLastName(),
							s.getStudent().getUmcn(),
							new AddressDTO(s.getStudent().getAddress().getId(),
									s.getStudent().getAddress().getStreet(),
									s.getStudent().getAddress().getHouseNumber(),
									new PlaceDTO(s.getStudent().getAddress().getPlace().getId(),
											s.getStudent().getAddress().getPlace().getName(),
											new CountryDTO(s.getStudent().getAddress().getPlace().getCountry().getId(),
													s.getStudent().getAddress().getPlace().getCountry().getName(),
													null, s.getStudent().getAddress().getPlace().getCountry().getActive()),
											s.getStudent().getAddress().getPlace().getActive()),
									s.getStudent().getAddress().getActive()),
							null, null, 
							new FacultyDTO(s.getStudent().getFaculty().getId(),
									s.getStudent().getFaculty().getName(),
									new AddressDTO(s.getStudent().getFaculty().getAddress().getId(),
											s.getStudent().getFaculty().getAddress().getStreet(),
											s.getStudent().getFaculty().getAddress().getHouseNumber(),
											new PlaceDTO(s.getStudent().getFaculty().getAddress().getPlace().getId(),
													s.getStudent().getFaculty().getAddress().getPlace().getName(),
													new CountryDTO(s.getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
															s.getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
															null, s.getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()),
													s.getStudent().getFaculty().getAddress().getPlace().getActive()),
											s.getStudent().getFaculty().getAddress().getActive()), null, null, null, null, null, null, null, null, null),
							s.getStudent().getFaculty().getActive()),
					s.getIndexNumber(),
					new YearOfStudyDTO(s.getYearOfStudy().getId(),
							s.getYearOfStudy().getYearOfStudy(),
							new ArrayList<SubjectDTO>(),
							s.getYearOfStudy().getActive()),
					examDTOs, null,
					s.getActive()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StudentOnYearDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
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
						new StudentOnYearDTO(e.getStudentOnYear().getId(),
								e.getStudentOnYear().getDateOfApplication(),
								new StudentDTO(e.getStudentOnYear().getStudent().getId(),
										new RegisteredUserDTO(e.getStudentOnYear().getStudent().getUser().getUsername(),
												null, e.getStudentOnYear().getStudent().getUser().getEmail()),
										e.getStudentOnYear().getStudent().getFirstName(),
										e.getStudentOnYear().getStudent().getLastName(),
										e.getStudentOnYear().getStudent().getUmcn(),
										null, null, null, null,
										e.getStudentOnYear().getStudent().getActive()),
								e.getStudentOnYear().getIndexNumber(),
								new YearOfStudyDTO(e.getStudentOnYear().getYearOfStudy().getId(),
										e.getStudentOnYear().getYearOfStudy().getYearOfStudy(),
										new ArrayList<SubjectDTO>(),
										e.getStudentOnYear().getYearOfStudy().getActive()),
								null, null,
								e.getStudentOnYear().getActive()),
						e.getActive()))
				.collect(Collectors.toList());
		
		service.softDelete(id);
		
		return new ResponseEntity<StudentOnYearDTO>(new StudentOnYearDTO(s.getId(),
					s.getDateOfApplication(),
					new StudentDTO(s.getStudent().getId(),
							new RegisteredUserDTO(s.getStudent().getUser().getUsername(),
									null, s.getStudent().getUser().getEmail()),
							s.getStudent().getFirstName(),
							s.getStudent().getLastName(),
							s.getStudent().getUmcn(),
							new AddressDTO(s.getStudent().getAddress().getId(),
									s.getStudent().getAddress().getStreet(),
									s.getStudent().getAddress().getHouseNumber(),
									new PlaceDTO(s.getStudent().getAddress().getPlace().getId(),
											s.getStudent().getAddress().getPlace().getName(),
											new CountryDTO(s.getStudent().getAddress().getPlace().getCountry().getId(),
													s.getStudent().getAddress().getPlace().getCountry().getName(),
													null, s.getStudent().getAddress().getPlace().getCountry().getActive()),
											s.getStudent().getAddress().getPlace().getActive()),
									s.getStudent().getAddress().getActive()),
							null, null, 
							new FacultyDTO(s.getStudent().getFaculty().getId(),
									s.getStudent().getFaculty().getName(),
									new AddressDTO(s.getStudent().getFaculty().getAddress().getId(),
											s.getStudent().getFaculty().getAddress().getStreet(),
											s.getStudent().getFaculty().getAddress().getHouseNumber(),
											new PlaceDTO(s.getStudent().getFaculty().getAddress().getPlace().getId(),
													s.getStudent().getFaculty().getAddress().getPlace().getName(),
													new CountryDTO(s.getStudent().getFaculty().getAddress().getPlace().getCountry().getId(),
															s.getStudent().getFaculty().getAddress().getPlace().getCountry().getName(),
															null, s.getStudent().getFaculty().getAddress().getPlace().getCountry().getActive()),
													s.getStudent().getFaculty().getAddress().getPlace().getActive()),
											s.getStudent().getFaculty().getAddress().getActive()), null, null, null, null, null, null, null, null, null),
							s.getStudent().getFaculty().getActive()),
					s.getIndexNumber(),
					new YearOfStudyDTO(s.getYearOfStudy().getId(),
							s.getYearOfStudy().getYearOfStudy(),
							new ArrayList<SubjectDTO>(),
							s.getYearOfStudy().getActive()),
					exams, null,
					s.getActive()), HttpStatus.OK);
	}
}
