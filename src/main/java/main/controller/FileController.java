package main.controller;

import java.io.FileDescriptor;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ser.std.FileSerializer;

import main.dto.FileDTO;
import main.model.File;
import main.service.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController implements ControllerInterface<FileDTO> {
	@Autowired
	private FileService service;
	
	@GetMapping("/download/{id}")
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
	public ResponseEntity<Iterable<FileDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Page<FileDTO>> findAll(int page, int size, String sortBy, boolean ascending) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<FileDTO> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<FileDTO> create(FileDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<FileDTO> update(FileDTO t, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<FileDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<FileDTO> softDelete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
