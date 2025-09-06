package com.questionservice.repositories;

import com.questionservice.entites.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findBySubject(String subject);  // filter by subject
}
