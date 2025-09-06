package com.examservice.controller;

import com.examservice.dto.ExamRequest;
import com.examservice.dto.ExamResponse;
import com.examservice.entities.Exam;
import com.examservice.services.ExamServiceImplements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private ExamServiceImplements service;

    public ExamController(ExamServiceImplements service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExamResponse> create(@RequestBody ExamRequest request) {
        Exam exam = service.createExam(request);
        ExamResponse response = toResponse(exam);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExamResponse>> getAll() {
        List<ExamResponse> response = service.getAllExams();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResponse> getExam(@PathVariable Long id) {
        ExamResponse response = service.getExam(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamResponse> update(@PathVariable Long id,
                                               @RequestBody ExamRequest request) {
        Exam exam = service.updateExam(id, request);
        return ResponseEntity.ok(toResponse(exam));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.removeExam(id);
        return ResponseEntity.ok("Exam deleted successfully with id = " + id);
    }

    private ExamResponse toResponse(Exam exam) {
        ExamResponse response = new ExamResponse();
        response.setId(exam.getId());
        response.setTitle(exam.getTitle());
        response.setDescription(exam.getDiscription());
        return response;
    }
}
