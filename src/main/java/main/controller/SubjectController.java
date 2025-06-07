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

import main.dto.AnnouncementDTO;
import main.dto.EducationGoalDTO;
import main.dto.EvaluationDTO;
import main.dto.OutcomeDTO;
import main.dto.RoleDTO;
import main.dto.SubjectDTO;
import main.dto.SubjectRealizationDTO;
import main.dto.TeacherOnRealizationDTO;
import main.dto.TeachingMaterialDTO;
import main.dto.YearOfStudyDTO;
import main.model.Announcement;
import main.model.EducationGoal;
import main.model.Evaluation;
import main.model.File;
import main.model.Outcome;
import main.model.Role;
import main.model.Subject;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.TeachingMaterial;
import main.model.YearOfStudy;
import main.service.SubjectService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController implements ControllerInterface<SubjectDTO> {
	@Autowired
	private SubjectService service;

	@Override
	@GetMapping("")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Iterable<SubjectDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
		
		for(Subject s : service.findAll()) {
			if(s.getPrerequisite() != null) {
				subjects.add(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
						s.isCompulsory(),
						s.getNumberOfClasses(), s.getNumberOfPractices(),
						s.getOtherTypesOfClasses(), 
						s.getResearchWork(),
						s.getClassesLeft(),
						s.getNumberOfSemesters(),
						new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(),null, s.getYearOfStudy().getActive()),
						new ArrayList<OutcomeDTO>(),
						new ArrayList<SubjectRealizationDTO>(),
						new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),s.getPrerequisite().getEcts(),
								s.getPrerequisite().getActive()),
						s.getActive()));
			}else {
				subjects.add(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
						s.isCompulsory(), s.getNumberOfClasses(), s.getNumberOfPractices(),
						s.getOtherTypesOfClasses(), 
						s.getResearchWork(),
						s.getClassesLeft(),
						s.getNumberOfSemesters(),
						new YearOfStudyDTO(),
						new ArrayList<OutcomeDTO>(),
						new ArrayList<SubjectRealizationDTO>(),
						new SubjectDTO(),
						s.getActive()));
			}
		}
		
		return new ResponseEntity<Iterable<SubjectDTO>>(subjects, HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/params")
	@Secured({"ADMIN"})
	public ResponseEntity<Page<SubjectDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Subject> subjectPage = service.findAll(pageable);

	    List<SubjectDTO> subjectDTOs = subjectPage.stream().map(s ->
	    new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
				s.isCompulsory(),
				s.getNumberOfClasses(), s.getNumberOfPractices(),
				s.getOtherTypesOfClasses(), 
				s.getResearchWork(),
				s.getClassesLeft(),
				s.getNumberOfSemesters(),
				new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(),null, s.getYearOfStudy().getActive()),
				new ArrayList<OutcomeDTO>(),
				new ArrayList<SubjectRealizationDTO>(),
				new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),s.getPrerequisite().getEcts(),
						s.getPrerequisite().getActive()),
				s.getActive()))
	    		.collect(Collectors.toList());

	    Page<SubjectDTO> resultPage = new PageImpl<SubjectDTO>(subjectDTOs, pageable, subjectPage.getTotalElements());

	    return new ResponseEntity<Page<SubjectDTO>>(resultPage, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Iterable<SubjectDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
		
		for(Subject s : service.findAllActive()) {
			if(s.getPrerequisite() != null) {
				subjects.add(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
						s.isCompulsory(),
						s.getNumberOfClasses(), s.getNumberOfPractices(),
						s.getOtherTypesOfClasses(), 
						s.getResearchWork(),
						s.getClassesLeft(),
						s.getNumberOfSemesters(),
						new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(),null, s.getYearOfStudy().getActive()),
						new ArrayList<OutcomeDTO>(),
						new ArrayList<SubjectRealizationDTO>(),
						new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),s.getPrerequisite().getEcts(),
								s.getPrerequisite().getActive()),
						s.getActive()));
			}else {
				subjects.add(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
						s.isCompulsory(), s.getNumberOfClasses(), s.getNumberOfPractices(),
						s.getOtherTypesOfClasses(), 
						s.getResearchWork(),
						s.getClassesLeft(),
						s.getNumberOfSemesters(),
						new YearOfStudyDTO(),
						new ArrayList<OutcomeDTO>(),
						new ArrayList<SubjectRealizationDTO>(),
						new SubjectDTO(),
						s.getActive()));
			}
		}
		
		return new ResponseEntity<Iterable<SubjectDTO>>(subjects, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<SubjectDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Subject s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<SubjectDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<SubjectDTO>(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
				s.isCompulsory(),
				s.getNumberOfClasses(), s.getNumberOfPractices(),
				s.getOtherTypesOfClasses(), 
				s.getResearchWork(),
				s.getClassesLeft(),
				s.getNumberOfSemesters(),
				new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(),null, s.getYearOfStudy().getActive()),
				new ArrayList<OutcomeDTO>(),
				new ArrayList<SubjectRealizationDTO>(),
				new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),s.getPrerequisite().getEcts(),
						s.getPrerequisite().getActive()),
				s.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping("")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<SubjectDTO> create(@RequestBody SubjectDTO t) {
		// TODO Auto-generated method stub
		Subject s = service.create(new Subject(t.getId(), t.getName(),t.getEcts(), 
				t.isCompulsory(),
				t.getNumberOfClasses(), t.getNumberOfPractices(),
				t.getOtherTypesOfClasses(), 
				t.getResearchWork(),
				t.getClassesLeft(),
				t.getNumberOfSemesters(),
				new YearOfStudy(t.getYearOfStudy().getId(), t.getYearOfStudy().getYearOfStudy(),new ArrayList<Subject>(), t.getYearOfStudy().getActive()),
				new ArrayList<Outcome>(),
				new ArrayList<SubjectRealization>(),
				new Subject(t.getPrerequisite().getId(), t.getPrerequisite().getName(),t.getPrerequisite().getEcts(),
						t.getPrerequisite().isCompulsory(), t.getPrerequisite().getNumberOfClasses(),
						t.getPrerequisite().getNumberOfPractices(), t.getPrerequisite().getOtherTypesOfClasses(),
						t.getPrerequisite().getResearchWork(), t.getPrerequisite().getClassesLeft(), t.getPrerequisite().getNumberOfSemesters(),
						new YearOfStudy(), new ArrayList<Outcome>(), new ArrayList<SubjectRealization>(), null, true),
				t.getActive()));
		
		if(s == null) {
			return new ResponseEntity<SubjectDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<SubjectDTO>(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
				s.isCompulsory(),
				s.getNumberOfClasses(), s.getNumberOfPractices(),
				s.getOtherTypesOfClasses(), 
				s.getResearchWork(),
				s.getClassesLeft(),
				s.getNumberOfSemesters(),
				new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(),null, s.getYearOfStudy().getActive()),
				new ArrayList<OutcomeDTO>(),
				new ArrayList<SubjectRealizationDTO>(),
				new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),s.getPrerequisite().getEcts(),
						s.getPrerequisite().getActive()),
				s.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<SubjectDTO> update(@RequestBody SubjectDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Subject s = service.findById(id).orElse(null);
		
		ArrayList<Subject> subjectsOnYear = new ArrayList<Subject>();
		ArrayList<SubjectDTO> subjectsOnYearDTO= new ArrayList<SubjectDTO>();
		
		ArrayList<SubjectRealization> subjectRealizations = new ArrayList<SubjectRealization>();
		ArrayList<SubjectRealizationDTO> subjectRealizationDTOs = new ArrayList<SubjectRealizationDTO>();
		
		ArrayList<Outcome> syllabi = new ArrayList<Outcome>();
		ArrayList<OutcomeDTO> syllabusDTOs = new ArrayList<OutcomeDTO>();
		
		
		if(s == null) {
			return new ResponseEntity<SubjectDTO>(HttpStatus.NOT_FOUND);
		}
		
		for(SubjectRealization sr : s.getSubjectRealizations()) {
			subjectRealizationDTOs.add(new SubjectRealizationDTO(sr.getId(), new ArrayList<EvaluationDTO>(),
					new HashSet<TeacherOnRealizationDTO>(), new ArrayList<AnnouncementDTO>(), 
					new SubjectDTO(sr.getSubject().getId(), sr.getSubject().getName(), sr.getSubject().getEcts(), sr.getSubject().getActive()),
					sr.getActive()));
		}
		
		for(SubjectRealizationDTO srdto : subjectRealizationDTOs) {
			subjectRealizations.add(new SubjectRealization(srdto.getId(), new ArrayList<Evaluation>(),
					new HashSet<TeacherOnRealization>(),
					new ArrayList<Announcement>(), new Subject(srdto.getSubject().getId(),
							srdto.getSubject().getName(),
							srdto.getSubject().getEcts(), 
							srdto.getSubject().isCompulsory(),
							srdto.getSubject().getNumberOfClasses(), 
							srdto.getSubject().getNumberOfPractices(),
							srdto.getSubject().getOtherTypesOfClasses(), 
							srdto.getSubject().getResearchWork(),
							srdto.getSubject().getClassesLeft(),
							srdto.getSubject().getNumberOfSemesters(),
							new YearOfStudy(srdto.getSubject().getYearOfStudy().getId(),
									srdto.getSubject().getYearOfStudy().getYearOfStudy(),
									new ArrayList<Subject>(), srdto.getSubject().getYearOfStudy().getActive()),
							new ArrayList<Outcome>(),
							new ArrayList<SubjectRealization>(),
							new Subject(srdto.getSubject().getPrerequisite().getId(),
									srdto.getSubject().getPrerequisite().getName(),
									srdto.getSubject().getPrerequisite().getEcts(),
									srdto.getSubject().getPrerequisite().isCompulsory(),
									srdto.getSubject().getPrerequisite().getNumberOfClasses(),
									srdto.getSubject().getPrerequisite().getNumberOfPractices(),
									srdto.getSubject().getPrerequisite().getOtherTypesOfClasses(),
									srdto.getSubject().getPrerequisite().getResearchWork(),
									srdto.getSubject().getPrerequisite().getClassesLeft(), 
									srdto.getSubject().getPrerequisite().getNumberOfSemesters(),
									new YearOfStudy(), new ArrayList<Outcome>(), new ArrayList<SubjectRealization>(), null, true),
							srdto.getSubject().getActive()),
					srdto.getActive()));
		}
		
		for(Outcome o : s.getSyllabi()) {
			syllabusDTOs.add(new OutcomeDTO(o.getId(), o.getDescription(), new EducationGoalDTO(),
					new TeachingMaterialDTO(o.getTeachingMaterial().getId(), null, null, null, null, o.getTeachingMaterial().getActive()),
					new SubjectDTO(o.getSubject().getId(),
							o.getSubject().getName(),
							o.getSubject().getEcts(),
							o.getSubject().getActive()),
					o.getActive()));
		}
		
		for(OutcomeDTO odto : syllabusDTOs) {
			syllabi.add(new Outcome(odto.getId(), odto.getDescription(), 
					new EducationGoal(odto.getEducationGoal().getId(),
							odto.getEducationGoal().getDescription(),
							null, odto.getEducationGoal().getActive()),
					new TeachingMaterial(odto.getTeachingMaterial().getId(),
							odto.getTeachingMaterial().getName(),
							new ArrayList<Teacher>(), 
							odto.getTeachingMaterial().getYearOfPublication(),
							new File(odto.getTeachingMaterial().getFile().getId(),
									odto.getTeachingMaterial().getFile().getUrl(),
									odto.getTeachingMaterial().getFile().getDescription(),
									null, null, null, null, null, null, null, odto.getTeachingMaterial().getFile().getActive()),
							odto.getTeachingMaterial().getActive()),
					new Subject(odto.getSubject().getPrerequisite().getId(),
							odto.getSubject().getPrerequisite().getName(),
							odto.getSubject().getPrerequisite().getEcts(),
							odto.getSubject().getPrerequisite().isCompulsory(),
							odto.getSubject().getPrerequisite().getNumberOfClasses(),
							odto.getSubject().getPrerequisite().getNumberOfPractices(),
							odto.getSubject().getPrerequisite().getOtherTypesOfClasses(),
							odto.getSubject().getPrerequisite().getResearchWork(),
							odto.getSubject().getPrerequisite().getClassesLeft(), 
							odto.getSubject().getPrerequisite().getNumberOfSemesters(),
							new YearOfStudy(), new ArrayList<Outcome>(), 
							new ArrayList<SubjectRealization>(), null, true),
					odto.getSubject().getActive()));
		}
		
		for(Subject yearSubject : s.getYearOfStudy().getSubjects()) {
			subjectsOnYearDTO.add(new SubjectDTO(yearSubject.getId(), 
					yearSubject.getName(),
					yearSubject.getEcts(),
					yearSubject.isCompulsory()));
		}
		
		for(SubjectDTO dto : subjectsOnYearDTO) {
			subjectsOnYear.add(new Subject(dto.getId(), 
					dto.getName(),
					dto.getEcts(),
					dto.isCompulsory(), dto.getNumberOfClasses(), dto.getNumberOfPractices(),
					dto.getOtherTypesOfClasses(), 
					dto.getResearchWork(),
					dto.getClassesLeft(),
					dto.getNumberOfSemesters(),
					new YearOfStudy(dto.getYearOfStudy().getId(), dto.getYearOfStudy().getYearOfStudy(),
							new ArrayList<Subject>(), t.getYearOfStudy().getActive()),
					new ArrayList<Outcome>(),
					new ArrayList<SubjectRealization>(),
					new Subject(dto.getPrerequisite().getId(), dto.getPrerequisite().getName(),
							dto.getPrerequisite().getEcts(),
							dto.getPrerequisite().isCompulsory(), dto.getPrerequisite().getNumberOfClasses(),
							dto.getPrerequisite().getNumberOfPractices(), dto.getPrerequisite().getOtherTypesOfClasses(),
							dto.getPrerequisite().getResearchWork(), dto.getPrerequisite().getClassesLeft(), 
							dto.getPrerequisite().getNumberOfSemesters(),
							new YearOfStudy(), new ArrayList<Outcome>(), new ArrayList<SubjectRealization>(), null, true),
					dto.getActive()));
		}
		
		s.setName(t.getName());
		s.setEcts(t.getEcts());
		s.setCompulsory(t.isCompulsory());
		s.setNumberOfClasses(t.getNumberOfClasses());
		s.setOtherTypesOfClasses(t.getOtherTypesOfClasses());
		s.setResearchWork(t.getResearchWork());
		s.setClassesLeft(t.getClassesLeft());
		s.setNumberOfPractices(t.getNumberOfPractices());
		s.setNumberOfSemesters(t.getNumberOfSemesters());
		s.setYearOfStudy(new YearOfStudy(t.getYearOfStudy().getId(), t.getYearOfStudy().getYearOfStudy(),null, true));
		s.setSyllabi(syllabi);
		s.setSubjectRealizations(subjectRealizations);
		
		s.setPrerequisite(new Subject(t.getPrerequisite().getId(), t.getPrerequisite().getName(),t.getPrerequisite().getEcts(),
						t.getPrerequisite().isCompulsory(), t.getPrerequisite().getNumberOfClasses(),
						t.getPrerequisite().getNumberOfPractices(), t.getPrerequisite().getOtherTypesOfClasses(),
						t.getPrerequisite().getResearchWork(), t.getPrerequisite().getClassesLeft(), t.getPrerequisite().getNumberOfSemesters(),
						new YearOfStudy(), new ArrayList<Outcome>(), new ArrayList<SubjectRealization>(), null, true));
		
		s = service.update(s);
		
		return new ResponseEntity<SubjectDTO>(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
				s.isCompulsory(), s.getNumberOfClasses(), s.getNumberOfPractices(),
				s.getOtherTypesOfClasses(), s.getResearchWork(), s.getClassesLeft(), s.getNumberOfSemesters(),
				new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, null),
				syllabusDTOs,
				subjectRealizationDTOs,
				new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),
						s.getPrerequisite().getEcts(), 
						s.getPrerequisite().isCompulsory(), 
						null, null, null, null, null, null, null, null, null,null, null), s.getActive()), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PatchMapping("/{id}")
	@Secured({"ADMIN","STAFF"})
	public ResponseEntity<SubjectDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Subject s = service.findById(id).orElse(null);
		
		ArrayList<Subject> subjectsOnYear = new ArrayList<Subject>();
		ArrayList<SubjectDTO> subjectsOnYearDTO= new ArrayList<SubjectDTO>();
		
		ArrayList<SubjectRealization> subjectRealizations = new ArrayList<SubjectRealization>();
		ArrayList<SubjectRealizationDTO> subjectRealizationDTOs = new ArrayList<SubjectRealizationDTO>();
		
		ArrayList<Outcome> syllabi = new ArrayList<Outcome>();
		ArrayList<OutcomeDTO> syllabusDTOs = new ArrayList<OutcomeDTO>();
		
		
		if(s == null) {
			return new ResponseEntity<SubjectDTO>(HttpStatus.NOT_FOUND);
		}
		
		for(SubjectRealization sr : s.getSubjectRealizations()) {
			subjectRealizationDTOs.add(new SubjectRealizationDTO(sr.getId(), new ArrayList<EvaluationDTO>(),
					new HashSet<TeacherOnRealizationDTO>(), new ArrayList<AnnouncementDTO>(), 
					new SubjectDTO(sr.getSubject().getId(), sr.getSubject().getName(), sr.getSubject().getEcts(), sr.getSubject().getActive()),
					sr.getActive()));
		}
		
		for(SubjectRealizationDTO srdto : subjectRealizationDTOs) {
			subjectRealizations.add(new SubjectRealization(srdto.getId(), new ArrayList<Evaluation>(),
					new HashSet<TeacherOnRealization>(),
					new ArrayList<Announcement>(), new Subject(srdto.getSubject().getId(),
							srdto.getSubject().getName(),
							srdto.getSubject().getEcts(), 
							srdto.getSubject().isCompulsory(),
							srdto.getSubject().getNumberOfClasses(), 
							srdto.getSubject().getNumberOfPractices(),
							srdto.getSubject().getOtherTypesOfClasses(), 
							srdto.getSubject().getResearchWork(),
							srdto.getSubject().getClassesLeft(),
							srdto.getSubject().getNumberOfSemesters(),
							new YearOfStudy(srdto.getSubject().getYearOfStudy().getId(),
									srdto.getSubject().getYearOfStudy().getYearOfStudy(),
									new ArrayList<Subject>(), srdto.getSubject().getYearOfStudy().getActive()),
							new ArrayList<Outcome>(),
							new ArrayList<SubjectRealization>(),
							new Subject(srdto.getSubject().getPrerequisite().getId(),
									srdto.getSubject().getPrerequisite().getName(),
									srdto.getSubject().getPrerequisite().getEcts(),
									srdto.getSubject().getPrerequisite().isCompulsory(),
									srdto.getSubject().getPrerequisite().getNumberOfClasses(),
									srdto.getSubject().getPrerequisite().getNumberOfPractices(),
									srdto.getSubject().getPrerequisite().getOtherTypesOfClasses(),
									srdto.getSubject().getPrerequisite().getResearchWork(),
									srdto.getSubject().getPrerequisite().getClassesLeft(), 
									srdto.getSubject().getPrerequisite().getNumberOfSemesters(),
									new YearOfStudy(), new ArrayList<Outcome>(), new ArrayList<SubjectRealization>(), null, true),
							srdto.getSubject().getActive()),
					srdto.getActive()));
		}
		
		for(Outcome o : s.getSyllabi()) {
			syllabusDTOs.add(new OutcomeDTO(o.getId(), o.getDescription(), new EducationGoalDTO(),
					new TeachingMaterialDTO(o.getTeachingMaterial().getId(), null, null, null, null, o.getTeachingMaterial().getActive()),
					new SubjectDTO(o.getSubject().getId(),
							o.getSubject().getName(),
							o.getSubject().getEcts(),
							o.getSubject().getActive()),
					o.getActive()));
		}
		
		for(OutcomeDTO odto : syllabusDTOs) {
			syllabi.add(new Outcome(odto.getId(), odto.getDescription(), 
					new EducationGoal(odto.getEducationGoal().getId(),
							odto.getEducationGoal().getDescription(),
							null, odto.getEducationGoal().getActive()),
					new TeachingMaterial(odto.getTeachingMaterial().getId(),
							odto.getTeachingMaterial().getName(),
							new ArrayList<Teacher>(), 
							odto.getTeachingMaterial().getYearOfPublication(),
							new File(odto.getTeachingMaterial().getFile().getId(),
									odto.getTeachingMaterial().getFile().getUrl(),
									odto.getTeachingMaterial().getFile().getDescription(),
									null, null, null, null, null, null, null, odto.getTeachingMaterial().getFile().getActive()),
							odto.getTeachingMaterial().getActive()),
					new Subject(odto.getSubject().getPrerequisite().getId(),
							odto.getSubject().getPrerequisite().getName(),
							odto.getSubject().getPrerequisite().getEcts(),
							odto.getSubject().getPrerequisite().isCompulsory(),
							odto.getSubject().getPrerequisite().getNumberOfClasses(),
							odto.getSubject().getPrerequisite().getNumberOfPractices(),
							odto.getSubject().getPrerequisite().getOtherTypesOfClasses(),
							odto.getSubject().getPrerequisite().getResearchWork(),
							odto.getSubject().getPrerequisite().getClassesLeft(), 
							odto.getSubject().getPrerequisite().getNumberOfSemesters(),
							new YearOfStudy(), new ArrayList<Outcome>(), 
							new ArrayList<SubjectRealization>(), null, true),
					odto.getSubject().getActive()));
		}
		
		for(Subject yearSubject : s.getYearOfStudy().getSubjects()) {
			subjectsOnYearDTO.add(new SubjectDTO(yearSubject.getId(), 
					yearSubject.getName(),
					yearSubject.getEcts(),
					yearSubject.isCompulsory()));
		}
		
		for(SubjectDTO dto : subjectsOnYearDTO) {
			subjectsOnYear.add(new Subject(dto.getId(), 
					dto.getName(),
					dto.getEcts(),
					dto.isCompulsory(), dto.getNumberOfClasses(), dto.getNumberOfPractices(),
					dto.getOtherTypesOfClasses(), 
					dto.getResearchWork(),
					dto.getClassesLeft(),
					dto.getNumberOfSemesters(),
					new YearOfStudy(dto.getYearOfStudy().getId(), dto.getYearOfStudy().getYearOfStudy(),
							new ArrayList<Subject>(), dto.getYearOfStudy().getActive()),
					new ArrayList<Outcome>(),
					new ArrayList<SubjectRealization>(),
					new Subject(dto.getPrerequisite().getId(), dto.getPrerequisite().getName(),
							dto.getPrerequisite().getEcts(),
							dto.getPrerequisite().isCompulsory(), dto.getPrerequisite().getNumberOfClasses(),
							dto.getPrerequisite().getNumberOfPractices(), dto.getPrerequisite().getOtherTypesOfClasses(),
							dto.getPrerequisite().getResearchWork(), dto.getPrerequisite().getClassesLeft(), 
							dto.getPrerequisite().getNumberOfSemesters(),
							new YearOfStudy(), new ArrayList<Outcome>(), new ArrayList<SubjectRealization>(), null, true),
					dto.getActive()));
		}
		
		service.softDelete(id);
		return new ResponseEntity<SubjectDTO>(new SubjectDTO(s.getId(), s.getName(),s.getEcts(), 
				s.isCompulsory(), s.getNumberOfClasses(), s.getNumberOfPractices(),
				s.getOtherTypesOfClasses(), s.getResearchWork(), s.getClassesLeft(), s.getNumberOfSemesters(),
				new YearOfStudyDTO(s.getYearOfStudy().getId(), s.getYearOfStudy().getYearOfStudy(), null, null),
				syllabusDTOs,
				subjectRealizationDTOs,
				new SubjectDTO(s.getPrerequisite().getId(), s.getPrerequisite().getName(),
						s.getPrerequisite().getEcts(), 
						s.getPrerequisite().isCompulsory(), 
						null, null, null, null, null, null, null, null, null,null, null), s.getActive()), HttpStatus.OK);
	}
	
	
}
