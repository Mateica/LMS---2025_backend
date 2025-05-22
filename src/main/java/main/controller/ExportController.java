package main.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import main.dto.StudentDTO;
import main.dto.TeacherDTO;
import main.dto.TitleDTO;
import main.model.Address;
import main.model.Country;
import main.model.Department;
import main.model.Faculty;
import main.model.File;
import main.model.Place;
import main.model.RegisteredUser;
import main.model.Student;
import main.model.StudyProgramme;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.TeachingMaterial;
import main.model.Title;
import main.model.University;
import main.service.ExportService;
import main.service.StudentService;
import main.service.TeacherService;

@RestController
@RequestMapping("/api/export")
public class ExportController {
	@Autowired
	private ExportService service;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private StudentService studentService;
	
	@PostMapping(path = "/xml", params = "type=teacher", produces = "application/xml")
	public ResponseEntity<String> exportTeacherToXML(@RequestBody TeacherDTO t) {
		try {
			String xml = service.exportTeacherToXML(new Teacher(t.getId(), 
					new RegisteredUser(t.getUser().getId(), t.getUser().getUsername(), null, t.getUser().getEmail(), null, null, null, t.getActive()),
					t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(), new ArrayList<Title>(), 
					new ArrayList<TeacherOnRealization>(), 
					new TeachingMaterial(t.getTeachingMaterial().getId(),
							t.getTeachingMaterial().getName(), new ArrayList<Teacher>(), t.getTeachingMaterial().getYearOfPublication(),
							new File(t.getTeachingMaterial().getFile().getId(), t.getTeachingMaterial().getFile().getUrl(),
									t.getTeachingMaterial().getFile().getDescription(), null, null, null,
									t.getTeachingMaterial().getFile().getActive()), t.getTeachingMaterial().getActive()),
					new Department(t.getDepartment().getId(), t.getDepartment().getName(),
							t.getDepartment().getDescription(), 
							new Faculty(t.getDepartment().getFaculty().getId(), t.getDepartment().getFaculty().getName(),
									new Address(t.getDepartment().getFaculty().getAddress().getId(),
											t.getDepartment().getFaculty().getAddress().getStreet(),
											t.getDepartment().getFaculty().getAddress().getHouseNumber(),
											new Place(t.getDepartment().getFaculty().getAddress().getPlace().getId(),
													t.getDepartment().getFaculty().getAddress().getPlace().getName(),
													new Country(t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getId(),
															t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getName(),
															new ArrayList<Place>(), 
															t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getActive()), 
													t.getDepartment().getFaculty().getAddress().getPlace().getActive()),
											t.getDepartment().getFaculty().getAddress().getActive()),
									new Teacher(t.getDepartment().getFaculty().getHeadmaster().getId(), 
											new RegisteredUser(t.getDepartment().getFaculty().getHeadmaster().getUser().getId(), 
													t.getDepartment().getFaculty().getHeadmaster().getUser().getUsername(), null, 
													t.getDepartment().getFaculty().getHeadmaster().getUser().getEmail(), null, null, null,
													t.getDepartment().getFaculty().getHeadmaster().getActive()),
											t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(), null, null, null, null, null) ,
									new University(t.getDepartment().getFaculty().getUniversity().getId(),
											t.getDepartment().getFaculty().getUniversity().getName(),
											t.getDepartment().getFaculty().getUniversity().getDateEstablished(),
											new Address(t.getDepartment().getFaculty().getUniversity().getAddress().getId(),
													t.getDepartment().getFaculty().getUniversity().getAddress().getStreet(),
													t.getDepartment().getFaculty().getUniversity().getAddress().getHouseNumber(),
													new Place(t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getId(),
															t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getName(),
															new Country(t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
																	t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
																	new ArrayList<Place>(), 
																	t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()), 
															t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getActive()),
													t.getDepartment().getFaculty().getAddress().getActive()),
											new Teacher(t.getDepartment().getFaculty().getUniversity().getRector().getId(), 
													new RegisteredUser(t.getDepartment().getFaculty().getUniversity().getRector().getUser().getId(), 
															t.getDepartment().getFaculty().getUniversity().getRector().getUser().getUsername(), null, 
															t.getDepartment().getFaculty().getUniversity().getRector().getUser().getEmail(),
															null, null, null, t.getDepartment().getFaculty().getUniversity().getRector().getActive()),
													t.getDepartment().getFaculty().getUniversity().getRector().getFirstName(),
													t.getDepartment().getFaculty().getUniversity().getRector().getLastName(), 
													t.getDepartment().getFaculty().getUniversity().getRector().getUmcn(),
													t.getDepartment().getFaculty().getUniversity().getRector().getBiography(),
													null, null, null, null, null),
											t.getDepartment().getFaculty().getUniversity().getContactDetails(),
											t.getDepartment().getFaculty().getUniversity().getDescription(),
											new ArrayList<Faculty>(), t.getDepartment().getFaculty().getUniversity().getActive()),
									t.getDepartment().getFaculty().getContactDetails(), 
									t.getDepartment().getFaculty().getDescription(), new HashSet<Department>(),
									new ArrayList<StudyProgramme>(), t.getDepartment().getFaculty().getActive()),
							new HashSet<Teacher>(),
							new Teacher(t.getDepartment().getChief().getId(), 
									new RegisteredUser(t.getDepartment().getChief().getUser().getId(), 
											t.getDepartment().getChief().getUser().getUsername(), null, 
											t.getDepartment().getChief().getUser().getEmail(), null, null, null, t.getDepartment().getChief().getActive()),
									t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(), null, null, null, null, null),
							new HashSet<StudyProgramme>(), t.getDepartment().getActive()), t.getActive()));
			
			return new ResponseEntity<String>(xml, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PostMapping(path = "/pdf", params = "type=teacher", produces = "application/pdf")
	public ResponseEntity<byte[]> exportTeacherToPDF(@RequestBody  TeacherDTO t){
		try {
			byte[] pdf = service.exportTeacherToPDF(new Teacher(t.getId(), 
					new RegisteredUser(t.getUser().getId(), t.getUser().getUsername(), null, t.getUser().getEmail(), null, null, null, t.getActive()),
					t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(), new ArrayList<Title>(), 
					new ArrayList<TeacherOnRealization>(), 
					new TeachingMaterial(t.getTeachingMaterial().getId(),
							t.getTeachingMaterial().getName(), new ArrayList<Teacher>(), t.getTeachingMaterial().getYearOfPublication(),
							new File(t.getTeachingMaterial().getFile().getId(), t.getTeachingMaterial().getFile().getUrl(),
									t.getTeachingMaterial().getFile().getDescription(), null, null, null,
									t.getTeachingMaterial().getFile().getActive()), t.getTeachingMaterial().getActive()),
					new Department(t.getDepartment().getId(), t.getDepartment().getName(),
							t.getDepartment().getDescription(), 
							new Faculty(t.getDepartment().getFaculty().getId(), t.getDepartment().getFaculty().getName(),
									new Address(t.getDepartment().getFaculty().getAddress().getId(),
											t.getDepartment().getFaculty().getAddress().getStreet(),
											t.getDepartment().getFaculty().getAddress().getHouseNumber(),
											new Place(t.getDepartment().getFaculty().getAddress().getPlace().getId(),
													t.getDepartment().getFaculty().getAddress().getPlace().getName(),
													new Country(t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getId(),
															t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getName(),
															new ArrayList<Place>(), 
															t.getDepartment().getFaculty().getAddress().getPlace().getCountry().getActive()), 
													t.getDepartment().getFaculty().getAddress().getPlace().getActive()),
											t.getDepartment().getFaculty().getAddress().getActive()),
									new Teacher(t.getDepartment().getFaculty().getHeadmaster().getId(), 
											new RegisteredUser(t.getDepartment().getFaculty().getHeadmaster().getUser().getId(), 
													t.getDepartment().getFaculty().getHeadmaster().getUser().getUsername(), null, 
													t.getDepartment().getFaculty().getHeadmaster().getUser().getEmail(), null, null, null,
													t.getDepartment().getFaculty().getHeadmaster().getActive()),
											t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(), null, null, null, null, null) ,
									new University(t.getDepartment().getFaculty().getUniversity().getId(),
											t.getDepartment().getFaculty().getUniversity().getName(),
											t.getDepartment().getFaculty().getUniversity().getDateEstablished(),
											new Address(t.getDepartment().getFaculty().getUniversity().getAddress().getId(),
													t.getDepartment().getFaculty().getUniversity().getAddress().getStreet(),
													t.getDepartment().getFaculty().getUniversity().getAddress().getHouseNumber(),
													new Place(t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getId(),
															t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getName(),
															new Country(t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getCountry().getId(),
																	t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getCountry().getName(),
																	new ArrayList<Place>(), 
																	t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getCountry().getActive()), 
															t.getDepartment().getFaculty().getUniversity().getAddress().getPlace().getActive()),
													t.getDepartment().getFaculty().getAddress().getActive()),
											new Teacher(t.getDepartment().getFaculty().getUniversity().getRector().getId(), 
													new RegisteredUser(t.getDepartment().getFaculty().getUniversity().getRector().getUser().getId(), 
															t.getDepartment().getFaculty().getUniversity().getRector().getUser().getUsername(), null, 
															t.getDepartment().getFaculty().getUniversity().getRector().getUser().getEmail(),
															null, null, null, t.getDepartment().getFaculty().getUniversity().getRector().getActive()),
													t.getDepartment().getFaculty().getUniversity().getRector().getFirstName(),
													t.getDepartment().getFaculty().getUniversity().getRector().getLastName(), 
													t.getDepartment().getFaculty().getUniversity().getRector().getUmcn(),
													t.getDepartment().getFaculty().getUniversity().getRector().getBiography(),
													null, null, null, null, null),
											t.getDepartment().getFaculty().getUniversity().getContactDetails(),
											t.getDepartment().getFaculty().getUniversity().getDescription(),
											new ArrayList<Faculty>(), t.getDepartment().getFaculty().getUniversity().getActive()),
									t.getDepartment().getFaculty().getContactDetails(), 
									t.getDepartment().getFaculty().getDescription(), new HashSet<Department>(),
									new ArrayList<StudyProgramme>(), t.getDepartment().getFaculty().getActive()),
							new HashSet<Teacher>(),
							new Teacher(t.getDepartment().getChief().getId(), 
									new RegisteredUser(t.getDepartment().getChief().getUser().getId(), 
											t.getDepartment().getChief().getUser().getUsername(), null, 
											t.getDepartment().getChief().getUser().getEmail(), null, null, null, t.getDepartment().getChief().getActive()),
									t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(), null, null, null, null, null),
							new HashSet<StudyProgramme>(), t.getDepartment().getActive()), t.getActive()));
			
			return new ResponseEntity<byte[]>(pdf,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PostMapping(path = "/xml", params = "type=teachers", produces = "application/xml")
	public ResponseEntity<ArrayList<String>> exportTeachersToXML(){
		ArrayList<String> xmlValues = new ArrayList<>();
        for (Teacher teacher : teacherService.findAll()) {
            try {
                xmlValues.add(service.exportTeacherToXML(teacher));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<ArrayList<String>>(xmlValues,HttpStatus.OK);
	}
	
	@PostMapping(path = "/pdf",params = "type=teachers", produces = "application/pdf")
	public ResponseEntity<ArrayList<byte[]>> exportTeachersToPDF(){
		ArrayList<byte[]> pdfValues = new ArrayList<byte[]>();
		for (Teacher teacher : teacherService.findAll()) {
            try {
                pdfValues.add(service.exportTeacherToPDF(teacher));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		return new ResponseEntity<ArrayList<byte[]>>(pdfValues, HttpStatus.OK);
	}
	
	@PostMapping(path = "/xml", params = "type=student", produces = "application/xml")
	public ResponseEntity<String> exportStudentToXML(@RequestBody StudentDTO s) {
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(path = "/xml", params = "type=students", produces = "application/xml")
	public ResponseEntity<ArrayList<String>> exportStudentsToXML(){
		ArrayList<String> xmlValues = new ArrayList<String>();

		for(Student s : studentService.findAll()) {
			try {
				String xml = service.exportStudentToXML(s);
				xmlValues.add(xml);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseEntity<ArrayList<String>>(xmlValues, HttpStatus.OK);
	}
	
    @PostMapping(path = "/pdf", params = "type=student", produces = "application/pdf")
	public ResponseEntity<byte[]> exportStudentToPDF(@RequestBody StudentDTO s) {
		return new ResponseEntity<byte[]>(HttpStatus.OK);
	}
	
	
    @PostMapping(path = "/pdf", params = "type=students", produces = "application/pdf")
	public ResponseEntity<ArrayList<byte[]>> exportStudentsToPDF(){
		ArrayList<byte[]> pdfValues = new ArrayList<byte[]>();
		
		return new ResponseEntity<ArrayList<byte[]>>(pdfValues, HttpStatus.OK);
	}
	
	
}
