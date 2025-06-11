package main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EvaluationDTO {
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer numberOfPoints;

    private EvaluationTypeDTO evaluationType;

    private EvaluationInstrumentDTO evaluationInstrument;

    private ExaminationDTO examination;

    private SubjectRealizationDTO subjectRealization;

    private List<EvaluationGradeDTO> evaluationGrades = new ArrayList<>();

    private Boolean active;

    public EvaluationDTO() {
        super();
    }

    public EvaluationDTO(Long id, LocalDateTime startTime, LocalDateTime endTime, Integer numberOfPoints,
                         EvaluationTypeDTO evaluationType, EvaluationInstrumentDTO evaluationInstrument, ExaminationDTO examination,
                         SubjectRealizationDTO subjectRealization, List<EvaluationGradeDTO> evaluationGrades, Boolean active) {
        super();
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfPoints = numberOfPoints;
        this.evaluationType = evaluationType;
        this.evaluationInstrument = evaluationInstrument;
        this.examination = examination;
        this.subjectRealization = subjectRealization;
        this.evaluationGrades = evaluationGrades;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(Integer numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public EvaluationTypeDTO getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(EvaluationTypeDTO evaluationType) {
        this.evaluationType = evaluationType;
    }

    public EvaluationInstrumentDTO getEvaluationInstrument() {
        return evaluationInstrument;
    }

    public void setEvaluationInstrument(EvaluationInstrumentDTO evaluationInstrument) {
        this.evaluationInstrument = evaluationInstrument;
    }

    public ExaminationDTO getExamination() {
        return examination;
    }

    public void setExamination(ExaminationDTO examination) {
        this.examination = examination;
    }

    public SubjectRealizationDTO getSubjectRealization() {
        return subjectRealization;
    }

    public void setSubjectRealization(SubjectRealizationDTO subjectRealization) {
        this.subjectRealization = subjectRealization;
    }

    public List<EvaluationGradeDTO> getEvaluationGrades() {
        return evaluationGrades;
    }

    public void setEvaluationGrades(List<EvaluationGradeDTO> evaluationGrades) {
        this.evaluationGrades = evaluationGrades;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}