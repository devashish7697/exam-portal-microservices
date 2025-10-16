package com.examservice.controller;

import com.examservice.dto.ExamRequest;
import com.examservice.dto.ExamResponse;
import com.examservice.entities.Exam;
import com.examservice.services.ExamServiceImplements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private ExamServiceImplements service;

    public ExamController(ExamServiceImplements service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExamResponse> create(@RequestBody ExamRequest request) {
        ExamResponse response = service.createExam(request);
        return ResponseEntity.created(URI.create("exams/" + response.getId())).body(response);
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
        ExamResponse updated = service.updateExam(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.removeExam(id);
        return ResponseEntity.ok("Exam deleted successfully with id = " + id);
    }

}
