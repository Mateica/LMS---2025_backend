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

import main.dto.EvaluationTypeDTO;
import main.model.EvaluationType;
import main.service.EvaluationTypeService;

@RestController
@RequestMapping("/api/evaluationTypes")
public class EvaluationTypeController implements ControllerInterface<EvaluationTypeDTO> {
    @Autowired
    private EvaluationTypeService service;

    @Override
    @Secured({"ADMIN", "TEACHER", "STAFF"})
    @GetMapping
    public ResponseEntity<Iterable<EvaluationTypeDTO>> findAll() {
        ArrayList<EvaluationTypeDTO> evaluationTypes = new ArrayList<>();

        for (EvaluationType et : service.findAll()) {
            evaluationTypes.add(new EvaluationTypeDTO(et.getId(), et.getName(), null, et.getActive()));
        }

        return new ResponseEntity<>(evaluationTypes, HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @GetMapping("/params")
    public ResponseEntity<Page<EvaluationTypeDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EvaluationType> evaluationTypePage = service.findAll(pageable);

        List<EvaluationTypeDTO> evaluationTypeDTOs = evaluationTypePage.stream().map(et ->
                new EvaluationTypeDTO(
                        et.getId(),
                        et.getName(),
                        null, et.getActive()
                )
        ).collect(Collectors.toList());

        Page<EvaluationTypeDTO> resultPage = new PageImpl<>(evaluationTypeDTOs, pageable, evaluationTypePage.getTotalElements());

        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationTypeDTO> findById(@PathVariable Long id) {
        EvaluationType et = service.findById(id).orElse(null);

        if (et == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new EvaluationTypeDTO(et.getId(), et.getName(), null, et.getActive()), HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @PostMapping
    public ResponseEntity<EvaluationTypeDTO> create(@RequestBody EvaluationTypeDTO t) {
        EvaluationType et = service.create(new EvaluationType(null, t.getName(), List.of(), true));

        if (et == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new EvaluationTypeDTO(et.getId(), et.getName(), List.of(), et.getActive()), HttpStatus.CREATED);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationTypeDTO> update(@RequestBody EvaluationTypeDTO t, @PathVariable Long id) {
        EvaluationType et = service.findById(id).orElse(null);

        if (et == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        et.setId(t.getId());
        et.setName(t.getName());
        et.setActive(t.getActive());

        et = service.update(et);
        return new ResponseEntity<>(new EvaluationTypeDTO(et.getId(), et.getName(), null, et.getActive()), HttpStatus.OK);
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<EvaluationTypeDTO> delete(@PathVariable Long id) {
        return null;
    }

    @Override
    @Secured({"ADMIN", "TEACHER"})
    @PatchMapping("/{id}")
    public ResponseEntity<EvaluationTypeDTO> softDelete(@PathVariable Long id) {
        EvaluationType et = service.findById(id).orElse(null);

        if (et == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.softDelete(id);

        return new ResponseEntity<>(new EvaluationTypeDTO(et.getId(), et.getName(), null, et.getActive()), HttpStatus.OK);
    }
}
