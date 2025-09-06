package com.questionservice.service;

import com.questionservice.dto.QuestionRequest;
import com.questionservice.dto.QuestionResponse;
import com.questionservice.entites.Question;
import com.questionservice.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImplements implements QuestionService {

    private final QuestionRepository repo;

    public QuestionServiceImplements(QuestionRepository repo) {
        this.repo = repo;
    }

    @Override
    public QuestionResponse createQuestion(QuestionRequest request) {
        Question q = toEntity(request);
        Question saved = repo.save(q);
        return toResponse(saved);
    }

    @Override
    public List<QuestionResponse> getAllQuestions() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public QuestionResponse getQuestion(Long id) {
        Question q = repo.findById(id).orElseThrow(() -> new RuntimeException("Question not found: " + id));
        return toResponse(q);
    }

    @Override
    public QuestionResponse updateQuestion(Long id, QuestionRequest request) {
        Question q = repo.findById(id).orElseThrow(() -> new RuntimeException("Question not found: " + id));
        q.setQuestionText(request.getText());
        q.setOptionA(request.getOptionA());
        q.setOptionB(request.getOptionB());
        q.setOptionC(request.getOptionC());
        q.setOptionD(request.getOptionD());
        q.setCorrectAnswer(request.getCorrectAnswer());
        q.setSubject(request.getSubject());
        Question updated = repo.save(q);
        return toResponse(updated);
    }

    @Override
    public void deleteQuestion(Long id) {
        repo.deleteById(id);
    }

    // --- mapping helpers ---
    private Question toEntity(QuestionRequest r) {
        Question q = new Question();
        q.setQuestionText(r.getText());
        q.setOptionA(r.getOptionA());
        q.setOptionB(r.getOptionB());
        q.setOptionC(r.getOptionC());
        q.setOptionD(r.getOptionD());
        q.setCorrectAnswer(r.getCorrectAnswer());
        q.setSubject(r.getSubject());
        return q;
    }

    private QuestionResponse toResponse(Question q) {
        QuestionResponse res = new QuestionResponse();
        res.setId(q.getId());
        res.setText(q.getQuestionText());
        res.setOptionA(q.getOptionA());
        res.setOptionB(q.getOptionB());
        res.setOptionC(q.getOptionC());
        res.setOptionD(q.getOptionD());
        res.setSubject(q.getSubject());
        return res;
    }
}

