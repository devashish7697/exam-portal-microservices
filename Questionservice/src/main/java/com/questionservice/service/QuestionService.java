package com.questionservice.service;

import com.questionservice.dto.QuestionRequest;
import com.questionservice.dto.QuestionResponse;

import java.util.List;

public interface QuestionService {
    QuestionResponse createQuestion(QuestionRequest request);

    List<QuestionResponse> getAllQuestions();

    QuestionResponse getQuestion(Long id);

    QuestionResponse updateQuestion(Long id, QuestionRequest request);

    void deleteQuestion(Long id);
}

