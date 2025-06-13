package main.mappers;

import java.util.List;
import java.util.stream.Collectors;

import main.dto.EvaluationDTO;
import main.dto.ExaminationDTO;
import main.dto.NoteDTO;
import main.model.Evaluation;
import main.model.Examination;
import main.model.Note;

public class ExamMapper {
	public static ExaminationDTO toDTO(Examination examination) {
        if (examination == null) {
            return null;
        }

        ExaminationDTO dto = new ExaminationDTO();
        dto.setId(examination.getId());
        dto.setNumberOfPoints(examination.getNumberOfPoints());

        List<NoteDTO> noteDTOs = examination.getNotes()
            .stream()
            .map(NoteMapper::toDTO)
            .collect(Collectors.toList());
        dto.setNotes(noteDTOs);

        List<EvaluationDTO> evaluationDTOs = examination.getEvaluations()
            .stream()
            .map(EvaluationMapper::toDTO)
            .collect(Collectors.toList());
        dto.setEvaluations(evaluationDTOs);

        dto.setStudentOnYear(StudentOnYearMapper.toDTO(examination.getStudentOnYear()));

        dto.setActive(examination.getActive());

        return dto;
    }

    public static Examination toEntity(ExaminationDTO dto) {
        if (dto == null) {
            return null;
        }

        Examination examination = new Examination();
        examination.setId(dto.getId());
        examination.setNumberOfPoints(dto.getNumberOfPoints() != null ? dto.getNumberOfPoints() : 0);

        List<Note> notes = dto.getNotes()
            .stream()
            .map(NoteMapper::toEntity)
            .collect(Collectors.toList());
        examination.setNotes(notes);

        List<Evaluation> evaluations = dto.getEvaluations()
            .stream()
            .map(EvaluationMapper::toEntity)
            .collect(Collectors.toList());
        examination.setEvaluations(evaluations);

        examination.setStudentOnYear(StudentOnYearMapper.toEntity(dto.getStudentOnYear())); // Assuming StudentOnYearMapper exists

        examination.setActive(dto.getActive() != null ? dto.getActive() : Boolean.FALSE);

        return examination;
    }
}
