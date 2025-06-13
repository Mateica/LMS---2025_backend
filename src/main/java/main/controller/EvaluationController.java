package main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import main.model.*;
import main.service.EvaluationInstrumentService;
import main.service.EvaluationTypeService;
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

import main.dto.AnnouncementDTO;
import main.dto.EvaluationDTO;
import main.dto.EvaluationInstrumentDTO;
import main.dto.EvaluationTypeDTO;
import main.dto.FileDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentDTO;
import main.service.EvaluationService;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController implements ControllerInterface<EvaluationDTO> {
    @Autowired
    private EvaluationService service;

    @Autowired
    private EvaluationTypeService evaluationTypeService;

    @Autowired
    private EvaluationInstrumentService evaluationInstrumentService;

    @Override
    @GetMapping
    @Secured({"ADMIN", "TEACHER", "STAFF", "STUDENT"})
    public ResponseEntity<Iterable<EvaluationDTO>> findAll() {
        ArrayList<EvaluationDTO> evaluations = new ArrayList<>();

        for (Evaluation e : service.findAll()) {
            evaluations.add(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(),
                    new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
                            null, e.getEvaluationType().getActive()),
                    new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
                            e.getEvaluationInstrument().getName(),
                            e.getEvaluationInstrument().getFile() != null ?
                                    new FileDTO(e.getEvaluationInstrument().getFile().getId(),
                                            e.getEvaluationInstrument().getFile().getName(),
                                            e.getEvaluationInstrument().getFile().getUrl(),
                                            e.getEvaluationInstrument().getFile().getDescription(),
                                            null, null,
                                            new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
                                                    e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
                                                    e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
                                                    e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null,
                                                    e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
                                            null,
                                            new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
                                                    new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
                                                    e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
                                                    e.getEvaluationInstrument().getFile().getStudent().getLastName(),
                                                    e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
                                            e.getEvaluationInstrument().getFile().getDocument(),
                                            e.getEvaluationInstrument().getFile().getActive()) : null, null),
                    null, null, null, null));
        }
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }

    @Override
    @GetMapping("/params")
    @Secured({"ADMIN", "TEACHER", "STAFF", "STUDENT"})
    public ResponseEntity<Page<EvaluationDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size,
                                                       @RequestParam(defaultValue = "id") String sortBy,
                                                       @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Evaluation> evaluationPage = service.findAll(pageable);

        List<EvaluationDTO> evaluationDTOs = evaluationPage.stream().map(e ->
                new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(),
                        new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
                                null, e.getEvaluationType().getActive()),
                        new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
                                e.getEvaluationInstrument().getName(),
                                new FileDTO(e.getEvaluationInstrument().getFile().getId(),
                                        e.getEvaluationInstrument().getFile().getName(),
                                        e.getEvaluationInstrument().getFile().getUrl(),
                                        e.getEvaluationInstrument().getFile().getDescription(),
                                        null, null,
                                        new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
                                                e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
                                                e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
                                                e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null,
                                                e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
                                        null,
                                        new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
                                                new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
                                                e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
                                                e.getEvaluationInstrument().getFile().getStudent().getLastName(),
                                                e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
                                        e.getEvaluationInstrument().getFile().getDocument(),
                                        e.getEvaluationInstrument().getFile().getActive()), null),
                        null, null, null, null)
        ).collect(Collectors.toList());

        Page<EvaluationDTO> resultPage = new PageImpl<>(evaluationDTOs, pageable, evaluationPage.getTotalElements());

        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @GetMapping("/active")
    @Secured({"ADMIN", "TEACHER", "STAFF", "STUDENT"})
    public ResponseEntity<Iterable<EvaluationDTO>> findAllActive() {
        ArrayList<EvaluationDTO> evaluations = new ArrayList<>();

        for (Evaluation e : service.findAllActive()) {
            evaluations.add(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(),
                    new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
                            null, e.getEvaluationType().getActive()),
                    new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
                            e.getEvaluationInstrument().getName(),
                            (e.getEvaluationInstrument().getFile() != null ? new FileDTO(e.getEvaluationInstrument().getFile().getId(),
                                    e.getEvaluationInstrument().getFile().getName(),
                                    e.getEvaluationInstrument().getFile().getUrl(),
                                    e.getEvaluationInstrument().getFile().getDescription(),
                                    null, null,
                                    new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
                                            e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
                                            e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
                                            e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null,
                                            e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
                                    null,
                                    new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
                                            new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
                                            e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
                                            e.getEvaluationInstrument().getFile().getStudent().getLastName(),
                                            e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
                                    e.getEvaluationInstrument().getFile().getDocument(),
                                    e.getEvaluationInstrument().getFile().getActive()) : null), null),
                    null, null, null, null));
        }
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }


    @Override
    @GetMapping("/{id}")
    @Secured({"ADMIN", "TEACHER", "STAFF", "STUDENT"})
    public ResponseEntity<EvaluationDTO> findById(@PathVariable("id") Long id) {
        Evaluation e = service.findById(id).orElse(null);

        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(),
                new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
                        null, e.getEvaluationType().getActive()),
                new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
                        e.getEvaluationInstrument().getName(),
                        new FileDTO(e.getEvaluationInstrument().getFile().getId(),
                                e.getEvaluationInstrument().getFile().getName(),
                                e.getEvaluationInstrument().getFile().getUrl(),
                                e.getEvaluationInstrument().getFile().getDescription(),
                                null, null,
                                new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null,
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
                                null,
                                new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
                                        new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
                                        e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
                                        e.getEvaluationInstrument().getFile().getStudent().getLastName(),
                                        e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
                                e.getEvaluationInstrument().getFile().getDocument(),
                                e.getEvaluationInstrument().getFile().getActive()), null),
                null, null, null, null), HttpStatus.OK);
    }

    @Override
    @PostMapping
    @Secured({"ADMIN", "TEACHER"})
    public ResponseEntity<EvaluationDTO> create(@RequestBody EvaluationDTO t) {
		Optional<EvaluationType> evaluationTypeOptional = evaluationTypeService.findById(t.getEvaluationType().getId());
        if (evaluationTypeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

		Optional<EvaluationInstrument> evaluationInstrumentOptional = evaluationInstrumentService.findById(t.getEvaluationInstrument().getId());
		if (evaluationInstrumentOptional.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

        Evaluation e = service.create(new Evaluation(
                null,
                t.getStartTime(),
                t.getEndTime(),
                t.getNumberOfPoints(),
                evaluationTypeOptional.get(),
                evaluationInstrumentOptional.get(),
                null,
                null,
                null,
                true)
        );

        if (e == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

		File file = e.getEvaluationInstrument().getFile();
		Announcement announcement = null;
		Student student = null;
		if (file != null) {
			announcement = file.getAnnouncement();
			student = file.getStudent();
		}


        return new ResponseEntity<>(
				new EvaluationDTO(
						e.getId(),
						e.getStartTime(),
						e.getEndTime(),
						e.getNumberOfPoints(),
                new EvaluationTypeDTO(
						e.getEvaluationType().getId(),
						e.getEvaluationType().getName(),
                        null,
						e.getEvaluationType().getActive()
				),
                new EvaluationInstrumentDTO(
						e.getEvaluationInstrument().getId(),
                        e.getEvaluationInstrument().getName(),
                        new FileDTO(
								(file == null) ? null : file.getId(),
								(file == null) ? null : file.getName(),
								(file == null) ? null : file.getUrl(),
								(file == null) ? null : file.getDescription(),
                                null,
								null,
                                new AnnouncementDTO(
										(announcement == null) ? null : announcement.getId(),
                                        (announcement == null) ? null : announcement.getTimePublished(),
                                        (announcement == null) ? null : announcement.getContent(),
										null,
										(announcement == null) ? null : announcement.getTitle(),
										null,
										(announcement == null) ? null : announcement.getActive()
								),
                                null,
                                new StudentDTO(
                                        (student == null) ? null : student.getId(),
                                        new RegisteredUserDTO(
												(student == null) ? null : student.getUser().getUsername(),
												null,
												(student == null) ? null : student.getUser().getEmail()
										),
										(student == null) ? null : student.getFirstName(),
										(student == null) ? null : student.getLastName(),
										(student == null) ? null : student.getUmcn(),
										null,
										null,
										null,
										null,
										null
								),
								(file == null) ? null : file.getDocument(),
								(file == null) ? null : file.getActive()),
						null
				),
				null,
				null,
				null,
				null),
				HttpStatus.CREATED
		);
    }

    @Override
    @PutMapping("/{id}")
    @Secured({"ADMIN", "TEACHER"})
    public ResponseEntity<EvaluationDTO> update(@RequestBody EvaluationDTO t, @PathVariable("id") Long id) {
        Evaluation e = service.findById(id).orElse(null);

        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        e.setId(t.getId());
        e.setStartTime(t.getStartTime());
        e.setEndTime(t.getEndTime());
        e.setNumberOfPoints(t.getNumberOfPoints());
        e.setEvaluationType(service.findById(t.getId()).get().getEvaluationType());
        e.setEvaluationInstrument(service.findById(t.getId()).get().getEvaluationInstrument());
        e.setExamination(service.findById(t.getId()).get().getExamination());
        e.setSubjectRealization(service.findById(t.getId()).get().getSubjectRealization());
        e.setEvaluationGrades(service.findById(t.getId()).get().getEvaluationGrades());
        e.setActive(t.getActive());

        e = service.update(e);

        return new ResponseEntity<>(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(),
                e.getNumberOfPoints(),
                new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
                        null, e.getEvaluationType().getActive()),
                new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
                        e.getEvaluationInstrument().getName(),
                        new FileDTO(e.getEvaluationInstrument().getFile().getId(),
                                e.getEvaluationInstrument().getFile().getName(),
                                e.getEvaluationInstrument().getFile().getUrl(),
                                e.getEvaluationInstrument().getFile().getDescription(),
                                null, null,
                                new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null,
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
                                null,
                                new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
                                        new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
                                        e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
                                        e.getEvaluationInstrument().getFile().getStudent().getLastName(),
                                        e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
                                e.getEvaluationInstrument().getFile().getDocument(),
                                e.getEvaluationInstrument().getFile().getActive()), null),
                null, null, null, null), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EvaluationDTO> delete(@PathVariable Long id) {
        return null;
    }

    @Override
    @PatchMapping("/{id}")
    @Secured({"ADMIN", "TEACHER", "STAFF"})
    public ResponseEntity<EvaluationDTO> softDelete(@PathVariable("id") Long id) {
        Evaluation e = service.findById(id).orElse(null);

        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.softDelete(id);

        return new ResponseEntity<>(new EvaluationDTO(e.getId(), e.getStartTime(), e.getEndTime(), e.getNumberOfPoints(),
                new EvaluationTypeDTO(e.getEvaluationType().getId(), e.getEvaluationType().getName(),
                        null, e.getEvaluationType().getActive()),
                new EvaluationInstrumentDTO(e.getEvaluationInstrument().getId(),
                        e.getEvaluationInstrument().getName(),
                        new FileDTO(e.getEvaluationInstrument().getFile().getId(),
                                e.getEvaluationInstrument().getFile().getName(),
                                e.getEvaluationInstrument().getFile().getUrl(),
                                e.getEvaluationInstrument().getFile().getDescription(),
                                null, null,
                                new AnnouncementDTO(e.getEvaluationInstrument().getFile().getAnnouncement().getId(),
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getTimePublished(),
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getContent(), null,
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getTitle(), null,
                                        e.getEvaluationInstrument().getFile().getAnnouncement().getActive()),
                                null,
                                new StudentDTO(e.getEvaluationInstrument().getFile().getStudent().getId(),
                                        new RegisteredUserDTO(e.getEvaluationInstrument().getFile().getStudent().getUser().getUsername(), null, e.getEvaluationInstrument().getFile().getStudent().getUser().getEmail()),
                                        e.getEvaluationInstrument().getFile().getStudent().getFirstName(),
                                        e.getEvaluationInstrument().getFile().getStudent().getLastName(),
                                        e.getEvaluationInstrument().getFile().getStudent().getUmcn(), null, null, null, null, null),
                                e.getEvaluationInstrument().getFile().getDocument(),
                                e.getEvaluationInstrument().getFile().getActive()), null),
                null, null, null, null), HttpStatus.OK);
    }
}
