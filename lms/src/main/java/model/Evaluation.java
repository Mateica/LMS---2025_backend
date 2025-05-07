package model;

import java.time.LocalDateTime;

public class Evaluation {
	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int numberOfPoints;
	private EvaluationType evaluationType;
	private EvaluationInstrument evaluationInstrument;
	private Examination examination;
}
