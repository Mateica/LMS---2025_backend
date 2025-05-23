package main.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import main.model.Evaluation;
import main.model.EvaluationGrade;
import main.model.File;
import main.model.Student;
import main.repository.EvaluationRepository;
import main.repository.FileRepository;
import main.repository.StudentRepository;

@Service
public class FileService implements ServiceInterface<File> {
	@Autowired
	private FileRepository fileRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private EvaluationRepository evalRepo;

	@Override
	public Iterable<File> findAll() {
		// TODO Auto-generated method stub
		return this.fileRepo.findAll();
	}

	@Override
	public Page<File> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.fileRepo.findAll(pageable);
	}

	@Override
	public Optional<File> findById(Long id) {
		// TODO Auto-generated method stub
		return this.fileRepo.findById(id);
	}

	@Override
	public File create(File t) {
		// TODO Auto-generated method stub
		return this.fileRepo.save(t);
	}

	@Override
	public File update(File t) {
		// TODO Auto-generated method stub
		if(fileRepo.findById(t.getId()).isPresent()) {
			return this.fileRepo.save(t);
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
        File f = findById(id).orElse(null);
		
		if(f != null) {
			f.setActive(false);
			fileRepo.save(f);
		}
	}
	
	public File upload(MultipartFile mpf, Long studentId, Long evalId, String description, String url) {
		Student student = studentRepo.findById(studentId).orElse(null);
		Evaluation evaluation = evalRepo.findById(evalId).orElse(null);
		
		if(student == null || evaluation == null) {
			return null;
		}
		
		File file = new File();
		file.setName(mpf.getOriginalFilename());
		file.setDescription(description);
		file.setUrl(url);
		try {
			file.setDocument(mpf.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file.setStudent(student);
		file.setEvaluation(evaluation);
		file.setActive(true);
		
		return fileRepo.save(file);
	}
}
