package main.controller;

import java.util.ArrayList;
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
import org.springframework.http.HttpStatusCode;
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
import main.dto.DepartmentDTO;
import main.dto.FacultyDTO;
import main.dto.PlaceDTO;
import main.dto.StudentDTO;
import main.dto.StudyProgrammeDTO;
import main.dto.SubjectDTO;
import main.dto.TeacherDTO;
import main.dto.UniversityDTO;
import main.dto.YearOfStudyDTO;
import main.model.Place;
import main.model.StudyProgramme;
import main.service.StudyProgrammeService;

@RestController
@RequestMapping("/api/studyProgrammes")
public class StudyProgrammeController implements ControllerInterface<StudyProgrammeDTO> {
	@Autowired
	private StudyProgrammeService service;

	@Override
	@GetMapping
	@Secured({"USER","STAFF","STUDENT","TEACHER"})
	public ResponseEntity<Iterable<StudyProgrammeDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<StudyProgrammeDTO> studyProgrammes = new ArrayList<StudyProgrammeDTO>();
		
		for(StudyProgramme sp : service.findAll()) {
			studyProgrammes.add(new StudyProgrammeDTO(sp.getId(), sp.getName(),
					new FacultyDTO(sp.getFaculty().getId(), sp.getFaculty().getName(),
							new AddressDTO(sp.getFaculty().getAddress().getId(), sp.getFaculty().getAddress().getStreet(),
									sp.getFaculty().getAddress().getHouseNumber(), null, sp.getFaculty().getAddress().getActive()),
							new TeacherDTO(sp.getFaculty().getHeadmaster().getId(), null, sp.getFaculty().getHeadmaster().getFirstName(),
									sp.getFaculty().getHeadmaster().getLastName(),
									sp.getFaculty().getHeadmaster().getUmcn(), sp.getFaculty().getHeadmaster().getBiography(),
									null, null, null, null, null, sp.getFaculty().getHeadmaster().getActive()),
							new UniversityDTO(sp.getFaculty().getUniversity().getId(), 
									sp.getFaculty().getUniversity().getName(),
									sp.getFaculty().getUniversity().getDateEstablished(),
									new AddressDTO(sp.getFaculty().getUniversity().getAddress().getId(), 
											sp.getFaculty().getUniversity().getAddress().getStreet(),
											sp.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
											sp.getFaculty().getUniversity().getAddress().getActive()), null, 
									sp.getFaculty().getUniversity().getContactDetails(),
									sp.getFaculty().getUniversity().getDescription(),
									new ArrayList<FacultyDTO>(),
									sp.getFaculty().getUniversity().getActive()),
							sp.getFaculty().getContactDetails(),
							sp.getFaculty().getDescription(), 
							new HashSet<DepartmentDTO>(), 
							new ArrayList<StudyProgrammeDTO>(), new ArrayList<StudentDTO>(),
							null, null),
					new YearOfStudyDTO(sp.getYearOfStudy().getId(), sp.getYearOfStudy().getYearOfStudy(), 
							new ArrayList<SubjectDTO>(), sp.getYearOfStudy().getActive()),
					new TeacherDTO(sp.getTeacher().getId(), null, sp.getTeacher().getFirstName(),
							sp.getTeacher().getLastName(),
							sp.getTeacher().getUmcn(), sp.getTeacher().getBiography(),
							null, null, null, null, null, sp.getTeacher().getActive()),
					new DepartmentDTO(sp.getDepartment().getId(),
							sp.getDepartment().getName(),
							sp.getDepartment().getDescription(), null, new HashSet<TeacherDTO>(),
							new TeacherDTO(sp.getDepartment().getChief().getId(), null, 
									sp.getDepartment().getChief().getFirstName(),
									sp.getDepartment().getChief().getLastName(),
									sp.getDepartment().getChief().getUmcn(), 
									sp.getDepartment().getChief().getBiography(),
									null, null, null, null, null, sp.getDepartment().getChief().getActive()),
							new HashSet<StudyProgrammeDTO>(), sp.getDepartment().getChief().getActive()),
					sp.getActive()));
		}
		
		return new ResponseEntity<Iterable<StudyProgrammeDTO>>(studyProgrammes, HttpStatus.OK);
	}

	@Override
	@GetMapping("/params")
	@Secured({"USER","STAFF","STUDENT","TEACHER"})
	public ResponseEntity<Page<StudyProgrammeDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<StudyProgramme> studyProgrammePage = service.findAll(pageable);

	    List<StudyProgrammeDTO> studyProgrammeDTOs = studyProgrammePage.stream().map(sp -> 
	    new StudyProgrammeDTO(sp.getId(), sp.getName(),
				new FacultyDTO(sp.getFaculty().getId(), sp.getFaculty().getName(),
						new AddressDTO(sp.getFaculty().getAddress().getId(), sp.getFaculty().getAddress().getStreet(),
								sp.getFaculty().getAddress().getHouseNumber(), null, sp.getFaculty().getAddress().getActive()),
						new TeacherDTO(sp.getFaculty().getHeadmaster().getId(), null, sp.getFaculty().getHeadmaster().getFirstName(),
								sp.getFaculty().getHeadmaster().getLastName(),
								sp.getFaculty().getHeadmaster().getUmcn(), sp.getFaculty().getHeadmaster().getBiography(),
								null, null, null, null, null, sp.getFaculty().getHeadmaster().getActive()),
						new UniversityDTO(sp.getFaculty().getUniversity().getId(), 
								sp.getFaculty().getUniversity().getName(),
								sp.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(sp.getFaculty().getUniversity().getAddress().getId(), 
										sp.getFaculty().getUniversity().getAddress().getStreet(),
										sp.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
										sp.getFaculty().getUniversity().getAddress().getActive()), null, 
								sp.getFaculty().getUniversity().getContactDetails(),
								sp.getFaculty().getUniversity().getDescription(),
								new ArrayList<FacultyDTO>(),
								sp.getFaculty().getUniversity().getActive()),
						sp.getFaculty().getContactDetails(),
						sp.getFaculty().getDescription(), 
						new HashSet<DepartmentDTO>(), 
						new ArrayList<StudyProgrammeDTO>(), new ArrayList<StudentDTO>(),
						null, null),
				new YearOfStudyDTO(sp.getYearOfStudy().getId(), sp.getYearOfStudy().getYearOfStudy(), 
						new ArrayList<SubjectDTO>(), sp.getYearOfStudy().getActive()),
				new TeacherDTO(sp.getTeacher().getId(), null, sp.getTeacher().getFirstName(),
						sp.getTeacher().getLastName(),
						sp.getTeacher().getUmcn(), sp.getTeacher().getBiography(),
						null, null, null, null, null, sp.getTeacher().getActive()),
				new DepartmentDTO(sp.getDepartment().getId(),
						sp.getDepartment().getName(),
						sp.getDepartment().getDescription(), null, new HashSet<TeacherDTO>(),
						new TeacherDTO(sp.getDepartment().getChief().getId(), null, 
								sp.getDepartment().getChief().getFirstName(),
								sp.getDepartment().getChief().getLastName(),
								sp.getDepartment().getChief().getUmcn(), 
								sp.getDepartment().getChief().getBiography(),
								null, null, null, null, null, sp.getDepartment().getChief().getActive()),
						new HashSet<StudyProgrammeDTO>(), sp.getDepartment().getChief().getActive()),
				sp.getActive())
	    ).collect(Collectors.toList());

	    Page<StudyProgrammeDTO> resultPage = new PageImpl<>(studyProgrammeDTOs, pageable, studyProgrammePage.getTotalElements());

	    return new ResponseEntity<>(resultPage, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	@Secured({"USER","STAFF","STUDENT","TEACHER"})
	public ResponseEntity<Iterable<StudyProgrammeDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<StudyProgrammeDTO> studyProgrammes = new ArrayList<StudyProgrammeDTO>();
		
		for(StudyProgramme sp : service.findAllActive()) {
			studyProgrammes.add(new StudyProgrammeDTO(sp.getId(), sp.getName(),
					new FacultyDTO(sp.getFaculty().getId(), sp.getFaculty().getName(),
							new AddressDTO(sp.getFaculty().getAddress().getId(), sp.getFaculty().getAddress().getStreet(),
									sp.getFaculty().getAddress().getHouseNumber(), null, sp.getFaculty().getAddress().getActive()),
							new TeacherDTO(sp.getFaculty().getHeadmaster().getId(), null, sp.getFaculty().getHeadmaster().getFirstName(),
									sp.getFaculty().getHeadmaster().getLastName(),
									sp.getFaculty().getHeadmaster().getUmcn(), sp.getFaculty().getHeadmaster().getBiography(),
									null, null, null, null, null, sp.getFaculty().getHeadmaster().getActive()),
							new UniversityDTO(sp.getFaculty().getUniversity().getId(), 
									sp.getFaculty().getUniversity().getName(),
									sp.getFaculty().getUniversity().getDateEstablished(),
									new AddressDTO(sp.getFaculty().getUniversity().getAddress().getId(), 
											sp.getFaculty().getUniversity().getAddress().getStreet(),
											sp.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
											sp.getFaculty().getUniversity().getAddress().getActive()), null, 
									sp.getFaculty().getUniversity().getContactDetails(),
									sp.getFaculty().getUniversity().getDescription(),
									new ArrayList<FacultyDTO>(),
									sp.getFaculty().getUniversity().getActive()),
							sp.getFaculty().getContactDetails(),
							sp.getFaculty().getDescription(), 
							new HashSet<DepartmentDTO>(), 
							new ArrayList<StudyProgrammeDTO>(), new ArrayList<StudentDTO>(),
							null, null),
					new YearOfStudyDTO(sp.getYearOfStudy().getId(), sp.getYearOfStudy().getYearOfStudy(), 
							new ArrayList<SubjectDTO>(), sp.getYearOfStudy().getActive()),
					new TeacherDTO(sp.getTeacher().getId(), null, sp.getTeacher().getFirstName(),
							sp.getTeacher().getLastName(),
							sp.getTeacher().getUmcn(), sp.getTeacher().getBiography(),
							null, null, null, null, null, sp.getTeacher().getActive()),
					new DepartmentDTO(sp.getDepartment().getId(),
							sp.getDepartment().getName(),
							sp.getDepartment().getDescription(), null, new HashSet<TeacherDTO>(),
							new TeacherDTO(sp.getDepartment().getChief().getId(), null, 
									sp.getDepartment().getChief().getFirstName(),
									sp.getDepartment().getChief().getLastName(),
									sp.getDepartment().getChief().getUmcn(), 
									sp.getDepartment().getChief().getBiography(),
									null, null, null, null, null, sp.getDepartment().getChief().getActive()),
							new HashSet<StudyProgrammeDTO>(), sp.getDepartment().getChief().getActive()),
					sp.getActive()));
		}
		
		return new ResponseEntity<Iterable<StudyProgrammeDTO>>(studyProgrammes, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"USER","STAFF","STUDENT","TEACHER"})
	public ResponseEntity<StudyProgrammeDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudyProgramme sp = service.findById(id).orElse(null);
		
		if(sp == null) {
			return new ResponseEntity<StudyProgrammeDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<StudyProgrammeDTO>(new StudyProgrammeDTO(sp.getId(), sp.getName(),
				new FacultyDTO(sp.getFaculty().getId(), sp.getFaculty().getName(),
						new AddressDTO(sp.getFaculty().getAddress().getId(), sp.getFaculty().getAddress().getStreet(),
								sp.getFaculty().getAddress().getHouseNumber(), null, sp.getFaculty().getAddress().getActive()),
						new TeacherDTO(sp.getFaculty().getHeadmaster().getId(), null, sp.getFaculty().getHeadmaster().getFirstName(),
								sp.getFaculty().getHeadmaster().getLastName(),
								sp.getFaculty().getHeadmaster().getUmcn(), sp.getFaculty().getHeadmaster().getBiography(),
								null, null, null, null, null, sp.getFaculty().getHeadmaster().getActive()),
						new UniversityDTO(sp.getFaculty().getUniversity().getId(), 
								sp.getFaculty().getUniversity().getName(),
								sp.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(sp.getFaculty().getUniversity().getAddress().getId(), 
										sp.getFaculty().getUniversity().getAddress().getStreet(),
										sp.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
										sp.getFaculty().getUniversity().getAddress().getActive()), null, 
								sp.getFaculty().getUniversity().getContactDetails(),
								sp.getFaculty().getUniversity().getDescription(),
								new ArrayList<FacultyDTO>(),
								sp.getFaculty().getUniversity().getActive()),
						sp.getFaculty().getContactDetails(),
						sp.getFaculty().getDescription(), 
						new HashSet<DepartmentDTO>(), 
						new ArrayList<StudyProgrammeDTO>(), new ArrayList<StudentDTO>(),
						null, null),
				new YearOfStudyDTO(sp.getYearOfStudy().getId(), sp.getYearOfStudy().getYearOfStudy(), 
						new ArrayList<SubjectDTO>(), sp.getYearOfStudy().getActive()),
				new TeacherDTO(sp.getTeacher().getId(), null, sp.getTeacher().getFirstName(),
						sp.getTeacher().getLastName(),
						sp.getTeacher().getUmcn(), sp.getTeacher().getBiography(),
						null, null, null, null, null, sp.getTeacher().getActive()),
				new DepartmentDTO(sp.getDepartment().getId(),
						sp.getDepartment().getName(),
						sp.getDepartment().getDescription(), null, new HashSet<TeacherDTO>(),
						new TeacherDTO(sp.getDepartment().getChief().getId(), null, 
								sp.getDepartment().getChief().getFirstName(),
								sp.getDepartment().getChief().getLastName(),
								sp.getDepartment().getChief().getUmcn(), 
								sp.getDepartment().getChief().getBiography(),
								null, null, null, null, null, sp.getDepartment().getChief().getActive()),
						new HashSet<StudyProgrammeDTO>(), sp.getDepartment().getChief().getActive()),
				sp.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<StudyProgrammeDTO> create(@RequestBody StudyProgrammeDTO t) {
		// TODO Auto-generated method stub
		StudyProgramme sp = service.create(new StudyProgramme(null, t.getName(),
											service.findById(t.getId()).get().getFaculty(),
											service.findById(t.getId()).get().getYearOfStudy(),
											service.findById(t.getId()).get().getTeacher(),
											service.findById(t.getId()).get().getDepartment(), true));
		
		if(sp == null) {
			return new ResponseEntity<StudyProgrammeDTO>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<StudyProgrammeDTO>(new StudyProgrammeDTO(sp.getId(), sp.getName(),
				new FacultyDTO(sp.getFaculty().getId(), sp.getFaculty().getName(),
						new AddressDTO(sp.getFaculty().getAddress().getId(), sp.getFaculty().getAddress().getStreet(),
								sp.getFaculty().getAddress().getHouseNumber(), null, sp.getFaculty().getAddress().getActive()),
						new TeacherDTO(sp.getFaculty().getHeadmaster().getId(), null, sp.getFaculty().getHeadmaster().getFirstName(),
								sp.getFaculty().getHeadmaster().getLastName(),
								sp.getFaculty().getHeadmaster().getUmcn(), sp.getFaculty().getHeadmaster().getBiography(),
								null, null, null, null, null, sp.getFaculty().getHeadmaster().getActive()),
						new UniversityDTO(sp.getFaculty().getUniversity().getId(), 
								sp.getFaculty().getUniversity().getName(),
								sp.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(sp.getFaculty().getUniversity().getAddress().getId(), 
										sp.getFaculty().getUniversity().getAddress().getStreet(),
										sp.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
										sp.getFaculty().getUniversity().getAddress().getActive()), null, 
								sp.getFaculty().getUniversity().getContactDetails(),
								sp.getFaculty().getUniversity().getDescription(),
								new ArrayList<FacultyDTO>(),
								sp.getFaculty().getUniversity().getActive()),
						sp.getFaculty().getContactDetails(),
						sp.getFaculty().getDescription(), 
						new HashSet<DepartmentDTO>(), 
						new ArrayList<StudyProgrammeDTO>(), new ArrayList<StudentDTO>(),
						null, null),
				new YearOfStudyDTO(sp.getYearOfStudy().getId(), sp.getYearOfStudy().getYearOfStudy(), 
						new ArrayList<SubjectDTO>(), sp.getYearOfStudy().getActive()),
				new TeacherDTO(sp.getTeacher().getId(), null, sp.getTeacher().getFirstName(),
						sp.getTeacher().getLastName(),
						sp.getTeacher().getUmcn(), sp.getTeacher().getBiography(),
						null, null, null, null, null, sp.getTeacher().getActive()),
				new DepartmentDTO(sp.getDepartment().getId(),
						sp.getDepartment().getName(),
						sp.getDepartment().getDescription(), null, new HashSet<TeacherDTO>(),
						new TeacherDTO(sp.getDepartment().getChief().getId(), null, 
								sp.getDepartment().getChief().getFirstName(),
								sp.getDepartment().getChief().getLastName(),
								sp.getDepartment().getChief().getUmcn(), 
								sp.getDepartment().getChief().getBiography(),
								null, null, null, null, null, sp.getDepartment().getChief().getActive()),
						new HashSet<StudyProgrammeDTO>(), sp.getDepartment().getChief().getActive()),
				sp.getActive()),HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<StudyProgrammeDTO> update(@RequestBody StudyProgrammeDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudyProgramme sp = service.findById(id).orElse(null);
		
		if(sp == null) {
			return new ResponseEntity<StudyProgrammeDTO>(HttpStatus.NOT_FOUND);
		}
		
		sp.setId(t.getId());
		sp.setName(t.getName());
		sp.setFaculty(service.findById(t.getId()).get().getFaculty());
		sp.setYearOfStudy(service.findById(t.getId()).get().getYearOfStudy());
		sp.setTeacher(service.findById(t.getId()).get().getTeacher());
		sp.setDepartment(service.findById(t.getId()).get().getDepartment());
		sp.setActive(t.getActive());
		
		sp = service.update(sp);
		
		
		return new ResponseEntity<StudyProgrammeDTO>(new StudyProgrammeDTO(sp.getId(), sp.getName(),
				new FacultyDTO(sp.getFaculty().getId(), sp.getFaculty().getName(),
						new AddressDTO(sp.getFaculty().getAddress().getId(), sp.getFaculty().getAddress().getStreet(),
								sp.getFaculty().getAddress().getHouseNumber(), null, sp.getFaculty().getAddress().getActive()),
						new TeacherDTO(sp.getFaculty().getHeadmaster().getId(), null, sp.getFaculty().getHeadmaster().getFirstName(),
								sp.getFaculty().getHeadmaster().getLastName(),
								sp.getFaculty().getHeadmaster().getUmcn(), sp.getFaculty().getHeadmaster().getBiography(),
								null, null, null, null, null, sp.getFaculty().getHeadmaster().getActive()),
						new UniversityDTO(sp.getFaculty().getUniversity().getId(), 
								sp.getFaculty().getUniversity().getName(),
								sp.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(sp.getFaculty().getUniversity().getAddress().getId(), 
										sp.getFaculty().getUniversity().getAddress().getStreet(),
										sp.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
										sp.getFaculty().getUniversity().getAddress().getActive()), null, 
								sp.getFaculty().getUniversity().getContactDetails(),
								sp.getFaculty().getUniversity().getDescription(),
								new ArrayList<FacultyDTO>(),
								sp.getFaculty().getUniversity().getActive()),
						sp.getFaculty().getContactDetails(),
						sp.getFaculty().getDescription(), 
						new HashSet<DepartmentDTO>(), 
						new ArrayList<StudyProgrammeDTO>(), new ArrayList<StudentDTO>(),
						null, null),
				new YearOfStudyDTO(sp.getYearOfStudy().getId(), sp.getYearOfStudy().getYearOfStudy(), 
						new ArrayList<SubjectDTO>(), sp.getYearOfStudy().getActive()),
				new TeacherDTO(sp.getTeacher().getId(), null, sp.getTeacher().getFirstName(),
						sp.getTeacher().getLastName(),
						sp.getTeacher().getUmcn(), sp.getTeacher().getBiography(),
						null, null, null, null, null, sp.getTeacher().getActive()),
				new DepartmentDTO(sp.getDepartment().getId(),
						sp.getDepartment().getName(),
						sp.getDepartment().getDescription(), null, new HashSet<TeacherDTO>(),
						new TeacherDTO(sp.getDepartment().getChief().getId(), null, 
								sp.getDepartment().getChief().getFirstName(),
								sp.getDepartment().getChief().getLastName(),
								sp.getDepartment().getChief().getUmcn(), 
								sp.getDepartment().getChief().getBiography(),
								null, null, null, null, null, sp.getDepartment().getChief().getActive()),
						new HashSet<StudyProgrammeDTO>(), sp.getDepartment().getChief().getActive()),
				sp.getActive()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StudyProgrammeDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PatchMapping("/{id}")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<StudyProgrammeDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		StudyProgramme sp = service.findById(id).orElse(null);
		
		if(sp == null) {
			return new ResponseEntity<StudyProgrammeDTO>(HttpStatus.NOT_FOUND);
		}
		
		this.service.softDelete(id);
		
		return new ResponseEntity<StudyProgrammeDTO>(new StudyProgrammeDTO(sp.getId(), sp.getName(),
				new FacultyDTO(sp.getFaculty().getId(), sp.getFaculty().getName(),
						new AddressDTO(sp.getFaculty().getAddress().getId(), sp.getFaculty().getAddress().getStreet(),
								sp.getFaculty().getAddress().getHouseNumber(), null, sp.getFaculty().getAddress().getActive()),
						new TeacherDTO(sp.getFaculty().getHeadmaster().getId(), null, sp.getFaculty().getHeadmaster().getFirstName(),
								sp.getFaculty().getHeadmaster().getLastName(),
								sp.getFaculty().getHeadmaster().getUmcn(), sp.getFaculty().getHeadmaster().getBiography(),
								null, null, null, null, null, sp.getFaculty().getHeadmaster().getActive()),
						new UniversityDTO(sp.getFaculty().getUniversity().getId(), 
								sp.getFaculty().getUniversity().getName(),
								sp.getFaculty().getUniversity().getDateEstablished(),
								new AddressDTO(sp.getFaculty().getUniversity().getAddress().getId(), 
										sp.getFaculty().getUniversity().getAddress().getStreet(),
										sp.getFaculty().getUniversity().getAddress().getHouseNumber(), null,
										sp.getFaculty().getUniversity().getAddress().getActive()), null, 
								sp.getFaculty().getUniversity().getContactDetails(),
								sp.getFaculty().getUniversity().getDescription(),
								new ArrayList<FacultyDTO>(),
								sp.getFaculty().getUniversity().getActive()),
						sp.getFaculty().getContactDetails(),
						sp.getFaculty().getDescription(), 
						new HashSet<DepartmentDTO>(), 
						new ArrayList<StudyProgrammeDTO>(), new ArrayList<StudentDTO>(),
						null, null),
				new YearOfStudyDTO(sp.getYearOfStudy().getId(), sp.getYearOfStudy().getYearOfStudy(), 
						new ArrayList<SubjectDTO>(), sp.getYearOfStudy().getActive()),
				new TeacherDTO(sp.getTeacher().getId(), null, sp.getTeacher().getFirstName(),
						sp.getTeacher().getLastName(),
						sp.getTeacher().getUmcn(), sp.getTeacher().getBiography(),
						null, null, null, null, null, sp.getTeacher().getActive()),
				new DepartmentDTO(sp.getDepartment().getId(),
						sp.getDepartment().getName(),
						sp.getDepartment().getDescription(), null, new HashSet<TeacherDTO>(),
						new TeacherDTO(sp.getDepartment().getChief().getId(), null, 
								sp.getDepartment().getChief().getFirstName(),
								sp.getDepartment().getChief().getLastName(),
								sp.getDepartment().getChief().getUmcn(), 
								sp.getDepartment().getChief().getBiography(),
								null, null, null, null, null, sp.getDepartment().getChief().getActive()),
						new HashSet<StudyProgrammeDTO>(), sp.getDepartment().getChief().getActive()),
				sp.getActive()), HttpStatus.OK);
	}
	
}
