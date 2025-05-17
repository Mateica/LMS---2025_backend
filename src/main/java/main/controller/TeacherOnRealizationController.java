package main.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.border.TitledBorder;

import org.apache.jena.tdb.store.Hash;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.CountryDTO;
import main.dto.OutcomeDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.dto.ScientificFieldDTO;
import main.dto.SubjectDTO;
import main.dto.SubjectRealizationDTO;
import main.dto.TeacherDTO;
import main.dto.TeacherOnRealizationDTO;
import main.dto.TeachingTypeDTO;
import main.dto.TitleDTO;
import main.dto.TitleTypeDTO;
import main.dto.YearOfStudyDTO;
import main.model.Account;
import main.model.Announcement;
import main.model.Country;
import main.model.Department;
import main.model.Evaluation;
import main.model.ForumUser;
import main.model.Outcome;
import main.model.RegisteredUser;
import main.model.Role;
import main.model.ScientificField;
import main.model.Subject;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.TeachingMaterial;
import main.model.TeachingType;
import main.model.Title;
import main.model.TitleType;
import main.model.YearOfStudy;
import main.service.TeacherOnRealizationService;

@RestController
@RequestMapping("/api/teacherOnRealization")
public class TeacherOnRealizationController implements ControllerInterface<TeacherOnRealizationDTO> {
	@Autowired
	private TeacherOnRealizationService service;

	@Override
	@GetMapping
	@Secured({"ADMIN"})
	public ResponseEntity<Iterable<TeacherOnRealizationDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<TeacherOnRealizationDTO> teachersOnRealization = new ArrayList<TeacherOnRealizationDTO>();
		
		for(TeacherOnRealization p : service.findAll()) {
			teachersOnRealization.add(new TeacherOnRealizationDTO(p.getId(),
						p.getNumberOfClasses(), 
						new TeacherDTO(p.getTeacher().getId(),
								new RegisteredUserDTO(p.getTeacher().getUser().getUsername(),
										null,
										p.getTeacher().getUser().getEmail()),
								p.getTeacher().getFirstName(),
								p.getTeacher().getLastName(),
								p.getTeacher().getUmcn(), p.getTeacher().getBiography(),
								null, new ArrayList<TeacherOnRealizationDTO>(), null, null, 
								p.getTeacher().getActive()),
						new SubjectRealizationDTO(p.getSubjectRealization().getId(),
								null, null, null,
								new SubjectDTO(p.getSubjectRealization().getSubject().getId(),
										p.getSubjectRealization().getSubject().getName(),
										p.getSubjectRealization().getSubject().getEcts(), 
										p.getSubjectRealization().getSubject().isCompulsory()), 
								p.getSubjectRealization().getActive()),
						new TeachingTypeDTO(p.getTeachingType().getId(),
								p.getTeachingType().getName(), p.getTeachingType().getActive()),
						p.getActive()));
		}
		
		
		return new ResponseEntity<Iterable<TeacherOnRealizationDTO>>(teachersOnRealization, HttpStatus.OK);
	}

	@Override
	@GetMapping("/params")
	@Secured({"ADMIN"})
	public ResponseEntity<Page<TeacherOnRealizationDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<TeacherOnRealization> teacherOnRealizationPage = service.findAll(pageable);

	    List<TeacherOnRealizationDTO> teacherOnRealizationDTOs = new ArrayList<TeacherOnRealizationDTO>();
	    
	    for(TeacherOnRealization t : teacherOnRealizationPage) {
	    	new TeacherOnRealizationDTO(t.getId(),
					t.getNumberOfClasses(), 
					new TeacherDTO(t.getTeacher().getId(),
							new RegisteredUserDTO(t.getTeacher().getUser().getUsername(),
									null,
									t.getTeacher().getUser().getEmail()),
							t.getTeacher().getFirstName(),
							t.getTeacher().getLastName(),
							t.getTeacher().getUmcn(), t.getTeacher().getBiography(),
							null, new ArrayList<TeacherOnRealizationDTO>(), null, null, 
							t.getTeacher().getActive()),
					new SubjectRealizationDTO(t.getSubjectRealization().getId(),
							null, null, null,
							new SubjectDTO(t.getSubjectRealization().getSubject().getId(),
									t.getSubjectRealization().getSubject().getName(),
									t.getSubjectRealization().getSubject().getEcts(), 
									t.getSubjectRealization().getSubject().isCompulsory()), 
							t.getSubjectRealization().getActive()),
					new TeachingTypeDTO(t.getTeachingType().getId(),
						t.getTeachingType().getName(), t.getTeachingType().getActive()),
					t.getActive());
	    }

	    Page<TeacherOnRealizationDTO> resultPage = new PageImpl<>(teacherOnRealizationDTOs, pageable, teacherOnRealizationPage.getTotalElements());

	    return new ResponseEntity<Page<TeacherOnRealizationDTO>>(resultPage, HttpStatus.OK);
	}
	
	@GetMapping("/teacherSubjects/{id}")
	@Secured({"ADMIN", "TEACHER"})
	public ResponseEntity<Iterable<TeacherOnRealizationDTO>> findAllTeacherSubjects(@PathVariable("id") Long id){
		ArrayList<TeacherOnRealizationDTO> teachersOnRealization = null;
		
		teachersOnRealization = (ArrayList<TeacherOnRealizationDTO>) service.findByTeacherId(id)
				.stream()
				.map(p -> 
				new TeacherOnRealizationDTO(p.getId(),
						p.getNumberOfClasses(), 
						new TeacherDTO(p.getTeacher().getId(),
								new RegisteredUserDTO(p.getTeacher().getUser().getUsername(),
										null,
										p.getTeacher().getUser().getEmail()),
								p.getTeacher().getFirstName(),
								p.getTeacher().getLastName(),
								p.getTeacher().getUmcn(), p.getTeacher().getBiography(),
								null, new ArrayList<TeacherOnRealizationDTO>(), null, null, 
								p.getTeacher().getActive()),
						new SubjectRealizationDTO(p.getSubjectRealization().getId(),
								null, null, null,
								new SubjectDTO(p.getSubjectRealization().getSubject().getId(),
										p.getSubjectRealization().getSubject().getName(),
										p.getSubjectRealization().getSubject().getEcts(), 
										p.getSubjectRealization().getSubject().isCompulsory()), 
								p.getSubjectRealization().getActive()),
						new TeachingTypeDTO(p.getTeachingType().getId(),
								p.getTeachingType().getName(), p.getTeachingType().getActive()),
						p.getActive()))
				.collect(Collectors.toList());
		
		return new ResponseEntity<Iterable<TeacherOnRealizationDTO>>(teachersOnRealization, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<TeacherOnRealizationDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		TeacherOnRealization p = service.findById(id).orElse(null);
		
		if(p == null) {
			return new ResponseEntity<TeacherOnRealizationDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<TeacherOnRealizationDTO>(new TeacherOnRealizationDTO(p.getId(),
						p.getNumberOfClasses(), 
						new TeacherDTO(p.getTeacher().getId(),
								new RegisteredUserDTO(p.getTeacher().getUser().getUsername(),
										null,
										p.getTeacher().getUser().getEmail()),
								p.getTeacher().getFirstName(),
								p.getTeacher().getLastName(),
								p.getTeacher().getUmcn(), p.getTeacher().getBiography(),
								null, new ArrayList<TeacherOnRealizationDTO>(), null, null, 
								p.getTeacher().getActive()),
						new SubjectRealizationDTO(p.getSubjectRealization().getId(),
								null, null, null,
								new SubjectDTO(p.getSubjectRealization().getSubject().getId(),
										p.getSubjectRealization().getSubject().getName(),
										p.getSubjectRealization().getSubject().getEcts(), 
										p.getSubjectRealization().getSubject().isCompulsory()), 
								p.getSubjectRealization().getActive()),
						new TeachingTypeDTO(p.getTeachingType().getId(),
								p.getTeachingType().getName(), p.getTeachingType().getActive()),
						p.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN"})
	public ResponseEntity<TeacherOnRealizationDTO> create(@RequestBody TeacherOnRealizationDTO t) {
		// TODO Auto-generated method stub
		TeacherOnRealization p = service.create(new TeacherOnRealization(null,
				t.getNumberOfClasses(), 
				new Teacher(t.getTeacher().getId(),
						new RegisteredUser(t.getTeacher().getUser().getId(),
								t.getTeacher().getUser().getUsername(),
								t.getTeacher().getUser().getPassword(),
								t.getTeacher().getUser().getEmail(), 
								new ArrayList<ForumUser>(),
								null,
								new HashSet<Role>(), true),
						t.getTeacher().getFirstName(),
						t.getTeacher().getLastName(),
						t.getTeacher().getUmcn(), t.getTeacher().getBiography(),
						null, new ArrayList<TeacherOnRealization>(), null, null, 
						t.getTeacher().getActive()),
				new SubjectRealization(t.getSubjectRealization().getId(),
						new ArrayList<Evaluation>(),
						new HashSet<TeacherOnRealization>(),
						new ArrayList<Announcement>(),
						new Subject(t.getSubjectRealization().getSubject().getId(), 
								t.getSubjectRealization().getSubject().getName(),
								t.getSubjectRealization().getSubject().getEcts(), 
								t.getSubjectRealization().getSubject().isCompulsory(),
								t.getSubjectRealization().getSubject().getNumberOfClasses(),
								t.getSubjectRealization().getSubject().getNumberOfPractices(),
								t.getSubjectRealization().getSubject().getOtherTypesOfClasses(), 
								t.getSubjectRealization().getSubject().getResearchWork(),
								t.getSubjectRealization().getSubject().getClassesLeft(),
								t.getSubjectRealization().getSubject().getNumberOfSemesters(),
								new YearOfStudy(t.getSubjectRealization().getSubject().getYearOfStudy().getId(),
										t.getSubjectRealization().getSubject().getYearOfStudy().getYearOfStudy(),
										new ArrayList<Subject>(),
										t.getSubjectRealization().getSubject().getYearOfStudy().getActive()),
								new ArrayList<Outcome>(),
								new ArrayList<SubjectRealization>(),
								new Subject(t.getSubjectRealization().getSubject().getPrerequisite().getId(),
										t.getSubjectRealization().getSubject().getPrerequisite().getName(),
										t.getSubjectRealization().getSubject().getPrerequisite().getEcts(),
										t.getSubjectRealization().getSubject().getPrerequisite().isCompulsory(),
										t.getSubjectRealization().getSubject().getPrerequisite().getNumberOfClasses(),
										t.getSubjectRealization().getSubject().getPrerequisite().getNumberOfPractices(), 
										t.getSubjectRealization().getSubject().getPrerequisite().getOtherTypesOfClasses(),
										t.getSubjectRealization().getSubject().getPrerequisite().getResearchWork(),
										t.getSubjectRealization().getSubject().getPrerequisite().getClassesLeft(),
										t.getSubjectRealization().getSubject().getPrerequisite().getNumberOfSemesters(),
										new YearOfStudy(),
										new ArrayList<Outcome>(),
										new ArrayList<SubjectRealization>(), null, true),
								t.getActive()), t.getSubjectRealization().getActive()), 
						null, t.getSubjectRealization().getActive()));
		
		if(p == null) {
			return new ResponseEntity<TeacherOnRealizationDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<TeacherOnRealizationDTO>(new TeacherOnRealizationDTO(p.getId(),
						p.getNumberOfClasses(), 
						new TeacherDTO(p.getTeacher().getId(),
								new RegisteredUserDTO(p.getTeacher().getUser().getUsername(),
										null,
										p.getTeacher().getUser().getEmail()),
								p.getTeacher().getFirstName(),
								p.getTeacher().getLastName(),
								p.getTeacher().getUmcn(), p.getTeacher().getBiography(),
								null, new ArrayList<TeacherOnRealizationDTO>(), null, null, 
								p.getTeacher().getActive()),
						new SubjectRealizationDTO(p.getSubjectRealization().getId(),
								null, null, null,
								new SubjectDTO(p.getSubjectRealization().getSubject().getId(),
										p.getSubjectRealization().getSubject().getName(),
										p.getSubjectRealization().getSubject().getEcts(), 
										p.getSubjectRealization().getSubject().isCompulsory()), 
								p.getSubjectRealization().getActive()), 
								null, p.getSubjectRealization().getActive()),
						HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<TeacherOnRealizationDTO> update(@RequestBody TeacherOnRealizationDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		TeacherOnRealization p = service.findById(id).orElse(null);
		
		if(p == null) {
			return new ResponseEntity<TeacherOnRealizationDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<Title> titles = new ArrayList<Title>();
		ArrayList<TitleDTO> titleDTOs = new ArrayList<TitleDTO>();
		
		for(TitleDTO dto : t.getTeacher().getTitles()) {
			titleDTOs.add(new TitleDTO(dto.getId(), dto.getDateElected(), dto.getDateAbolished(),
					new ScientificFieldDTO(dto.getScientificField().getId(), dto.getScientificField().getName(),
							new ArrayList<TitleDTO>(), dto.getScientificField().getActive()),
					new TitleTypeDTO(dto.getTitleType().getId(), dto.getTitleType().getName(), dto.getTitleType().getActive()),
					dto.getActive()));
			
			titles.add(new Title(dto.getId(), dto.getDateElected(), dto.getDateAbolished(),
					new ScientificField(dto.getScientificField().getId(), dto.getScientificField().getName(),
							new ArrayList<Title>(), dto.getScientificField().getActive()),
					new TitleType(dto.getTitleType().getId(), dto.getTitleType().getName(), dto.getTitleType().getActive()),
					dto.getActive()));
		}
		
		p.setId(t.getId());
		p.setNumberOfClasses(t.getNumberOfClasses());
		p.setTeacher(new Teacher(t.getTeacher().getId(),
				new RegisteredUser(id, t.getTeacher().getUser().getUsername(),
						t.getTeacher().getUser().getPassword(),
						t.getTeacher().getUser().getEmail(), new ArrayList<ForumUser>(),
						new ArrayList<Account>(), new HashSet<Role>(), t.getTeacher().getUser().getActive()),
				t.getTeacher().getFirstName(),
				t.getTeacher().getLastName(),
				t.getTeacher().getUmcn(), t.getTeacher().getBiography(),
				titles, new ArrayList<TeacherOnRealization>(),
				new TeachingMaterial(t.getTeacher().getTeachingMaterial().getId(),
						t.getTeacher().getTeachingMaterial().getName(),
						new ArrayList<Teacher>(), t.getTeacher().getTeachingMaterial().getYearOfPublication(), null, 
						t.getTeacher().getTeachingMaterial().getActive()),
				new Department(t.getTeacher().getDepartment().getId(), 
						t.getTeacher().getDepartment().getName(),
						t.getTeacher().getDepartment().getDescription(), null, null, null, null,
						t.getTeacher().getDepartment().getActive()), 
				t.getTeacher().getActive()));
		
		p = service.update(p);
		
				return new ResponseEntity<TeacherOnRealizationDTO>(new TeacherOnRealizationDTO(p.getId(),
						p.getNumberOfClasses(), 
						new TeacherDTO(p.getTeacher().getId(),
								new RegisteredUserDTO(p.getTeacher().getUser().getUsername(),
										null,
										p.getTeacher().getUser().getEmail()),
								p.getTeacher().getFirstName(),
								p.getTeacher().getLastName(),
								p.getTeacher().getUmcn(), p.getTeacher().getBiography(),
								null, new ArrayList<TeacherOnRealizationDTO>(), null, null, 
								p.getTeacher().getActive()),
						new SubjectRealizationDTO(p.getSubjectRealization().getId(),
								null, null, null,
								new SubjectDTO(p.getSubjectRealization().getSubject().getId(),
										p.getSubjectRealization().getSubject().getName(),
										p.getSubjectRealization().getSubject().getEcts(), 
										p.getSubjectRealization().getSubject().isCompulsory()), 
								p.getSubjectRealization().getActive()), 
								null, p.getSubjectRealization().getActive()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TeacherOnRealizationDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<TeacherOnRealizationDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		TeacherOnRealization p = service.findById(id).orElse(null);
		
		if(p == null) {
			return new ResponseEntity<TeacherOnRealizationDTO>(HttpStatus.BAD_REQUEST);
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<TeacherOnRealizationDTO>(new TeacherOnRealizationDTO(p.getId(),
				p.getNumberOfClasses(), 
				new TeacherDTO(p.getTeacher().getId(),
						new RegisteredUserDTO(p.getTeacher().getUser().getUsername(),
								null,
								p.getTeacher().getUser().getEmail()),
						p.getTeacher().getFirstName(),
						p.getTeacher().getLastName(),
						p.getTeacher().getUmcn(), p.getTeacher().getBiography(),
						null, new ArrayList<TeacherOnRealizationDTO>(), null, null, 
						p.getTeacher().getActive()),
				new SubjectRealizationDTO(p.getSubjectRealization().getId(),
						null, null, null,
						new SubjectDTO(p.getSubjectRealization().getSubject().getId(),
								p.getSubjectRealization().getSubject().getName(),
								p.getSubjectRealization().getSubject().getEcts(), 
								p.getSubjectRealization().getSubject().isCompulsory()), 
						p.getSubjectRealization().getActive()), 
						null, p.getSubjectRealization().getActive()), HttpStatus.OK);
	}
	
}
