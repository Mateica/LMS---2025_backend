package main.mappers;

import main.dto.StudentOnYearDTO;
import main.model.StudentOnYear;

public class StudentOnYearMapper {
	    // Entity -> DTO
	    public static StudentOnYearDTO toDTO(StudentOnYear entity) {
	        if (entity == null) {
	            return null;
	        }

	        StudentOnYearDTO dto = new StudentOnYearDTO();
	        dto.setId(entity.getId());
	        dto.setDateOfApplication(entity.getDateOfApplication());

	        dto.setStudent(StudentMapper.toDTO(entity.getStudent())); // assuming StudentMapper exists

	        dto.setIndexNumber(entity.getIndexNumber());

	        dto.setYearOfStudy(YearOfStudyMapper.toDTO(entity.getYearOfStudy())); // assuming YearOfStudyMapper exists

	        List<ExaminationDTO> examinationDTOs = null;
	        if (entity.getExaminations() != null) {
	            examinationDTOs = entity.getExaminations()
	                .stream()
	                .map(ExaminationMapper::toDTO) // assuming ExaminationMapper exists
	                .collect(Collectors.toList());
	        }
	        dto.setExaminations(examinationDTOs);

	        dto.setStudentAffairsOffice(StudentAffairsOfficeMapper.toDTO(entity.getStudentAffairsOffice())); // assuming StudentAffairsOfficeMapper exists

	        dto.setActive(entity.getActive());

	        return dto;
	    }

	    // DTO -> Entity
	    public static StudentOnYear toEntity(StudentOnYearDTO dto) {
	        if (dto == null) {
	            return null;
	        }

	        StudentOnYear entity = new StudentOnYear();
	        entity.setId(dto.getId());
	        entity.setDateOfApplication(dto.getDateOfApplication());

	        entity.setStudent(StudentMapper.toEntity(dto.getStudent()));

	        entity.setIndexNumber(dto.getIndexNumber());

	        entity.setYearOfStudy(YearOfStudyMapper.toEntity(dto.getYearOfStudy()));

	        List<Examination> examinations = null;
	        if (dto.getExaminations() != null) {
	            examinations = dto.getExaminations()
	                .stream()
	                .map(ExaminationMapper::toEntity)
	                .collect(Collectors.toList());
	        }
	        entity.setExaminations(examinations);

	        entity.setStudentAffairsOffice(StudentAffairsOfficeMapper.toEntity(dto.getStudentAffairsOffice()));

	        entity.setActive(dto.getActive());

	        return entity;
	    }
	}
