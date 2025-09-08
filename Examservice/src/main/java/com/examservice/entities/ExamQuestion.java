package com.examservice.entities;

import jakarta.persistence.*;

@Entity()
@Table(name = "exam_question")
public class ExamQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The id of the question in Question microservice
    @Column(name = "question_id", nullable = false)
    private Long questionId;

    // Marks assigned to this question inside this exam
    @Column(name = "marks", nullable = false)
    private Integer marks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    public ExamQuestion() {}

    public ExamQuestion(Long id, Long questionId, Integer marks, Exam exam) {
        this.id = id;
        this.questionId = questionId;
        this.marks = marks;
        this.exam = exam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
