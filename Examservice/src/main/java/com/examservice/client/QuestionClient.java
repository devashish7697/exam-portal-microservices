package com.examservice.client;

import com.examservice.dto.QuestionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Question-Service", url = "http://localhost:8082/api/questions")
public interface QuestionClient {

    @GetMapping("/{id}")
    QuestionResponse getQuestionById(@PathVariable("id") Long id);

    @GetMapping("/by-subject/{subject}")
    List<QuestionResponse> getQuestionsBySubject(@PathVariable("subject") String subject);

}
