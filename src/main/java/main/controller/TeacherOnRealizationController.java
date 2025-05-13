package main.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.dto.SubjectRealizationDTO;
import main.dto.TeacherDTO;
import main.dto.TeacherOnRealizationDTO;
import main.service.TeacherOnRealizationService;

@RestController
@RequestMapping("/api/teacherOnRealization")
public class TeacherOnRealizationController implements ControllerInterface<TeacherOnRealizationDTO> {
	@Autowired
	private TeacherOnRealizationService service;

	@Override
	public ResponseEntity<Iterable<TeacherOnRealizationDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Page<TeacherOnRealizationDTO>> findAll(int page, int size, String sortBy, boolean ascending) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@GetMapping("/teacherSubjects/{id}")
	@Secured({"ADMIN", "TEACHER"})
	public ResponseEntity<Iterable<TeacherOnRealizationDTO>> findByTeacher(Long id){
		ArrayList<TeacherOnRealizationDTO> teachersOnRealization = new ArrayList<TeacherOnRealizationDTO>();
		
		teachersOnRealization.stream().map(t -> new TeacherOnRealizationDTO());
		
		return new ResponseEntity<Iterable<TeacherOnRealizationDTO>>(teachersOnRealization, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TeacherOnRealizationDTO> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TeacherOnRealizationDTO> create(TeacherOnRealizationDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TeacherOnRealizationDTO> update(TeacherOnRealizationDTO t, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TeacherOnRealizationDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TeacherOnRealizationDTO> softDelete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
