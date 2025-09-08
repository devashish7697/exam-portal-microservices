package com.examservice.dto;

public class ExamQuestionRequest {
        private Long questionId;
        private Integer marks;

        public ExamQuestionRequest() {}

    public ExamQuestionRequest(Long questionId, Integer marks) {
        this.questionId = questionId;
        this.marks = marks;
    }

    public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }

        public Integer getMarks() { return marks; }
        public void setMarks(Integer marks) { this.marks = marks; }
}

