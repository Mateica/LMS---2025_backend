package main.mappers;

import java.util.List;
import java.util.stream.Collectors;

import main.dto.EvaluationDTO;
import main.dto.ExaminationDTO;
import main.dto.NoteDTO;
import main.model.Examination;
import main.model.Note;

public class NoteMapper {
	public static NoteDTO toDTO(Note t) {
        if (t == null) {
            return null;
        }

        NoteDTO dto = new NoteDTO();
        dto.setId(t.getId());
        dto.setContent(t.getContent());
        dto.setExamination(ExamMapper.toDTO(t.getExamination()));

        dto.setActive(t.getActive());

        return dto;
    }

    public static Note toEntity(NoteDTO dto) {
        if (dto == null) {
            return null;
        }

        Note note = new Note();
        note.setId(dto.getId());
        note.setContent(dto.getContent());
        note.setExamination(ExamMapper.toEntity(dto.getExamination()));



        note.setActive(dto.getActive() != null ? dto.getActive() : false);

        return note;
    }
}
