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
import org.springframework.web.bind.annotation.*;

import main.dto.EvaluationInstrumentDTO;
import main.dto.FileDTO;
import main.model.EvaluationInstrument;
import main.service.EvaluationInstrumentService;
import main.model.File;

@RestController
@RequestMapping("api/evaluationInstruments")
public class EvaluationInstrumentController implements ControllerInterface<EvaluationInstrumentDTO> {
    @Autowired
    private EvaluationInstrumentService service;

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @GetMapping
    public ResponseEntity<Iterable<EvaluationInstrumentDTO>> findAll() {
        ArrayList<EvaluationInstrumentDTO> evaluationInstruments = new ArrayList<>();

        for (EvaluationInstrument ei : service.findAll()) {
            FileDTO fileDTO = null;
            File file = ei.getFile();
            if (file != null) {
                fileDTO = new FileDTO(
                        file.getId(),
                        file.getUrl(),
                        file.getDescription(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        file.getActive()
                );
            }

            evaluationInstruments.add(new EvaluationInstrumentDTO(
                    ei.getId(),
                    ei.getName(),
                    fileDTO,
                    ei.getActive()));
        }

        return new ResponseEntity<>(evaluationInstruments, HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @GetMapping("/params")
    public ResponseEntity<Page<EvaluationInstrumentDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size,
                                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                                 @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EvaluationInstrument> evaluationTypePage = service.findAll(pageable);

        List<EvaluationInstrumentDTO> evaluationTypeDTOs = evaluationTypePage.stream().map(ei -> {
                    FileDTO fileDTO = null;
                    File file = ei.getFile();
                    if (file != null) {
                        fileDTO = new FileDTO(
                                file.getId(),
                                file.getUrl(),
                                file.getDescription(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                file.getActive()
                        );
                    }

                    return new EvaluationInstrumentDTO(
                            ei.getId(),
                            ei.getName(),
                            fileDTO,
                            ei.getActive()
                    );
                })
                .collect(Collectors.toList());

        Page<EvaluationInstrumentDTO> resultPage = new PageImpl<>(evaluationTypeDTOs, pageable, evaluationTypePage.getTotalElements());

        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationInstrumentDTO> findById(@PathVariable Long id) {
        EvaluationInstrument ei = service.findById(id).orElse(null);

        if (ei == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new EvaluationInstrumentDTO(ei.getId(), ei.getName(),
                new FileDTO(ei.getFile().getId(), ei.getFile().getUrl(), ei.getFile().getDescription(),
                        null, null, null, null, null, null, null, ei.getFile().getActive()), ei.getActive()), HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @PostMapping
    public ResponseEntity<EvaluationInstrumentDTO> create(@RequestBody EvaluationInstrumentDTO t) {
        File file = null;
        FileDTO fileDTO = t.getFile();
        if (fileDTO != null) {
            file = new File(t.getFile().getId(), t.getFile().getUrl(), t.getFile().getDescription(), null, null, null, null, null, null, t.getFile().getActive());
        }

        EvaluationInstrument ei = service.create(new EvaluationInstrument(null, t.getName(), null, file, true));

        if (ei == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        FileDTO createdFileDTO = null;
        File createdFile = ei.getFile();
        if (createdFile != null) {
            createdFileDTO = new FileDTO(t.getFile().getId(), t.getFile().getUrl(), t.getFile().getDescription(), null, null, null, null, null, null, null, t.getFile().getActive());
        }

        return new ResponseEntity<>(new EvaluationInstrumentDTO(ei.getId(), ei.getName(), createdFileDTO, ei.getActive()), HttpStatus.CREATED);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationInstrumentDTO> update(@RequestBody EvaluationInstrumentDTO t, @PathVariable Long id) {
        EvaluationInstrument ei = service.findById(id).orElse(null);

        if (ei == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ei.setId(t.getId());
        ei.setName(t.getName());
        ei.setActive(t.getActive());
        ei.setFile(new File(t.getFile().getId(), t.getFile().getUrl(), t.getFile().getDescription(),
                null, null, null, null, null, null, t.getFile().getActive()));

        ei = service.update(ei);

        return new ResponseEntity<>(new EvaluationInstrumentDTO(ei.getId(), ei.getName(),
                new FileDTO(ei.getFile().getId(), ei.getFile().getUrl(), ei.getFile().getDescription(),
                        null, null, null, null, null, null, null, ei.getFile().getActive()), ei.getActive()), HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<EvaluationInstrumentDTO> delete(@PathVariable Long id) {
        return null;
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @PatchMapping("/{id}")
    public ResponseEntity<EvaluationInstrumentDTO> softDelete(@PathVariable Long id) {
        EvaluationInstrument ei = service.findById(id).orElse(null);

        if (ei == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.softDelete(id);

        return new ResponseEntity<>(new EvaluationInstrumentDTO(ei.getId(), ei.getName(),
                new FileDTO(ei.getFile().getId(), ei.getFile().getUrl(), ei.getFile().getDescription(),
                        null, null, null, null, null, null, null, ei.getFile().getActive()), ei.getActive()), HttpStatus.OK);
    }
}
