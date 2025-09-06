package com.examservice.services;

import com.examservice.client.QuestionClient;
import com.examservice.dto.ExamRequest;
import com.examservice.dto.ExamResponse;
import com.examservice.dto.QuestionResponse;
import com.examservice.entities.Exam;
import com.examservice.repositories.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamServiceImplements implements ExamService{

    private ExamRepository repo;
    private QuestionClient questionClient;

    public ExamServiceImplements(ExamRepository repo,QuestionClient questionClient) {
        this.repo = repo;
        this.questionClient = questionClient;
    }

    @Override
    public Exam createExam(ExamRequest request) {
        Exam exam = new Exam();
        exam.setTitle(request.getTitle());
        exam.setDiscription(request.getDescription());
        exam.setQuestionsIds(request.getQuestionIds());

        return repo.save(exam);
    }

    @Override
    public List<ExamResponse> getAllExams() {
        List<Exam> exams =  repo.findAll();
        return exams.stream()
                .map( exam -> {
                   List<QuestionResponse> questions = exam.getQuestionsIds().stream()
                           .map(questionClient::getQuestionById).collect(Collectors.toList());

                    // build response
                    ExamResponse response = new ExamResponse();
                    response.setId(exam.getId());
                    response.setTitle(exam.getTitle());
                    response.setDescription(exam.getDiscription());
                    response.setQuestions(questions);

                    return response;
                }).collect(Collectors.toList());

    }

    @Override
    public ExamResponse getExam(Long examId) {
        Exam exam = repo.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " +examId));

        // fetch question from question MS
        List<QuestionResponse> questions = exam.getQuestionsIds().stream()
                .map(questionClient::getQuestionById)
                .collect(Collectors.toList());

        // build response
        ExamResponse response = new ExamResponse();
        response.setId(exam.getId());
        response.setTitle(exam.getTitle());
        response.setDescription(exam.getDiscription());
        response.setQuestions(questions);

        return response;
    }

    public Exam getExamById(Long id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Exam Not Found With ID : "+id));
    }

    @Override
    public Exam updateExam(Long id, ExamRequest updatedExamRequest) {
        Exam exam = getExamById(id);
        exam.setTitle(updatedExamRequest.getTitle());
        exam.setDiscription(updatedExamRequest.getDescription());
        exam.setQuestionsIds(updatedExamRequest.getQuestionIds());

        return repo.save(exam);
    }

    @Override
    public void removeExam(Long id) {
        Exam exam = getExamById(id);
        repo.delete(exam);
    }

}
