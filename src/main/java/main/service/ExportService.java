package main.service;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import main.dto.StudentDTO;
import main.dto.TeacherDTO;
import main.dto.TitleDTO;
import main.model.Address;
import main.model.Department;
import main.model.Faculty;
import main.model.Student;
import main.model.StudentOnYear;
import main.model.SubjectAttendance;
import main.model.Teacher;
import main.model.Title;
import main.model.University;
import main.repository.StudentRepository;
import main.repository.TeacherRepository;

@Service
public class ExportService {
	@Autowired 
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	public String exportTeacherToXML(Teacher t) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Teacher.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter writer = new StringWriter();
		marshaller.marshal(new Teacher(t.getId(), t.getUser(), t.getFirstName(), t.getLastName(), t.getUmcn(),
				t.getBiography(), t.getTitles(), t.getTeachersOnRealization(),
				null, t.getTeachingMaterial(), t.getDepartment(), t.getActive()), writer);
		return writer.toString();
	}
	
	public String exportTeachersToXML() throws Exception {
		JAXBContext context = JAXBContext.newInstance(Teacher.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter writer = new StringWriter();
		
		this.teacherRepository.findByActiveIsTrue().forEach(t -> {
			try {
				marshaller.marshal(new Teacher(t.getId(), t.getUser(), t.getFirstName(), t.getLastName(), t.getUmcn(),
						t.getBiography(), t.getTitles(), t.getTeachersOnRealization(),
						null, t.getTeachingMaterial(), t.getDepartment(), t.getActive()), writer);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return writer.toString();
	}
	
	public byte[] exportTeacherToPDF(Teacher t) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);

	    for(Title title : t.getTitles()) {
	    document.add(new Paragraph("First Name: " + t.getFirstName() + ", Last Name: "
	    + t.getLastName() +", UMCN: "+ t.getUmcn()+ ", Biography: "+t.getBiography() +", Titles: "
	    		+ t.getTitles() + ", Subjects: "
	    + t.getTeachersOnRealization().getFirst().getSubjectRealization().getSubject().getName() + ", Teaching Material: "
	    + t.getTeachingMaterial().getName() + " " + t.getTeachingMaterial().getAuthors().getFirst().getFirstName() + " "
	    + t.getTeachingMaterial().getAuthors().getFirst().getLastName() + " "+ t.getTeachingMaterial().getYearOfPublication() 
	    +", Department: "+ t.getDepartment().getName() + " "+ t.getDepartment().getDescription() + " "
	    + t.getDepartment().getFaculty().getName() + " "+t.getDepartment().getFaculty().getAddress().getStreet() + " "
	    +t.getDepartment().getFaculty().getAddress().getHouseNumber()));
	    }
	    
	    document.close();
	    return baos.toByteArray();
	}
	
	public byte[] exportTeachersToPDF() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);

	    this.teacherRepository.findByActiveIsTrue().forEach(t -> {
	    	for(Title title : t.getTitles()) {
	    	    document.add(new Paragraph("First Name: " + t.getFirstName() + ", Last Name: "
	    	    + t.getLastName() +", UMCN: "+ t.getUmcn()+ ", Biography: "+t.getBiography() +", Titles: "
	    	    		+ t.getTitles() + ", Subjects: "
	    	    + t.getTeachersOnRealization().getFirst().getSubjectRealization().getSubject().getName() + ", Teaching Material: "
	    	    + t.getTeachingMaterial().getName() + " " + t.getTeachingMaterial().getAuthors().getFirst().getFirstName() + " "
	    	    + t.getTeachingMaterial().getAuthors().getFirst().getLastName() + " "+ t.getTeachingMaterial().getYearOfPublication() 
	    	    +", Department: "+ t.getDepartment().getName() + " "+ t.getDepartment().getDescription() + " "
	    	    + t.getDepartment().getFaculty().getName() + " "+t.getDepartment().getFaculty().getAddress().getStreet() + " "
	    	    +t.getDepartment().getFaculty().getAddress().getHouseNumber()));
	    	    }
	    });
	    
	    document.close();
	    return baos.toByteArray();
	}
	
	public String exportStudentToXML(Student s) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Student.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter writer = new StringWriter();
		marshaller.marshal(new Student(s.getId(), s.getUser(), s.getFirstName(), s.getLastName(), s.getUmcn(),
				s.getAddress(), s.getStudentsOnYear(), s.getSubjectAttendances(), null, s.getActive()), writer);
		return writer.toString();
	}
	
	public String exportStudentsToXML() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Student.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter writer = new StringWriter();
		this.studentRepository.findByActiveIsTrue().forEach(s -> {
			try {
				marshaller.marshal(new Student(s.getId(), s.getUser(), s.getFirstName(), s.getLastName(), s.getUmcn(),
						s.getAddress(), s.getStudentsOnYear(), s.getSubjectAttendances(),
						s.getFaculty(),s.getActive()), writer);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		return writer.toString();
	}
	
	public byte[] exportStudentToPDF(Student s) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);
	    
	   ArrayList<String> indexNumbers  = new ArrayList<String>();
	   ArrayList<SubjectAttendance> attendedCourses = new ArrayList<SubjectAttendance>();
	   
	   indexNumbers = (ArrayList<String>) s.getStudentsOnYear()
			   			.stream()
			   			.map(i -> i.getIndexNumber())
			   			.collect(Collectors.toList());
	   
	   attendedCourses = (ArrayList<SubjectAttendance>) s.getSubjectAttendances()
			   .stream()
			   .map(a-> new SubjectAttendance(a.getId(), a.getFinalGrade(), a.getSubjectRealization(), s, a.getActive()))
			   .collect(Collectors.toList());
	    		
	   document.add(new Paragraph("First Name: " + s.getFirstName() + ", Last Name: "
	    	+ s.getLastName() +", UMCN: "+ s.getUmcn()+ ", Address: "+s.getAddress().getStreet() + " "
	    	+s.getAddress().getHouseNumber() + ", "+s.getAddress().getPlace().getName() +", "
	   +s.getAddress().getPlace().getCountry().getName() +", Index Number(s): "
	    	+ indexNumbers
	    	+ ", Attends courses: " + attendedCourses 
	    	+ ", Faculty: "+s.getFaculty().getName() +
	    	", "+s.getFaculty().getAddress().getStreet()+" "+s.getFaculty().getAddress().getHouseNumber()));
	    
	    document.close();
	    return baos.toByteArray();
	   }
	
	public byte[] exportStudentsToPDF() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);
	    
	    this.studentRepository.findByActiveIsTrue().forEach(s ->{
	    	new Student(s.getId(),s.getUser(),s.getFirstName(), s.getLastName(), s.getUmcn(),
	    			s.getAddress(),
	    			s.getStudentsOnYear(), s.getSubjectAttendances(), s.getFaculty(), s.getActive());
	    });
	    
	    document.close();
	    return baos.toByteArray();
	}
	
	
}
