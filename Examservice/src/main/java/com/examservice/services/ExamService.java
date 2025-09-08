package com.examservice.services;

import com.examservice.dto.ExamRequest;
import com.examservice.dto.ExamResponse;
import com.examservice.entities.Exam;

import java.util.List;

public interface ExamService {

    public ExamResponse createExam(ExamRequest request);
    public List<ExamResponse> getAllExams();
    public ExamResponse getExam(Long examId);

    public ExamResponse updateExam(Long id, ExamRequest updatedExamRequest);
    public void removeExam(Long id);
}
