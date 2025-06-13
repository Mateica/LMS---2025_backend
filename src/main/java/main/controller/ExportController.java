package main.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
import main.model.Account;
import main.model.Address;
import main.model.Country;
import main.model.Department;
import main.model.Evaluation;
import main.model.Examination;
import main.model.Faculty;
import main.model.File;
import main.model.ForumUser;
import main.model.Outcome;
import main.model.Place;
import main.model.RegisteredUser;
import main.model.Role;
import main.model.Student;
import main.model.StudentAffairsOffice;
import main.model.StudentOnYear;
import main.model.StudentServiceStaff;
import main.model.StudyProgramme;
import main.model.Subject;
import main.model.SubjectAttendance;
import main.model.SubjectRealization;
import main.model.Teacher;
import main.model.TeacherOnRealization;
import main.model.TeachingMaterial;
import main.model.Title;
import main.model.University;
import main.model.YearOfStudy;
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
					null, new TeachingMaterial(t.getTeachingMaterial().getId(),
							t.getTeachingMaterial().getName(), new ArrayList<Teacher>(), t.getTeachingMaterial().getYearOfPublication(),
							new File(t.getTeachingMaterial().getFile().getId(), t.getTeachingMaterial().getFile().getUrl(),
									t.getTeachingMaterial().getFile().getDescription(), null, null, null,
									null, null, null, t.getTeachingMaterial().getFile().getActive()), t.getTeachingMaterial().getActive()),
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
											t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(), null, null, null, null, null, null) ,
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
													null, null, null, null, null, null),
											t.getDepartment().getFaculty().getUniversity().getContactDetails(),
											t.getDepartment().getFaculty().getUniversity().getDescription(),
											new ArrayList<Faculty>(), t.getDepartment().getFaculty().getUniversity().getActive()),
									t.getDepartment().getFaculty().getContactDetails(), 
									t.getDepartment().getFaculty().getDescription(), new HashSet<Department>(),
									new ArrayList<StudyProgramme>(), null, null, t.getDepartment().getFaculty().getActive()),
							new HashSet<Teacher>(),
							new Teacher(t.getDepartment().getChief().getId(), 
									new RegisteredUser(t.getDepartment().getChief().getUser().getId(), 
											t.getDepartment().getChief().getUser().getUsername(), null, 
											t.getDepartment().getChief().getUser().getEmail(), null, null, null, t.getDepartment().getChief().getActive()),
									t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(), null, null, null, null, null, null),
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
					null, new TeachingMaterial(t.getTeachingMaterial().getId(),
							t.getTeachingMaterial().getName(), new ArrayList<Teacher>(), t.getTeachingMaterial().getYearOfPublication(),
							new File(t.getTeachingMaterial().getFile().getId(), t.getTeachingMaterial().getFile().getUrl(),
									t.getTeachingMaterial().getFile().getDescription(), null, null, null,
									null, null, null, t.getTeachingMaterial().getFile().getActive()), t.getTeachingMaterial().getActive()),
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
											t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(), null, null, null, null, null, null) ,
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
													null, null, null, null, null, null),
											t.getDepartment().getFaculty().getUniversity().getContactDetails(),
											t.getDepartment().getFaculty().getUniversity().getDescription(),
											new ArrayList<Faculty>(), t.getDepartment().getFaculty().getUniversity().getActive()),
									t.getDepartment().getFaculty().getContactDetails(), 
									t.getDepartment().getFaculty().getDescription(), new HashSet<Department>(),
									new ArrayList<StudyProgramme>(), null, null, t.getDepartment().getFaculty().getActive()),
							new HashSet<Teacher>(),
							new Teacher(t.getDepartment().getChief().getId(), 
									new RegisteredUser(t.getDepartment().getChief().getUser().getId(), 
											t.getDepartment().getChief().getUser().getUsername(), null, 
											t.getDepartment().getChief().getUser().getEmail(), null, null, null, t.getDepartment().getChief().getActive()),
									t.getFirstName(), t.getLastName(), t.getUmcn(), t.getBiography(), null, null, null, null, null, null),
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
		try {
			HashSet<StudentOnYear> studentOnYears = new HashSet<StudentOnYear>();
			ArrayList<SubjectAttendance> attendedCourses = new ArrayList<SubjectAttendance>();
			
			studentOnYears = (HashSet<StudentOnYear>) s.getStudentsOnYear().stream().map(soy -> 
			new StudentOnYear(soy.getId(), soy.getDateOfApplication(),
					new Student(null,
							new RegisteredUser(soy.getStudent().getUser().getId(),
									soy.getStudent().getUser().getUsername(),
									soy.getStudent().getUser().getPassword(), s.getUser().getEmail(),
									null, new ArrayList<Account>(),
									new HashSet<Role>(), soy.getStudent().getUser().getActive()),
							soy.getStudent().getFirstName(), soy.getStudent().getLastName(), soy.getStudent().getUmcn(), 
							new Address(soy.getStudent().getAddress().getId(),
									soy.getStudent().getAddress().getStreet(), 
									soy.getStudent().getAddress().getHouseNumber(), 
									new Place(soy.getStudent().getAddress().getPlace().getId(),
											soy.getStudent().getAddress().getPlace().getName(),
											new Country(soy.getStudent().getAddress().getPlace().getCountry().getId(),
													soy.getStudent().getAddress().getPlace().getCountry().getName(),
													new ArrayList<Place>(), 
													soy.getStudent().getAddress().getPlace().getCountry().getActive()), 
											soy.getStudent().getAddress().getPlace().getActive()),
									soy.getStudent().getAddress().getActive()),
							new HashSet<StudentOnYear>(), new ArrayList<SubjectAttendance>(), 
							new Faculty(),
							soy.getStudent().getActive()), soy.getIndexNumber(),
					new YearOfStudy(soy.getYearOfStudy().getId(), 
							soy.getYearOfStudy().getYearOfStudy(),
							new ArrayList<Subject>(), soy.getYearOfStudy().getActive()),
					new ArrayList<Examination>(),
					null,
					soy.getActive()))
					.collect(Collectors.toSet());
			attendedCourses = (ArrayList<SubjectAttendance>) s.getSubjectAttendances().stream().map(a ->
			new SubjectAttendance(a.getId(), a.getFinalGrade(),
					new SubjectRealization(a.getSubjectRealization().getId(), null,
							null,
							null,
							new Subject(a.getSubjectRealization().getSubject().getId(),
									a.getSubjectRealization().getSubject().getName(),
									a.getSubjectRealization().getSubject().getEcts(),
									a.getSubjectRealization().getSubject().isCompulsory(),
									a.getSubjectRealization().getSubject().getNumberOfClasses(),
									a.getSubjectRealization().getSubject().getNumberOfPractices(),
									a.getSubjectRealization().getSubject().getOtherTypesOfClasses(),
									a.getSubjectRealization().getSubject().getResearchWork(),
									a.getSubjectRealization().getSubject().getClassesLeft(),
									a.getSubjectRealization().getSubject().getNumberOfSemesters(),
									new YearOfStudy(a.getSubjectRealization().getSubject().getYearOfStudy().getId(),
											a.getSubjectRealization().getSubject().getYearOfStudy().getYearOfStudy(),
											null, a.getSubjectRealization().getSubject().getYearOfStudy().getActive()),
									new ArrayList<Outcome>(),
									new ArrayList<SubjectRealization>(),
									null, a.getSubjectRealization().getSubject().getActive()),
							a.getSubjectRealization().getActive()),
					new Student(a.getStudent().getId(), null,
							a.getStudent().getFirstName(), 
							a.getStudent().getLastName(),
							a.getStudent().getUmcn(),
							new Address(a.getStudent().getAddress().getId(), a.getStudent().getAddress().getStreet(),
									a.getStudent().getAddress().getHouseNumber(), null, a.getStudent().getAddress().getActive()),
							new HashSet<StudentOnYear>(), new ArrayList<SubjectAttendance>(), 
							new Faculty(a.getStudent().getFaculty().getId(),
									a.getStudent().getFaculty().getName(),
									new Address(a.getStudent().getFaculty().getAddress().getId(),
											a.getStudent().getFaculty().getAddress().getStreet(),
											a.getStudent().getFaculty().getAddress().getHouseNumber(), null, 
											a.getStudent().getFaculty().getAddress().getActive()),
									new Teacher(a.getStudent().getFaculty().getHeadmaster().getId(), null,
											a.getStudent().getFaculty().getHeadmaster().getFirstName(),
											a.getStudent().getFaculty().getHeadmaster().getLastName(),
											a.getStudent().getFaculty().getHeadmaster().getUmcn(),
											a.getStudent().getFaculty().getHeadmaster().getBiography(),
											null, null, null, null, null, a.getStudent().getFaculty().getHeadmaster().getActive()),
									new University(a.getStudent().getFaculty().getUniversity().getId(),
											a.getStudent().getFaculty().getName(), a.getStudent().getFaculty().getUniversity().getDateEstablished(),
											new Address(a.getStudent().getFaculty().getUniversity().getAddress().getId(),
													a.getStudent().getFaculty().getUniversity().getAddress().getStreet(),
													a.getStudent().getFaculty().getUniversity().getAddress().getHouseNumber(), null, 
													a.getStudent().getFaculty().getUniversity().getAddress().getActive()), 
											new Teacher(a.getStudent().getFaculty().getUniversity().getRector().getId(), null,
															a.getStudent().getFaculty().getUniversity().getRector().getFirstName(),
															a.getStudent().getFaculty().getUniversity().getRector().getLastName(),
															a.getStudent().getFaculty().getUniversity().getRector().getUmcn(),
															a.getStudent().getFaculty().getUniversity().getRector().getBiography(),
															null, null, null, null, null, 
															a.getStudent().getFaculty().getUniversity().getRector().getActive()),
											null, null, null,
											a.getStudent().getFaculty().getUniversity().getRector().getActive()),
									a.getStudent().getFaculty().getContactDetails(),
									a.getStudent().getFaculty().getDescription(),
									new HashSet<Department>(),
									new ArrayList<StudyProgramme>(),
									new ArrayList<Student>(),
									new StudentAffairsOffice(a.getStudent().getFaculty().getStudentAffairsOffice().getId(),
											new ArrayList<StudentServiceStaff>(), null, a.getStudent().getFaculty().getStudentAffairsOffice().getActive()),
									a.getStudent().getFaculty().getActive()),
							a.getStudent().getActive()),
					a.getActive()))
					.collect(Collectors.toList());
			
			
			
			String xml = service.exportStudentToXML(new Student(null,
					new RegisteredUser(null, s.getUser().getUsername(), s.getUser().getPassword(), s.getUser().getEmail(), null, 
							new ArrayList<Account>(),
							new HashSet<Role>(), s.getUser().getActive()),
					s.getFirstName(), s.getLastName(), s.getUmcn(), 
					new Address(s.getAddress().getId(),
							s.getAddress().getStreet(),
							s.getAddress().getHouseNumber(), 
							new Place(s.getAddress().getPlace().getId(), s.getAddress().getPlace().getName(), 
									new Country(s.getAddress().getPlace().getCountry().getId(),
											s.getAddress().getPlace().getCountry().getName(),
											new ArrayList<Place>(),
											s.getAddress().getPlace().getCountry().getActive()), 
									s.getAddress().getPlace().getCountry().getActive()), 
							s.getAddress().getActive()),
					studentOnYears, attendedCourses, 
					new Faculty(s.getFaculty().getId(),
							s.getFaculty().getName(),
							new Address(s.getFaculty().getAddress().getId(),
									s.getFaculty().getAddress().getStreet(),
									s.getFaculty().getAddress().getHouseNumber(), null, 
									s.getFaculty().getAddress().getActive()),
							new Teacher(s.getFaculty().getHeadmaster().getId(), null,
									s.getFaculty().getHeadmaster().getFirstName(),
									s.getFaculty().getHeadmaster().getLastName(),
									s.getFaculty().getHeadmaster().getUmcn(),
									s.getFaculty().getHeadmaster().getBiography(),
									null, null, null, null, null,
									s.getFaculty().getHeadmaster().getActive()),
							new University(s.getFaculty().getUniversity().getId(),
									s.getFaculty().getName(), s.getFaculty().getUniversity().getDateEstablished(),
									new Address(s.getFaculty().getUniversity().getAddress().getId(),
											s.getFaculty().getUniversity().getAddress().getStreet(),
											s.getFaculty().getUniversity().getAddress().getHouseNumber(), null, 
											s.getFaculty().getUniversity().getAddress().getActive()), 
									new Teacher(s.getFaculty().getUniversity().getRector().getId(), null,
											s.getFaculty().getUniversity().getRector().getFirstName(),
											s.getFaculty().getUniversity().getRector().getLastName(),
											s.getFaculty().getUniversity().getRector().getUmcn(),
											s.getFaculty().getUniversity().getRector().getBiography(),
													null, null, null, null, null, 
													s.getFaculty().getUniversity().getRector().getActive()),
									null, null, null,
									s.getFaculty().getUniversity().getRector().getActive()),
							s.getFaculty().getContactDetails(),
							s.getFaculty().getDescription(),
							new HashSet<Department>(),
							new ArrayList<StudyProgramme>(),
							new ArrayList<Student>(),
							new StudentAffairsOffice(s.getFaculty().getStudentAffairsOffice().getId(),
									new ArrayList<StudentServiceStaff>(), null, s.getFaculty().getStudentAffairsOffice().getActive()),
							s.getFaculty().getActive()), s.getActive()));
			return new ResponseEntity<String>(xml,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
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
		try {
			HashSet<StudentOnYear> studentOnYears = new HashSet<StudentOnYear>();
			ArrayList<SubjectAttendance> attendedCourses = new ArrayList<SubjectAttendance>();
			
			byte[] pdf = service.exportStudentToPDF(new Student(null,
					new RegisteredUser(null, s.getUser().getUsername(), s.getUser().getPassword(), s.getUser().getEmail(), null, 
							new ArrayList<Account>(),
							new HashSet<Role>(), s.getUser().getActive()),
					s.getFirstName(), s.getLastName(), s.getUmcn(), 
					new Address(s.getAddress().getId(),
							s.getAddress().getStreet(),
							s.getAddress().getHouseNumber(), 
							new Place(s.getAddress().getPlace().getId(), s.getAddress().getPlace().getName(), 
									new Country(s.getAddress().getPlace().getCountry().getId(),
											s.getAddress().getPlace().getCountry().getName(),
											new ArrayList<Place>(),
											s.getAddress().getPlace().getCountry().getActive()), 
									s.getAddress().getPlace().getCountry().getActive()), 
							s.getAddress().getActive()),
					studentOnYears, attendedCourses, 
					new Faculty(s.getFaculty().getId(),
							s.getFaculty().getName(),
							new Address(s.getFaculty().getAddress().getId(),
									s.getFaculty().getAddress().getStreet(),
									s.getFaculty().getAddress().getHouseNumber(), null, 
									s.getFaculty().getAddress().getActive()),
							new Teacher(s.getFaculty().getHeadmaster().getId(), null,
									s.getFaculty().getHeadmaster().getFirstName(),
									s.getFaculty().getHeadmaster().getLastName(),
									s.getFaculty().getHeadmaster().getUmcn(),
									s.getFaculty().getHeadmaster().getBiography(),
									null, null, null, null, null,
									s.getFaculty().getHeadmaster().getActive()),
							new University(s.getFaculty().getUniversity().getId(),
									s.getFaculty().getName(), s.getFaculty().getUniversity().getDateEstablished(),
									new Address(s.getFaculty().getUniversity().getAddress().getId(),
											s.getFaculty().getUniversity().getAddress().getStreet(),
											s.getFaculty().getUniversity().getAddress().getHouseNumber(), null, 
											s.getFaculty().getUniversity().getAddress().getActive()), 
									new Teacher(s.getFaculty().getUniversity().getRector().getId(), null,
											s.getFaculty().getUniversity().getRector().getFirstName(),
											s.getFaculty().getUniversity().getRector().getLastName(),
											s.getFaculty().getUniversity().getRector().getUmcn(),
											s.getFaculty().getUniversity().getRector().getBiography(),
													null, null, null, null, null, 
													s.getFaculty().getUniversity().getRector().getActive()),
									null, null, null,
									s.getFaculty().getUniversity().getRector().getActive()),
							s.getFaculty().getContactDetails(),
							s.getFaculty().getDescription(),
							new HashSet<Department>(),
							new ArrayList<StudyProgramme>(),
							new ArrayList<Student>(),
							new StudentAffairsOffice(s.getFaculty().getStudentAffairsOffice().getId(),
									new ArrayList<StudentServiceStaff>(), null, s.getFaculty().getStudentAffairsOffice().getActive()),
							s.getFaculty().getActive()), s.getActive()));
			
			return new ResponseEntity<byte[]>(pdf,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(HttpStatus.OK);
	}
	
	
    @PostMapping(path = "/pdf", params = "type=students", produces = "application/pdf")
	public ResponseEntity<ArrayList<byte[]>> exportStudentsToPDF(){
    	ArrayList<byte[]> pdfValues = new ArrayList<byte[]>();
		for (Student student : studentService.findAll()) {
            try {
                pdfValues.add(service.exportStudentToPDF(student));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		return new ResponseEntity<ArrayList<byte[]>>(pdfValues, HttpStatus.OK);
	}
	
	
}
