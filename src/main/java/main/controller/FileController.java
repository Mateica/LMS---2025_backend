package main.controller;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ser.std.FileSerializer;

import main.dto.AnnouncementDTO;
import main.dto.EvaluationDTO;
import main.dto.FileDTO;
import main.dto.MessageDTO;
import main.dto.RegisteredUserDTO;
import main.dto.StudentDTO;
import main.dto.TeacherDTO;
import main.model.File;
import main.model.Teacher;
import main.service.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController implements ControllerInterface<FileDTO> {
	@Autowired
	private FileService service;
	
	@GetMapping("/download/{id}")
	@Secured({"ADMIN","TEACHER","STAFF", "STUDENT", "USER"})
	public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
	    Optional<File> optionalFile = service.findById(id);

	    if (optionalFile.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    File file = optionalFile.get();

	    return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_PDF)
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
	            .body(file.getDocument());
	}

	
	@PostMapping("/upload")
	@Secured({"ADMIN","TEACHER","STAFF", "STUDENT", "USER"})
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile mpf, 
			@RequestParam("studentId") Long studentId, @RequestParam("evalId") Long evalId, 
			@RequestParam("description") String description, @RequestParam("url") String url) {
		File file = service.upload(mpf, studentId, evalId, description, url);

		if(file == null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("File uploaded successfully!", HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Iterable<FileDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<FileDTO> files = new ArrayList<FileDTO>();
		
		for(File f : service.findAll()) {
			files.add(new FileDTO(f.getId(), f.getName(), f.getUrl(), f.getDescription(),
					null, null,
					null,null, null,
					f.getDocument(), f.getActive()));
		}
		
		return new ResponseEntity<Iterable<FileDTO>>(files, HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Page<FileDTO>> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
		// TODO Auto-generated method stub
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<File> filePage = service.findAll(pageable);

	    List<FileDTO> fileDTOs = filePage.stream().map(f ->
	    
	    new FileDTO(f.getId(), f.getName(), f.getUrl(), f.getDescription(),
				null, null,
				null,null, null,
				f.getDocument(), f.getActive()))
	    		.collect(Collectors.toList());

	    Page<FileDTO> resultPage = new PageImpl<FileDTO>(fileDTOs, pageable, filePage.getTotalElements());

	    return new ResponseEntity<Page<FileDTO>>(resultPage, HttpStatus.OK);
	}
	
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<Iterable<FileDTO>> findAllActive() {
		// TODO Auto-generated method stub
		ArrayList<FileDTO> files = new ArrayList<FileDTO>();
		
		for(File f : service.findAllActive()) {
			files.add(new FileDTO(f.getId(), f.getName(), f.getUrl(), f.getDescription(),
					null, null,
					null,null, null,
					f.getDocument(), f.getActive()));
		}
		
		return new ResponseEntity<Iterable<FileDTO>>(files, HttpStatus.OK);
	}
	

	@Override
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<FileDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		File f = service.findById(id).orElse(null);
		
		if(f == null) {
			return new ResponseEntity<FileDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<FileDTO>(new FileDTO(f.getId(), f.getName(), f.getUrl(), f.getDescription(),
					null, null,
					null,null, null,
					f.getDocument(), f.getActive()), HttpStatus.OK);
	}

	@Override
	@Secured({"ADMIN","TEACHER","STAFF", "STUDENT", "USER"})
	public ResponseEntity<FileDTO> create(@RequestBody FileDTO t) {
		// TODO Auto-generated method stub
		File f  = service.create(new File(null, t.getName(), t.getUrl(), t.getDescription(),
											service.findById(t.getId()).get().getPost(),
											service.findById(t.getId()).get().getMessage(),
											service.findById(t.getId()).get().getAnnouncement(),
											service.findById(t.getId()).get().getEvaluation(),
											service.findById(t.getId()).get().getStudent(), t.getDocument(), true));
		
		if(f == null) {
			return new ResponseEntity<FileDTO>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<FileDTO>(new FileDTO(f.getId(), f.getName(), f.getUrl(), f.getDescription(),
					null, null,
					null,null, null,
					f.getDocument(), f.getActive()), HttpStatus.CREATED);
	}

	@Override
	@Secured({"ADMIN","TEACHER","STAFF", "STUDENT", "USER"})
	public ResponseEntity<FileDTO> update(@RequestBody FileDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		File f = service.findById(id).orElse(null);
		
		if(f == null) {
			return new ResponseEntity<FileDTO>(HttpStatus.NOT_FOUND);
		}
		
		f.setId(t.getId());
		f.setName(t.getName());
		f.setUrl(t.getUrl());
		f.setDescription(t.getDescription());
		f.setPost(service.findById(t.getId()).get().getPost());
		f.setMessage(service.findById(t.getId()).get().getMessage());
		f.setAnnouncement(service.findById(t.getId()).get().getAnnouncement());
		f.setEvaluation(service.findById(t.getId()).get().getEvaluation());
		f.setStudent(service.findById(t.getId()).get().getStudent());
		f.setDocument(t.getDocument());
		f.setActive(t.getActive());
		
		f = service.update(f);
		
		return new ResponseEntity<FileDTO>(new FileDTO(f.getId(), f.getName(), f.getUrl(), f.getDescription(),
					null, null,
					null,null, null,
					f.getDocument(), f.getActive()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<FileDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Secured({"ADMIN","TEACHER","STAFF"})
	public ResponseEntity<FileDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		File f = service.findById(id).orElse(null);
		
		if(f == null) {
			return new ResponseEntity<FileDTO>(HttpStatus.NOT_FOUND);
		}
		
		service.softDelete(id);
		
		return new ResponseEntity<FileDTO>(new FileDTO(f.getId(), f.getName(), f.getUrl(), f.getDescription(),
					null, null,
					null,null, null,
					f.getDocument(), f.getActive()), HttpStatus.OK);
	}
	
}
