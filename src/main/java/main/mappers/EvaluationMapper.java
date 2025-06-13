package main.mappers;

import java.util.List;
import java.util.stream.Collectors;

import main.dto.EvaluationDTO;
import main.dto.EvaluationGradeDTO;
import main.model.Evaluation;
import main.model.EvaluationGrade;

public class EvaluationMapper {
	public static EvaluationDTO toDTO(Evaluation evaluation) {
        if (evaluation == null) {
            return null;
        }

        EvaluationDTO dto = new EvaluationDTO();
        dto.setId(evaluation.getId());
        dto.setStartTime(evaluation.getStartTime());
        dto.setEndTime(evaluation.getEndTime());
        dto.setNumberOfPoints(evaluation.getNumberOfPoints());

        dto.setEvaluationType(EvaluationTypeMapper.toDTO(evaluation.getEvaluationType()));
        dto.setEvaluationInstrument(EvaluationInstrumentMapper.toDTO(evaluation.getEvaluationInstrument())); 
        dto.setExamination(ExamMapper.toDTO(evaluation.getExamination())); 
        dto.setSubjectRealization(SubjectRealizationMapper.toDTO(evaluation.getSubjectRealization())); 

        List<EvaluationGradeDTO> evaluationGradeDTOs = evaluation.getEvaluationGrades()
            .stream()
            .map(EvaluationGradeMapper::toDTO)
            .collect(Collectors.toList());
        dto.setEvaluationGrades(evaluationGradeDTOs);

        dto.setActive(evaluation.getActive());

        return dto;
    }

    public static Evaluation toEntity(EvaluationDTO dto) {
        if (dto == null) {
            return null;
        }

        Evaluation evaluation = new Evaluation();
        evaluation.setId(dto.getId());
        evaluation.setStartTime(dto.getStartTime());
        evaluation.setEndTime(dto.getEndTime());
        evaluation.setNumberOfPoints(dto.getNumberOfPoints());

        evaluation.setEvaluationType(EvaluationTypeMapper.toEntity(dto.getEvaluationType())); 
        evaluation.setEvaluationInstrument(EvaluationInstrumentMapper.toEntity(dto.getEvaluationInstrument()));
        evaluation.setExamination(ExamMapper.toEntity(dto.getExamination()));
        evaluation.setSubjectRealization(SubjectRealizationMapper.toEntity(dto.getSubjectRealization()));

        List<EvaluationGrade> evaluationGrades = dto.getEvaluationGrades()
            .stream()
            .map(EvaluationGradeMapper::toEntity)
            .collect(Collectors.toList());
        evaluation.setEvaluationGrades(evaluationGrades);

        evaluation.setActive(dto.getActive());

        return evaluation;
    }
}
