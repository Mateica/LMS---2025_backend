package main.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.plaf.BorderUIResource.TitledBorderUIResource;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.AddressDTO;
import main.dto.CountryDTO;
import main.dto.DepartmentDTO;
import main.dto.EducationGoalDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.FacultyDTO;
import main.dto.FileDTO;
import main.dto.OutcomeDTO;
import main.dto.PlaceDTO;
import main.dto.RegisteredUserDTO;
import main.dto.ScientificFieldDTO;
import main.dto.StudyProgrammeDTO;
import main.dto.SubjectDTO;
import main.dto.TeacherDTO;
import main.dto.TeachingMaterialDTO;
import main.dto.TitleDTO;
import main.dto.TitleTypeDTO;
import main.dto.UniversityDTO;
import main.dto.YearOfStudyDTO;
import main.model.Address;
import main.model.Country;
import main.model.EducationGoal;
import main.model.EvaluationType;
import main.model.Faculty;
import main.model.File;
import main.model.Outcome;
import main.model.Place;
import main.model.RegisteredUser;
import main.model.Subject;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeachingMaterial;
import main.model.Title;
import main.model.University;
import main.model.YearOfStudy;
import main.service.EducationGoalService;
import main.service.EvaluationTypeService;
import main.service.OutcomeService;
import main.service.SubjectService;

@RestController
@RequestMapping("/api/syllabi")
public class OutcomeController implements ControllerInterface<OutcomeDTO> {
	@Autowired
	private OutcomeService service;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private EducationGoalService educationGoalService;

	@Override
	@Secured({"ADMIN","TEACHER"})
	@GetMapping
	public ResponseEntity<Iterable<OutcomeDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<OutcomeDTO> syllabi = new ArrayList<OutcomeDTO>();
		ArrayList<TeacherDTO> authors = new ArrayList<TeacherDTO>();
		ArrayList<TitleDTO> teacherTitles = new ArrayList<TitleDTO>();
		HashSet<TeacherDTO> staffMembers = new HashSet<TeacherDTO>();
		
		for(Outcome o : service.findAll()) {
			for(Teacher t : o.getTeachingMaterial().getAuthors()) {
				for(Title title : t.getTitles()) {
					teacherTitles.add(new TitleDTO(title.getId(), title.getDateElected(), title.getDateAbolished(),
							new ScientificFieldDTO(),
							new TitleTypeDTO(title.getTitleType().getId(), 
									title.getTitleType().getName(),
									title.getTitleType().getActive()), title.getActive()));
				}
				
				for(Teacher teacher : o.getTeachingMaterial().getAuthors()) {
					for(Teacher staff : teacher.getDepartment().getStaff()) {
						staffMembers.add(new TeacherDTO(staff.getId(), 
								new RegisteredUserDTO(staff.getUser().getUsername(), null, staff.getUser().getEmail()),
								staff.getFirstName(), staff.getLastName(), staff.getUmcn(), staff.getBiography(),
								null, null,null,
								null, null, t.getActive()));
					}
				}
				
				authors.add(new TeacherDTO(t.getId(),
						new RegisteredUserDTO(t.getUser().getUsername(), null, t.getUser().getEmail()),
						t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(),
						teacherTitles, null,
						null, null, new DepartmentDTO(t.getDepartment().getId(), 
								t.getDepartment().getName(),
								t.getDepartment().getDescription(),
								new FacultyDTO(t.getDepartment().getFaculty().getId(),
										t.getDepartment().getFaculty().getName(),
										new AddressDTO(t.getDepartment().getFaculty().getAddress().getId(),
												t.getDepartment().getFaculty().getAddress().getStreet(),
												t.getDepartment().getFaculty().getAddress().getHouseNumber(),
												new PlaceDTO(t.getDepartment().getFaculty().getAddress().getPlace().getId(),
														t.getDepartment().getFaculty().getAddress().getPlace().getName(),
														new CountryDTO(t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getId(),
																t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getName(), null, 
																t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getActive()), 
														t.getDepartment().getFaculty().getAddress().getPlace().getActive()), 
												t.getDepartment().getFaculty().getAddress().getActive()),
										new TeacherDTO(t.getDepartment().getFaculty().getHeadmaster().getId(),
												new RegisteredUserDTO(t.getDepartment().getFaculty().getHeadmaster().getUser().getUsername(),
														null, t.getDepartment().getFaculty().getHeadmaster().getUser().getEmail()),
												t.getDepartment().getFaculty().getHeadmaster().getFirstName(),
												t.getDepartment().getFaculty().getHeadmaster().getLastName(),
												t.getDepartment().getFaculty().getHeadmaster().getUmcn(),
												t.getDepartment().getFaculty().getHeadmaster().getBiography(), new ArrayList<TitleDTO>(),
												null, null, null, null, t.getDepartment().getFaculty().getHeadmaster().getActive()),
										new UniversityDTO(t.getDepartment().getFaculty().getUniversity().getId(),
												t.getDepartment().getFaculty().getUniversity().getName(),
												t.getDepartment().getFaculty().getUniversity().getDateEstablished(),
												new AddressDTO(t.getDepartment().getFaculty().getUniversity().getAddress().getId(),
														t.getDepartment().getFaculty().getUniversity().getAddress().getStreet(),
														t.getDepartment().getFaculty().getUniversity().getAddress().getHouseNumber(),
														new PlaceDTO(t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getId(),
																t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getName(),
																new CountryDTO(t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getId(),
																		t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getName(), null, 
																		t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getActive()), 
																t.getDepartment().getFaculty().getAddress().getPlace().getActive()), 
														t.getDepartment().getFaculty().getUniversity().getAddress().getActive()),
												new TeacherDTO(),
												t.getDepartment().getFaculty().getUniversity().getContactDetails(), 
												t.getDepartment().getFaculty().getUniversity().getDescription(), null, 
												t.getDepartment().getFaculty().getUniversity().getActive()),
										t.getDepartment().getFaculty().getContactDetails(),
										t.getDepartment().getFaculty().getDescription(),
										null, null, 
										null, null, t.getDepartment().getFaculty().getActive()),
								staffMembers,
								new TeacherDTO(t.getDepartment().getChief().getId(),
										new RegisteredUserDTO(t.getDepartment().getChief().getUser().getUsername(),
												null, t.getDepartment().getChief().getUser().getEmail()),
										t.getDepartment().getChief().getFirstName(),
										t.getDepartment().getChief().getLastName(),
										t.getDepartment().getChief().getUmcn(),
										t.getDepartment().getChief().getBiography(),
										null, null, null, null, null, t.getDepartment().getChief().getActive()),
								new HashSet<StudyProgrammeDTO>(), 
								t.getDepartment().getActive()),
						t.getActive()));
			}
			syllabi.add(new OutcomeDTO(o.getId(), o.getDescription(), 
					new EducationGoalDTO(o.getEducationGoal().getId(), o.getEducationGoal().getDescription(),
							null, o.getEducationGoal().getActive()),
					new TeachingMaterialDTO(o.getTeachingMaterial().getId(), o.getTeachingMaterial().getName(),
							authors, o.getTeachingMaterial().getYearOfPublication(),
							new FileDTO(o.getTeachingMaterial().getFile().getId(), 
									o.getTeachingMaterial().getFile().getUrl(),
									o.getTeachingMaterial().getFile().getDescription(), null, null, null,
									null, null, null, null, o.getTeachingMaterial().getFile().getActive()),
							o.getTeachingMaterial().getActive()),
					new SubjectDTO(o.getSubject().getId(), o.getSubject().getName(), o.getSubject().getEcts(),
							o.getSubject().isCompulsory()),
					o.getActive()));
		}
		
		return new ResponseEntity<Iterable<OutcomeDTO>>(syllabi, HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/params")
	@Secured({"ADMIN","TEACHER"})
	public ResponseEntity<Page<OutcomeDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Outcome> syllabusPage = service.findAll(pageable);
	    
		ArrayList<TeacherDTO> authors = new ArrayList<TeacherDTO>();
		ArrayList<TitleDTO> teacherTitles = new ArrayList<TitleDTO>();
		HashSet<TeacherDTO> staffMembers = new HashSet<TeacherDTO>();
		List<OutcomeDTO> syllabusList = new ArrayList<OutcomeDTO>();
		
		for(Outcome o : service.findAll()) {
			for(Teacher t : o.getTeachingMaterial().getAuthors()) {
				for(Title title : t.getTitles()) {
					teacherTitles.add(new TitleDTO(title.getId(), title.getDateElected(), title.getDateAbolished(),
							new ScientificFieldDTO(),
							new TitleTypeDTO(title.getTitleType().getId(), 
									title.getTitleType().getName(),
									title.getTitleType().getActive()), title.getActive()));
				}
				
				for(Teacher teacher : o.getTeachingMaterial().getAuthors()) {
					for(Teacher staff : teacher.getDepartment().getStaff()) {
						staffMembers.add(new TeacherDTO(staff.getId(), 
								new RegisteredUserDTO(staff.getUser().getUsername(), null, staff.getUser().getEmail()),
								staff.getFirstName(), staff.getLastName(), staff.getUmcn(), staff.getBiography(),
								null, null,null,
								null, null, t.getActive()));
					}
				}
				
				authors.add(new TeacherDTO(t.getId(),
						new RegisteredUserDTO(t.getUser().getUsername(), null, t.getUser().getEmail()),
						t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(),
						teacherTitles, null,
						null, null, new DepartmentDTO(t.getDepartment().getId(), 
								t.getDepartment().getName(),
								t.getDepartment().getDescription(),
								new FacultyDTO(t.getDepartment().getFaculty().getId(),
										t.getDepartment().getFaculty().getName(),
										new AddressDTO(t.getDepartment().getFaculty().getAddress().getId(),
												t.getDepartment().getFaculty().getAddress().getStreet(),
												t.getDepartment().getFaculty().getAddress().getHouseNumber(),
												new PlaceDTO(t.getDepartment().getFaculty().getAddress().getPlace().getId(),
														t.getDepartment().getFaculty().getAddress().getPlace().getName(),
														new CountryDTO(t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getId(),
																t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getName(), null, 
																t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getActive()), 
														t.getDepartment().getFaculty().getAddress().getPlace().getActive()), 
												t.getDepartment().getFaculty().getAddress().getActive()),
										new TeacherDTO(t.getDepartment().getFaculty().getHeadmaster().getId(),
												new RegisteredUserDTO(t.getDepartment().getFaculty().getHeadmaster().getUser().getUsername(),
														null, t.getDepartment().getFaculty().getHeadmaster().getUser().getEmail()),
												t.getDepartment().getFaculty().getHeadmaster().getFirstName(),
												t.getDepartment().getFaculty().getHeadmaster().getLastName(),
												t.getDepartment().getFaculty().getHeadmaster().getUmcn(),
												t.getDepartment().getFaculty().getHeadmaster().getBiography(), new ArrayList<TitleDTO>(),
												null, null, null, null, t.getDepartment().getFaculty().getHeadmaster().getActive()),
										new UniversityDTO(t.getDepartment().getFaculty().getUniversity().getId(),
												t.getDepartment().getFaculty().getUniversity().getName(),
												t.getDepartment().getFaculty().getUniversity().getDateEstablished(),
												new AddressDTO(t.getDepartment().getFaculty().getUniversity().getAddress().getId(),
														t.getDepartment().getFaculty().getUniversity().getAddress().getStreet(),
														t.getDepartment().getFaculty().getUniversity().getAddress().getHouseNumber(),
														new PlaceDTO(t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getId(),
																t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getName(),
																new CountryDTO(t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getId(),
																		t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getName(), null, 
																		t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getActive()), 
																t.getDepartment().getFaculty().getAddress().getPlace().getActive()), 
														t.getDepartment().getFaculty().getUniversity().getAddress().getActive()),
												new TeacherDTO(),
												t.getDepartment().getFaculty().getUniversity().getContactDetails(), 
												t.getDepartment().getFaculty().getUniversity().getDescription(), null, 
												t.getDepartment().getFaculty().getUniversity().getActive()),
										t.getDepartment().getFaculty().getContactDetails(),
										t.getDepartment().getFaculty().getDescription(),
										null, null, 
										null, null, t.getDepartment().getFaculty().getActive()),
								staffMembers,
								new TeacherDTO(t.getDepartment().getChief().getId(),
										new RegisteredUserDTO(t.getDepartment().getChief().getUser().getUsername(),
												null, t.getDepartment().getChief().getUser().getEmail()),
										t.getDepartment().getChief().getFirstName(),
										t.getDepartment().getChief().getLastName(),
										t.getDepartment().getChief().getUmcn(),
										t.getDepartment().getChief().getBiography(),
										null, null, null, null, null, t.getDepartment().getChief().getActive()),
								new HashSet<StudyProgrammeDTO>(), 
								t.getDepartment().getActive()),
						t.getActive()));
			}
			
			for(Outcome s : syllabusPage) {
				syllabusList.add(new OutcomeDTO(s.getId(), s.getDescription(), 
						new EducationGoalDTO(s.getEducationGoal().getId(), s.getEducationGoal().getDescription(),
								null, s.getEducationGoal().getActive()),
						new TeachingMaterialDTO(s.getTeachingMaterial().getId(), s.getTeachingMaterial().getName(),
								authors, s.getTeachingMaterial().getYearOfPublication(),
								new FileDTO(s.getTeachingMaterial().getFile().getId(), 
										s.getTeachingMaterial().getFile().getUrl(),
										s.getTeachingMaterial().getFile().getDescription(), null, null, null,
										null, null, null, null, s.getTeachingMaterial().getFile().getActive()),
								s.getTeachingMaterial().getActive()),
						new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(), s.getSubject().getEcts(),
								s.getSubject().isCompulsory()),
						s.getActive()));
			}
		    
		}


	    Page<OutcomeDTO> resultPage = new PageImpl<OutcomeDTO>(syllabusList, pageable, syllabusPage.getTotalElements());
		return new ResponseEntity<Page<OutcomeDTO>>(resultPage, HttpStatus.OK);
	}
	    
	    
			
		

	@Override
	@Secured({"ADMIN","TEACHER"})
	@GetMapping("/{id}")
	public ResponseEntity<OutcomeDTO> findById(Long id) {
		// TODO Auto-generated method stub
		Outcome o = service.findById(id).orElse(null);
		
		if(o == null) {
			return new ResponseEntity<OutcomeDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<TeacherDTO> authors = new ArrayList<TeacherDTO>();
		ArrayList<TitleDTO> teacherTitles = new ArrayList<TitleDTO>();
		HashSet<TeacherDTO> staffMembers = new HashSet<TeacherDTO>();
		
		for(Teacher t : o.getTeachingMaterial().getAuthors()) {
			for(Title title : t.getTitles()) {
				teacherTitles.add(new TitleDTO(title.getId(), title.getDateElected(), title.getDateAbolished(),
						new ScientificFieldDTO(),
						new TitleTypeDTO(title.getTitleType().getId(), 
								title.getTitleType().getName(),
								title.getTitleType().getActive()), title.getActive()));
			}
			
			for(Teacher teacher : o.getTeachingMaterial().getAuthors()) {
				for(Teacher staff : teacher.getDepartment().getStaff()) {
					staffMembers.add(new TeacherDTO(staff.getId(), 
							new RegisteredUserDTO(staff.getUser().getUsername(), null, staff.getUser().getEmail()),
							staff.getFirstName(), staff.getLastName(), staff.getUmcn(), staff.getBiography(),
							null, null,null,
							null, null, t.getActive()));
					}
				}
			}
		return new ResponseEntity<OutcomeDTO>(new OutcomeDTO(o.getId(), o.getDescription(), 
				new EducationGoalDTO(o.getEducationGoal().getId(), o.getEducationGoal().getDescription(),
						null, o.getEducationGoal().getActive()),
				new TeachingMaterialDTO(o.getTeachingMaterial().getId(), o.getTeachingMaterial().getName(),
						authors, o.getTeachingMaterial().getYearOfPublication(),
						new FileDTO(o.getTeachingMaterial().getFile().getId(), 
								o.getTeachingMaterial().getFile().getUrl(),
								o.getTeachingMaterial().getFile().getDescription(), null, null, null,
								null, null, null, null, o.getTeachingMaterial().getFile().getActive()),
						o.getTeachingMaterial().getActive()),
				new SubjectDTO(o.getSubject().getId(), o.getSubject().getName(), o.getSubject().getEcts(),
						o.getSubject().isCompulsory()),
				o.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN"})
	@PostMapping
	public ResponseEntity<OutcomeDTO> create(OutcomeDTO t) {
		// TODO Auto-generated method stub
		Outcome o = service.create(new Outcome(null, t.getDescription(), 
				new EducationGoal(t.getEducationGoal().getId(), t.getEducationGoal().getDescription(),
						null, t.getEducationGoal().getActive()),
				new TeachingMaterial(t.getTeachingMaterial().getId(), t.getTeachingMaterial().getName(),
						new ArrayList<Teacher>(), t.getTeachingMaterial().getYearOfPublication(),
						new File(t.getTeachingMaterial().getFile().getId(), 
								t.getTeachingMaterial().getFile().getUrl(),
								t.getTeachingMaterial().getFile().getDescription(), null, null, null,
								null, null, null, null, t.getTeachingMaterial().getFile().getActive()),
						t.getTeachingMaterial().getActive()),
				new Subject(t.getSubject().getId(), 
						t.getSubject().getName(),
						t.getSubject().getEcts(),
						t.getSubject().isCompulsory(),
						t.getSubject().getNumberOfClasses(),
						t.getSubject().getNumberOfPractices(),
						t.getSubject().getOtherTypesOfClasses(),
						t.getSubject().getResearchWork(),
						t.getSubject().getClassesLeft(),
						t.getSubject().getNumberOfSemesters(),
						new YearOfStudy(t.getSubject().getYearOfStudy().getId(),
								t.getSubject().getYearOfStudy().getYearOfStudy(),
								new ArrayList<Subject>(), t.getSubject().getYearOfStudy().getActive()),
						new ArrayList<Outcome>(),
						new ArrayList<SubjectRealization>(),
						null, true),
				t.getActive()));
		
		if(o == null) {
			return new ResponseEntity<OutcomeDTO>(HttpStatus.BAD_REQUEST);
		}
		ArrayList<TeacherDTO> authors = new ArrayList<TeacherDTO>();
		ArrayList<TitleDTO> teacherTitles = new ArrayList<TitleDTO>();
		HashSet<TeacherDTO> staffMembers = new HashSet<TeacherDTO>();
		
		for(Teacher tt : o.getTeachingMaterial().getAuthors()) {
			for(Title title : tt.getTitles()) {
				teacherTitles.add(new TitleDTO(title.getId(), title.getDateElected(), title.getDateAbolished(),
						new ScientificFieldDTO(),
						new TitleTypeDTO(title.getTitleType().getId(), 
								title.getTitleType().getName(),
								title.getTitleType().getActive()), title.getActive()));
			}
			
			for(Teacher teacher : o.getTeachingMaterial().getAuthors()) {
				for(Teacher staff : teacher.getDepartment().getStaff()) {
					staffMembers.add(new TeacherDTO(staff.getId(), 
							new RegisteredUserDTO(staff.getUser().getUsername(), null, staff.getUser().getEmail()),
							staff.getFirstName(), staff.getLastName(), staff.getUmcn(), staff.getBiography(),
							null, null,null,
							null, null, t.getActive()));
					}
				}
			}
		return new ResponseEntity<OutcomeDTO>(new OutcomeDTO(o.getId(), o.getDescription(), 
				new EducationGoalDTO(o.getEducationGoal().getId(), o.getEducationGoal().getDescription(),
						null, o.getEducationGoal().getActive()),
				new TeachingMaterialDTO(o.getTeachingMaterial().getId(), o.getTeachingMaterial().getName(),
						authors, o.getTeachingMaterial().getYearOfPublication(),
						new FileDTO(o.getTeachingMaterial().getFile().getId(), 
								o.getTeachingMaterial().getFile().getUrl(),
								o.getTeachingMaterial().getFile().getDescription(), null, null, null,
								null, null, null, null, o.getTeachingMaterial().getFile().getActive()),
						o.getTeachingMaterial().getActive()),
				new SubjectDTO(o.getSubject().getId(), o.getSubject().getName(), o.getSubject().getEcts(),
						o.getSubject().isCompulsory()),
				o.getActive()), HttpStatus.CREATED);
	}

	@Override
	@Secured({"ADMIN", "TEACHER"})
	@PutMapping("/{id}")
	public ResponseEntity<OutcomeDTO> update(OutcomeDTO t, Long id) {
		// TODO Auto-generated method stub
		Outcome o = service.findById(id).orElse(null);
		
		if(o == null) {
			return new ResponseEntity<OutcomeDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<TeacherDTO> authors = new ArrayList<TeacherDTO>();
		ArrayList<TitleDTO> teacherTitles = new ArrayList<TitleDTO>();
		HashSet<TeacherDTO> staffMembers = new HashSet<TeacherDTO>();
		
		o.setDescription(t.getDescription());
		
		if(t.getSubject().getId() != null) {
			Subject subject = subjectService.findById(t.getSubject().getId()).get();
			
			o.setSubject(subject);
		}
		
		if(t.getEducationGoal().getId() != null) {
			EducationGoal educationGoal = educationGoalService.findById(t.getEducationGoal().getId()).get();
			
			educationGoal.setDescription(t.getEducationGoal().getDescription());
			
			o.setEducationGoal(educationGoal);
		}
		
		o.setActive(t.getActive());
		
		o = service.update(o)
;
		return new ResponseEntity<OutcomeDTO>(new OutcomeDTO(o.getId(), o.getDescription(), 
				new EducationGoalDTO(o.getEducationGoal().getId(), o.getEducationGoal().getDescription(),
						null, o.getEducationGoal().getActive()),
				new TeachingMaterialDTO(o.getTeachingMaterial().getId(), o.getTeachingMaterial().getName(),
						authors, o.getTeachingMaterial().getYearOfPublication(),
						new FileDTO(o.getTeachingMaterial().getFile().getId(), 
								o.getTeachingMaterial().getFile().getUrl(),
								o.getTeachingMaterial().getFile().getDescription(), null, null, null,
								null, null, null, null, o.getTeachingMaterial().getFile().getActive()),
						o.getTeachingMaterial().getActive()),
				new SubjectDTO(o.getSubject().getId(), o.getSubject().getName(), o.getSubject().getEcts(), o.getSubject().getActive()),
				o.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<OutcomeDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Secured({"ADMIN"})
	@PatchMapping("/{id}")
	public ResponseEntity<OutcomeDTO> softDelete(Long id) {
		// TODO Auto-generated method stub
		Outcome o = service.findById(id).orElse(null);
		
		if(o == null) {
			return new ResponseEntity<OutcomeDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<TeacherDTO> authors = new ArrayList<TeacherDTO>();
		ArrayList<TitleDTO> teacherTitles = new ArrayList<TitleDTO>();
		HashSet<TeacherDTO> staffMembers = new HashSet<TeacherDTO>();
		
		for(Teacher t : o.getTeachingMaterial().getAuthors()) {
			for(Title title : t.getTitles()) {
				teacherTitles.add(new TitleDTO(title.getId(), title.getDateElected(), title.getDateAbolished(),
						new ScientificFieldDTO(),
						new TitleTypeDTO(title.getTitleType().getId(), 
								title.getTitleType().getName(),
								title.getTitleType().getActive()), title.getActive()));
			}
			
			for(Teacher teacher : o.getTeachingMaterial().getAuthors()) {
				for(Teacher staff : teacher.getDepartment().getStaff()) {
					staffMembers.add(new TeacherDTO(staff.getId(), 
							new RegisteredUserDTO(staff.getUser().getUsername(), null, staff.getUser().getEmail()),
							staff.getFirstName(), staff.getLastName(), staff.getUmcn(), staff.getBiography(),
							null, null,null,
							null, null, t.getActive()));
					}
				}
			}
		
		service.softDelete(id);
		
		return new ResponseEntity<OutcomeDTO>(new OutcomeDTO(o.getId(), o.getDescription(), 
				new EducationGoalDTO(o.getEducationGoal().getId(), o.getEducationGoal().getDescription(),
						null, o.getEducationGoal().getActive()),
				new TeachingMaterialDTO(o.getTeachingMaterial().getId(), o.getTeachingMaterial().getName(),
						authors, o.getTeachingMaterial().getYearOfPublication(),
						new FileDTO(o.getTeachingMaterial().getFile().getId(), 
								o.getTeachingMaterial().getFile().getUrl(),
								o.getTeachingMaterial().getFile().getDescription(), null, null, null,
								null, null, null, null, o.getTeachingMaterial().getFile().getActive()),
						o.getTeachingMaterial().getActive()),
				new SubjectDTO(o.getSubject().getId(), o.getSubject().getName(), o.getSubject().getEcts(), o.getSubject().getActive()),
				o.getActive()), HttpStatus.OK);
	}
}
