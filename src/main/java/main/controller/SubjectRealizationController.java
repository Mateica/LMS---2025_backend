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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.AnnouncementDTO;
import main.dto.DepartmentDTO;
import main.dto.EducationGoalDTO;
import main.dto.EvaluationDTO;
import main.dto.EvaluationInstrumentDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.ExaminationDTO;
import main.dto.FileDTO;
import main.dto.OutcomeDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentDTO;
import main.dto.StudentOnYearDTO;
import main.dto.SubjectDTO;
import main.dto.SubjectRealizationDTO;
import main.dto.TeacherDTO;
import main.dto.TeacherOnRealizationDTO;
import main.dto.YearOfStudyDTO;
import main.model.Announcement;
import main.model.Department;
import main.model.EducationGoal;
import main.model.Evaluation;
import main.model.EvaluationInstrument;
import main.model.EvaluationType;
import main.model.Examination;
import main.model.File;
import main.model.Outcome;
import main.model.RegisteredUser;
import main.model.Student;
import main.model.StudentOnYear;
import main.model.Subject;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.TeachingMaterial;
import main.model.YearOfStudy;
import main.service.SubjectRealizationService;
import main.service.SubjectService;

@RestController
@RequestMapping("/api/subjectRealizations")
public class SubjectRealizationController implements ControllerInterface<SubjectRealizationDTO> {
	@Autowired
	private SubjectRealizationService service;

	@Override
	@GetMapping("")
	@Secured({"ADMIN"})
	public ResponseEntity<Iterable<SubjectRealizationDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<SubjectRealizationDTO> subjectRealizations = new ArrayList<SubjectRealizationDTO>();
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		HashSet<TeacherOnRealizationDTO> teachersOnRealization = new HashSet<TeacherOnRealizationDTO>();
		ArrayList<AnnouncementDTO> announcements = new ArrayList<AnnouncementDTO>();
		
		for(SubjectRealization s : service.findAll()) {
			
			for(Evaluation e : s.getEvaluations()) {
				evaluations.add(new EvaluationDTO(e.getId(), 
						e.getStartTime(),
						e.getEndTime(), 
						e.getNumberOfPoints(),
						new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
								new ArrayList<EvaluationDTO>(), 
								e.getEvaluationType().getActive()),
						new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(), e.getEvaluationInstrument().getName(),
								new FileDTO(e.getEvaluationInstrument().getFile().getId(),
										e.getEvaluationInstrument().getFile().getUrl(), 
										e.getEvaluationInstrument().getFile().getDescription(),
										null, null, null, null, null, null, null, e.getEvaluationInstrument().getFile().getActive()),
								e.getEvaluationInstrument().getActive()),
						new ExaminationDTO(e.getExamination().getId(), e.getExamination().getNumberOfPoints(),
								null, null, e.getExamination().getActive()),
						null, e.getActive()));
			}
			
			for(TeacherOnRealization t : s.getTeachersOnRealization()) {
				teachersOnRealization.add(new TeacherOnRealizationDTO(t.getId(), t.getNumberOfClasses(),
						new TeacherDTO(t.getTeacher().getId(), null,
								t.getTeacher().getFirstName(), t.getTeacher().getLastName(),
								t.getTeacher().getUmcn(), t.getTeacher().getBiography(), null, null, null, null,
								t.getTeacher().getActive()),
						null, null, t.getActive()));
			}
			
			for(Announcement a : s.getAnnouncements()) {
				ArrayList<FileDTO> attachments = new ArrayList<FileDTO>();
				
				attachments = (ArrayList<FileDTO>) a.getAttachments()
								.stream()
								.map(f -> 
								new FileDTO(f.getId(), f.getUrl(), f.getDescription(),
										null, null, null, null, null, null, null, f.getActive()))
								.collect(Collectors.toList());
				announcements.add(new AnnouncementDTO(a.getId(), a.getTimePublished(), a.getContent(),
						null, a.getTitle(), attachments, a.getActive()));
			}
			
			if(s.getSubject().getPrerequisite() != null) {
				subjectRealizations.add(new SubjectRealizationDTO(s.getId(),
						evaluations,
						teachersOnRealization,
						announcements,
						new SubjectDTO(s.getSubject().getId(),
								s.getSubject().getName(),
								s.getSubject().getEcts(), 
								s.getSubject().isCompulsory()), 
						s.getSubject().getActive()));
			}else {
				subjectRealizations.add(new SubjectRealizationDTO(s.getId(),
						evaluations,
						teachersOnRealization,
						announcements,
						null, 
						s.getSubject().getActive()));
			}
		}
		
		return new ResponseEntity<Iterable<SubjectRealizationDTO>>(subjectRealizations, HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/params")
	@Secured({"ADMIN"})
	public ResponseEntity<Page<SubjectRealizationDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<SubjectRealization> subjectRealizationPage = service.findAll(pageable);
	    
	    ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		HashSet<TeacherOnRealizationDTO> teachersOnRealization = new HashSet<TeacherOnRealizationDTO>();
		ArrayList<AnnouncementDTO> announcements = new ArrayList<AnnouncementDTO>();
		
		for(SubjectRealization s : subjectRealizationPage) {
			
			for(Evaluation e : s.getEvaluations()) {
				evaluations.add(new EvaluationDTO(e.getId(), 
						e.getStartTime(),
						e.getEndTime(), 
						e.getNumberOfPoints(),
						new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
								new ArrayList<EvaluationDTO>(), 
								e.getEvaluationType().getActive()),
						new EvaluationInstrumentDTO(),
						new ExaminationDTO(), null, e.getActive()));
			}
			
			for(TeacherOnRealization t : s.getTeachersOnRealization()) {
				teachersOnRealization.add(new TeacherOnRealizationDTO(t.getId(), t.getNumberOfClasses(),
						new TeacherDTO(t.getTeacher().getId(), null,
								t.getTeacher().getFirstName(), t.getTeacher().getLastName(),
								t.getTeacher().getUmcn(), t.getTeacher().getBiography(), null, null, null, null,
								t.getTeacher().getActive()),
						null, null, t.getActive()));
			}
			
			for(Announcement a : s.getAnnouncements()) {
				ArrayList<FileDTO> attachments = new ArrayList<FileDTO>();
				
				attachments = (ArrayList<FileDTO>) a.getAttachments()
								.stream()
								.map(f -> 
								new FileDTO(f.getId(), f.getUrl(), f.getDescription(),
										null, null, null, null, null, null, null, f.getActive()))
								.collect(Collectors.toList());
				announcements.add(new AnnouncementDTO(a.getId(), a.getTimePublished(), a.getContent(),
						null, a.getTitle(), attachments, a.getActive()));
			}
		}
		
		

	    List<SubjectRealizationDTO> subjectRealizationDTOs = subjectRealizationPage.stream().map(s -> 
	    		new SubjectRealizationDTO(s.getId(),
						evaluations,
						teachersOnRealization,
						announcements,
						new SubjectDTO(s.getSubject().getId(),
								s.getSubject().getName(),
								s.getSubject().getEcts(), 
								s.getSubject().isCompulsory()), 
						s.getSubject().getActive())
	    ).collect(Collectors.toList());

	    Page<SubjectRealizationDTO> resultPage = new PageImpl<SubjectRealizationDTO>(subjectRealizationDTOs, pageable, subjectRealizationPage.getTotalElements());

	    return new ResponseEntity<Page<SubjectRealizationDTO>>(resultPage, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectRealizationDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		SubjectRealization s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<SubjectRealizationDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		HashSet<TeacherOnRealizationDTO> teachersOnRealization = new HashSet<TeacherOnRealizationDTO>();
		ArrayList<AnnouncementDTO> announcements = new ArrayList<AnnouncementDTO>();
		
		for(Evaluation e : s.getEvaluations()) {
			evaluations.add(new EvaluationDTO(e.getId(), 
					e.getStartTime(),
					e.getEndTime(), 
					e.getNumberOfPoints(),
					new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
							new ArrayList<EvaluationDTO>(), 
							e.getEvaluationType().getActive()),
					new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(), e.getEvaluationInstrument().getName(),
							new FileDTO(e.getEvaluationInstrument().getFile().getId(),
									e.getEvaluationInstrument().getFile().getUrl(), 
									e.getEvaluationInstrument().getFile().getDescription(),
									null, null, null, null, null, null, null, e.getEvaluationInstrument().getFile().getActive()),
							e.getEvaluationInstrument().getActive()),
					new ExaminationDTO(e.getExamination().getId(), e.getExamination().getNumberOfPoints(),
							null, null, e.getExamination().getActive()), null, e.getActive()));
		}
		
		for(TeacherOnRealization t : s.getTeachersOnRealization()) {
			teachersOnRealization.add(new TeacherOnRealizationDTO(t.getId(), t.getNumberOfClasses(),
					new TeacherDTO(t.getTeacher().getId(), null,
							t.getTeacher().getFirstName(), t.getTeacher().getLastName(),
							t.getTeacher().getUmcn(), t.getTeacher().getBiography(), null, null, null, null,
							t.getTeacher().getActive()),
					null, null, t.getActive()));
		}
		
		for(Announcement a : s.getAnnouncements()) {
			ArrayList<FileDTO> attachments = new ArrayList<FileDTO>();
			
			attachments = (ArrayList<FileDTO>) a.getAttachments()
							.stream()
							.map(f -> 
							new FileDTO(f.getId(), f.getUrl(), f.getDescription(),
									null, null, null, null, null, null, null, f.getActive()))
							.collect(Collectors.toList());
			announcements.add(new AnnouncementDTO(a.getId(), a.getTimePublished(), a.getContent(),
					null, a.getTitle(), attachments, a.getActive()));
		}
		
		
		return new ResponseEntity<SubjectRealizationDTO>(new SubjectRealizationDTO(s.getId(),
				evaluations,
				teachersOnRealization,
				announcements,
				new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(), s.getSubject().getEcts(),
						s.getSubject().getActive()), 
				s.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping("")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectRealizationDTO> create(@RequestBody SubjectRealizationDTO t) {
		// TODO Auto-generated method stub
		SubjectRealization s = service.create(new SubjectRealization(null,
				new ArrayList<Evaluation>(),
				new HashSet<TeacherOnRealization>(),
				new ArrayList<Announcement>(),
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
								new ArrayList<Subject>(),
								t.getSubject().getYearOfStudy().getActive()),
						new ArrayList<Outcome>(),
						new ArrayList<SubjectRealization>(), null, true),
		t.getActive()));
		
		if(s == null) {
			return new ResponseEntity<SubjectRealizationDTO>(HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		HashSet<TeacherOnRealizationDTO> teachersOnRealization = new HashSet<TeacherOnRealizationDTO>();
		ArrayList<AnnouncementDTO> announcements = new ArrayList<AnnouncementDTO>();
		
		for(Evaluation e : s.getEvaluations()) {
			evaluations.add(new EvaluationDTO(e.getId(), 
					e.getStartTime(),
					e.getEndTime(), 
					e.getNumberOfPoints(),
					new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
							new ArrayList<EvaluationDTO>(), 
							e.getEvaluationType().getActive()),
					new EvaluationInstrumentDTO(),
					new ExaminationDTO(), null, e.getActive()));
		}
		
		for(TeacherOnRealization tor : s.getTeachersOnRealization()) {
			teachersOnRealization.add(new TeacherOnRealizationDTO(tor.getId(), tor.getNumberOfClasses(),
					new TeacherDTO(tor.getTeacher().getId(), null,
							tor.getTeacher().getFirstName(), tor.getTeacher().getLastName(),
							tor.getTeacher().getUmcn(), tor.getTeacher().getBiography(), null, null, null, null,
							tor.getTeacher().getActive()),
					null, null, tor.getActive()));
		}
		
		for(Announcement a : s.getAnnouncements()) {
			ArrayList<FileDTO> attachments = new ArrayList<FileDTO>();
			
			attachments = (ArrayList<FileDTO>) a.getAttachments()
							.stream()
							.map(f -> 
							new FileDTO(f.getId(), f.getUrl(), f.getDescription(),
									null, null, null, null, null, null, null, f.getActive()))
							.collect(Collectors.toList());
			announcements.add(new AnnouncementDTO(a.getId(), a.getTimePublished(), a.getContent(),
					null, a.getTitle(), attachments, a.getActive()));
		}
		
		
		return new ResponseEntity<SubjectRealizationDTO>(new SubjectRealizationDTO(s.getId(),
				evaluations,
				teachersOnRealization,
				announcements,
				new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(), s.getSubject().getEcts(),
						s.getSubject().getActive()), 
				s.getActive()), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectRealizationDTO> update(@RequestBody SubjectRealizationDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		SubjectRealization s = service.findById(id).orElse(null);
		
		ArrayList<Subject> subjectsOnYear = new ArrayList<Subject>();
		ArrayList<SubjectDTO> subjectsOnYearDTO= new ArrayList<SubjectDTO>();
		
		ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
		HashSet<TeacherOnRealization> teachersOnRealization = new HashSet<TeacherOnRealization>();
		ArrayList<Announcement> announcements = new ArrayList<Announcement>();
		
		
		ArrayList<EvaluationDTO> evaluationDTOs = new ArrayList<EvaluationDTO>();
		HashSet<TeacherOnRealizationDTO> teacherOnRealizationDTOs = new HashSet<TeacherOnRealizationDTO>();
		ArrayList<AnnouncementDTO> announcementDTOs = new ArrayList<AnnouncementDTO>();
		
		
		if(s == null) {
			return new ResponseEntity<SubjectRealizationDTO>(HttpStatus.NOT_FOUND);
		}
		
		for(Evaluation e : s.getEvaluations()) {
			evaluationDTOs.add(new EvaluationDTO(e.getId(), 
					e.getStartTime(),
					e.getEndTime(), 
					e.getNumberOfPoints(),
					new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
							new ArrayList<EvaluationDTO>(), 
							e.getEvaluationType().getActive()),
					new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(), e.getEvaluationInstrument().getName(),
							new FileDTO(e.getEvaluationInstrument().getFile().getId(),
									e.getEvaluationInstrument().getFile().getUrl(), 
									e.getEvaluationInstrument().getFile().getDescription(),
									null, null, null, null, null, null, null, e.getEvaluationInstrument().getFile().getActive()),
							e.getEvaluationInstrument().getActive()),
					new ExaminationDTO(e.getExamination().getId(), e.getExamination().getNumberOfPoints(),
							null, null, e.getExamination().getActive()), null, e.getActive()));
		}
		
		for(TeacherOnRealization tor : s.getTeachersOnRealization()) {
			teacherOnRealizationDTOs.add(new TeacherOnRealizationDTO(tor.getId(), tor.getNumberOfClasses(),
					new TeacherDTO(tor.getTeacher().getId(), null,
							tor.getTeacher().getFirstName(), tor.getTeacher().getLastName(),
							tor.getTeacher().getUmcn(), tor.getTeacher().getBiography(), null, null, null, null,
							tor.getTeacher().getActive()),
					null, null, tor.getActive()));
		}
		
		for(Announcement a : s.getAnnouncements()) {
			ArrayList<FileDTO> attachments = new ArrayList<FileDTO>();
			
			attachments = (ArrayList<FileDTO>) a.getAttachments()
							.stream()
							.map(f -> 
							new FileDTO(f.getId(), f.getUrl(), f.getDescription(),
									null, null, null, null, null, null, null, f.getActive()))
							.collect(Collectors.toList());
			announcementDTOs.add(new AnnouncementDTO(a.getId(), a.getTimePublished(), a.getContent(),
					null, a.getTitle(), attachments, a.getActive()));
		}
		
		for(EvaluationDTO edto : evaluationDTOs) {
			evaluations.add(new Evaluation(edto.getId(), 
					edto.getStartTime(),
					edto.getEndTime(), 
					edto.getNumberOfPoints(),
					new EvaluationType(edto.getEvaluationType().getId(), edto.getEvaluationType().getName(),
							new ArrayList<Evaluation>(), 
							edto.getEvaluationType().getActive()),
					new EvaluationInstrument(edto.getEvaluationInstrument().getId(),
							edto.getEvaluationInstrument().getName(),
							new ArrayList<Evaluation>(),
							new File(edto.getEvaluationInstrument().getFile().getId(),
									edto.getEvaluationInstrument().getFile().getUrl(), 
									edto.getEvaluationInstrument().getFile().getDescription(),
									null, null, null, null, null, null, null, edto.getEvaluationInstrument().getFile().getActive()),
							edto.getEvaluationInstrument().getActive()),
					new Examination(edto.getExamination().getId(), edto.getExamination().getNumberOfPoints(),
							null, null, null, edto.getExamination().getActive()), null, null, edto.getActive()));
		}
		
		for(TeacherOnRealizationDTO tdto : teacherOnRealizationDTOs) {
			teachersOnRealization.add(new TeacherOnRealization(tdto.getId(), tdto.getNumberOfClasses(),
					new Teacher(tdto.getTeacher().getId(), null,
							tdto.getTeacher().getFirstName(), tdto.getTeacher().getLastName(),
							tdto.getTeacher().getUmcn(), tdto.getTeacher().getBiography(), null, null, null, null,
							null, tdto.getTeacher().getActive()),
					null, null, tdto.getActive()));
		}
		
		for(AnnouncementDTO adto : announcementDTOs) {
			ArrayList<File> attachments = new ArrayList<File>();
			
			attachments = (ArrayList<File>) adto.getAttachments()
							.stream()
							.map(f -> 
							new File(f.getId(), f.getUrl(), f.getDescription(),
									null, null, null, null, null, null, null, f.getActive()))
							.collect(Collectors.toList());
			announcements.add(new Announcement(adto.getId(), adto.getTimePublished(), adto.getContent(),
					null, adto.getTitle(), attachments, adto.getActive()));
		}
		
		s.setId(t.getId());
		s.setEvaluations(evaluations);
		s.setTeachersOnRealization(teachersOnRealization);
		s.setAnnouncements(announcements);
		s.setSubject(new Subject(t.getSubject().getId(),
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
						new ArrayList<Subject>(),
						t.getSubject().getYearOfStudy().getActive()),
				new ArrayList<Outcome>(),
				new ArrayList<SubjectRealization>(), null, true));
		
		
		s = service.update(s);
		
		return new ResponseEntity<SubjectRealizationDTO>(new SubjectRealizationDTO(s.getId(),
				evaluationDTOs,
				teacherOnRealizationDTOs,
				announcementDTOs,
				new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(), s.getSubject().getEcts(),
						s.getSubject().getActive()), 
				s.getActive()), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectRealizationDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping("/softDelete/{id}")
	@Secured({"ADMIN"})
	public ResponseEntity<SubjectRealizationDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		SubjectRealization s = service.findById(id).orElse(null);
		
		if(s == null) {
			return new ResponseEntity<SubjectRealizationDTO>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>();
		HashSet<TeacherOnRealizationDTO> teachersOnRealization = new HashSet<TeacherOnRealizationDTO>();
		ArrayList<AnnouncementDTO> announcements = new ArrayList<AnnouncementDTO>();
		
		for(Evaluation e : s.getEvaluations()) {
			evaluations.add(new EvaluationDTO(e.getId(), 
					e.getStartTime(),
					e.getEndTime(), 
					e.getNumberOfPoints(),
					new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
							new ArrayList<EvaluationDTO>(), 
							e.getEvaluationType().getActive()),
					new EvaluationInstrumentDTO(),
					new ExaminationDTO(), null, e.getActive()));
		}
		
		for(TeacherOnRealization tor : s.getTeachersOnRealization()) {
			teachersOnRealization.add(new TeacherOnRealizationDTO(tor.getId(), tor.getNumberOfClasses(),
					new TeacherDTO(tor.getTeacher().getId(), null,
							tor.getTeacher().getFirstName(), tor.getTeacher().getLastName(),
							tor.getTeacher().getUmcn(), tor.getTeacher().getBiography(), null, null, null, null,
							tor.getTeacher().getActive()),
					null, null, tor.getActive()));
		}
		
		for(Announcement a : s.getAnnouncements()) {
			ArrayList<FileDTO> attachments = new ArrayList<FileDTO>();
			
			attachments = (ArrayList<FileDTO>) a.getAttachments()
							.stream()
							.map(f -> 
							new FileDTO(f.getId(), f.getUrl(), f.getDescription(),
									null, null, null, null, null, null, null, f.getActive()))
							.collect(Collectors.toList());
			announcements.add(new AnnouncementDTO(a.getId(), a.getTimePublished(), a.getContent(),
					null, a.getTitle(), attachments, a.getActive()));
		}
		
		service.softDelete(id);
		return new ResponseEntity<SubjectRealizationDTO>(new SubjectRealizationDTO(s.getId(),
				evaluations,
				teachersOnRealization,
				announcements,
				new SubjectDTO(s.getSubject().getId(), s.getSubject().getName(), s.getSubject().getEcts(),
						s.getSubject().getActive()), 
				s.getActive()), HttpStatus.OK);
	}
}
