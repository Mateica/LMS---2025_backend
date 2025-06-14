package main.service;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import main.model.Examination;
import main.model.Faculty;
import main.model.Student;
import main.model.StudentOnYear;
import main.model.SubjectAttendance;
import main.model.Teacher;
import main.model.Title;
import main.model.University;
import main.repository.ExaminationRepository;
import main.repository.StudentRepository;
import main.repository.TeacherRepository;
import main.wrappers.TeacherWrapper;

@Service
public class ExportService {
	@Autowired 
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ExaminationRepository examRepository;
	
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
	
	public String exportTeachersToXML(List<Teacher> teachers) throws Exception {
		JAXBContext context = JAXBContext.newInstance(TeacherWrapper.class);
	    Marshaller marshaller = context.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	    TeacherWrapper wrapper = new TeacherWrapper();
	    wrapper.setTeachers(teachers);

	    StringWriter writer = new StringWriter();
	    marshaller.marshal(wrapper, writer);

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
	
	public byte[] exportTeachersToPDF(List<Teacher> teachers) {
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
	
	public String exportExaminationToXML(Examination exam) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Examination.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter writer = new StringWriter();
        marshaller.marshal(exam, writer);
        return writer.toString();
    }

    public byte[] exportExaminationToPDF(Examination exam) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Examination Details"));
        document.add(new Paragraph("Number of Points: " + exam.getNumberOfPoints()));

        if (exam.getNotes() != null) {
            document.add(new Paragraph("Notes:"));
            exam.getNotes().forEach(note -> 
                document.add(new Paragraph("- " + note.getContent()))
            );
        }

        if (exam.getEvaluations() != null) {
            document.add(new Paragraph("Evaluations:"));
            exam.getEvaluations().forEach(e -> 
                document.add(new Paragraph("- " + e.getStartTime() + " to " + e.getEndTime() + 
                    " Type: " + (e.getEvaluationType() != null ? e.getEvaluationType().getName() : "N/A") +
                    ", Instrument: " + (e.getEvaluationInstrument() != null ? e.getEvaluationInstrument().getName() : "N/A")
                ))
            );
        }

        if (exam.getStudentOnYear() != null) {
            document.add(new Paragraph("Student: " + exam.getStudentOnYear().getIndexNumber()));
        }

        document.close();
        return baos.toByteArray();
    }
    
    public byte[] exportExaminationsToPDF(List<Examination> exams) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        for (Examination exam : exams) {
        	document.add(new Paragraph("Number of points: "+exam.getNumberOfPoints()));
            document.add(new Paragraph("Subject: " + exam.getEvaluations().getFirst().getSubjectRealization().getSubject().getName()));
            document.add(new Paragraph("Start Date: " + exam.getEvaluations().getLast().getStartTime().toString()));
            document.add(new Paragraph("End Date: " + exam.getEvaluations().getLast().getEndTime().toString()));
            document.add(new Paragraph("Grade: " + exam.getEvaluations().getLast().getEvaluationGrades().getLast().getMark()));
            document.add(new Paragraph("\n"));
        }

        document.close();
        return baos.toByteArray();
    }

	
	
}
