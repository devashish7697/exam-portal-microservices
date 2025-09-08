package com.examservice.services;

import com.examservice.client.QuestionClient;
import com.examservice.dto.*;
import com.examservice.entities.Exam;
import com.examservice.entities.ExamQuestion;
import com.examservice.repositories.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ExamResponse createExam(ExamRequest request) {
        Exam exam = new Exam();
        exam.setTitle(request.getTitle());
        exam.setDiscription(request.getDescription());

        List<ExamQuestion> examQuestions = new ArrayList<>();
        if (request.getQuestions() != null){
            for (ExamQuestionRequest qreq : request.getQuestions()){
                if (qreq.getQuestionId() == null || qreq.getMarks() == null) continue;
                ExamQuestion eq = new ExamQuestion();
                eq.setQuestionId(qreq.getQuestionId());
                eq.setMarks(qreq.getMarks());
                eq.setExam(exam);
                examQuestions.add(eq);
            }
        }
        exam.setQuestions(examQuestions);
        Exam save = repo.save(exam);
        return buildResponse(save);
    }


    @Override
    public List<ExamResponse> getAllExams() {
        List<Exam> exams =  repo.findAll();
        return exams.stream().map(this::buildResponse).collect(Collectors.toList());
    }

    @Override
    public ExamResponse getExam(Long examId) {
        Exam exam = repo.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " +examId));

        return buildResponse(exam);
    }

    public Exam getExamById(Long id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Exam Not Found With ID : "+id));
    }

    @Override
    public ExamResponse updateExam(Long id, ExamRequest updatedExamRequest) {
        Exam exam = getExamById(id);

        exam.setTitle(updatedExamRequest.getTitle());
        exam.setDiscription(updatedExamRequest.getDescription());
        // remove existing question to give new one
        exam.getQuestions().clear();
        if (updatedExamRequest.getQuestions() != null){
            for (ExamQuestionRequest qreq : updatedExamRequest.getQuestions()){
                if (qreq.getQuestionId() == null || qreq.getMarks() == null) continue;
                ExamQuestion eq = new ExamQuestion();
                eq.setQuestionId(qreq.getQuestionId());
                eq.setMarks(qreq.getMarks());
                eq.setExam(exam);
                exam.getQuestions().add(eq);
            }
        }
        Exam save = repo.save(exam);
        return buildResponse(save);
    }

    @Override
    public void removeExam(Long id) {
        Exam exam = getExamById(id);
        repo.delete(exam);
    }

    private ExamResponse buildResponse(Exam exam) {
        ExamResponse examResponse = new ExamResponse();
        examResponse.setId(exam.getId());
        examResponse.setDescription(exam.getDiscription());
        examResponse.setTitle(exam.getTitle());

        List<ExamQuestionResponse> questionResponses = new ArrayList<>();
        for (ExamQuestion eq : exam.getQuestions()){
            QuestionResponse qdto;
            try {
                qdto = questionClient.getQuestionById(eq.getQuestionId());
            } catch (Exception e){
                qdto = new QuestionResponse();
                qdto.setId(eq.getQuestionId());
                qdto.setText("Question Unavailable id : "+eq.getQuestionId());
                e.printStackTrace();
            }
            ExamQuestionResponse eqr = new ExamQuestionResponse();
            eqr.setQuestion(qdto);
            eqr.setMarks(eq.getMarks());
            questionResponses.add(eqr);
        }
        examResponse.setQuestions(questionResponses);
        return examResponse;
    }

}
