package com.examservice.dto;

public class ExamQuestionResponse {

    private QuestionResponse question;
    private Integer marks;

    public ExamQuestionResponse() {}

    public ExamQuestionResponse(QuestionResponse question, Integer marks) {
        this.question = question;
        this.marks = marks;
    }

    public QuestionResponse getQuestion() { return question; }
    public void setQuestion(QuestionResponse question) { this.question = question; }

    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }

}
