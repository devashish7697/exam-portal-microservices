package com.examservice.dto;
import java.util.List;

public class ExamRequest {
    private String title;
    private String description;
    private List<ExamQuestionRequest> questions;

    public ExamRequest() {}

    public ExamRequest(String title, String description, List<ExamQuestionRequest> questions) {
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExamQuestionRequest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ExamQuestionRequest> questions) {
        this.questions = questions;
    }
}

