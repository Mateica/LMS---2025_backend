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
import main.model.University;
import main.service.EvaluationGradeService;
import main.service.RegisteredUserService;
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
	
	@Override
	@GetMapping
	@Secured({"ADMIN", "STAFF"})
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
	@Secured({"ADMIN", "STAFF"})
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
	@Secured({"ADMIN", "STAFF"})
	public ResponseEntity<Iterable<TeacherDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<TeacherDTO> teachers = new ArrayList<TeacherDTO>();
		ArrayList<TeacherOnRealizationDTO> teachersOnRealization = new ArrayList<TeacherOnRealizationDTO>();
		
		for(Teacher s : service.findAllActive()) {
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
	@GetMapping("/{id}")
	@Secured({"ADMIN", "STAFF"})
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
								t.getTeachingMaterial().getFile().getDescription(), null, null, null, null, null, null,
								t.getTeachingMaterial().getFile().getActive()),
						t.getActive()),
				new Department(t.getDepartment().getId(), t.getDepartment().getName(), t.getDepartment().getDescription(), null, null, null, null, null), true));
		
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
		
		s.getEvaluationGrades().stream().map(g -> new EvaluationGrade(g.getId(), null, null, null, g.getMark(), g.getActive()));
		
		
		s.setUser(userService.findById(t.getUser().getId()).get());
		s.setFirstName(t.getFirstName());
		s.setLastName(t.getLastName());
		s.setUmcn(t.getUmcn());
		s.setBiography(t.getBiography());
//		s.setTitles();
		s.setTeachersOnRealization(teacherOnRealizationService.findByTeacherId(id));
		s.setEvaluationGrades(grades);
//		s.setStudentAffairsOffice(officeService.findById(t.getStudentAffairsOffice().getId()).get());
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
	@PutMapping("/softDelete/{id}")
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
		
		return new ResponseEntity<TeacherDTO>(new TeacherDTO(s.getId(), 
				new RegisteredUserDTO(s.getUser().getUsername(), null, s.getUser().getEmail()),
				s.getFirstName(), s.getLastName(),  s.getUmcn(), s.getBiography(),
				new ArrayList<TitleDTO>(), teachersOnRealization, null, null, null, s.getActive()), HttpStatus.OK);
	}
}
