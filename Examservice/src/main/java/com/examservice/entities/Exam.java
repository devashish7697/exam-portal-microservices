package com.examservice.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String discription;

//    @ElementCollection
//    private List<Long> questionsIds;

     // cascade = ALL -> saving exam also saves its examQuestion children.
     // orphanRemoval = true -> if an ExamQuestion is removed from the list, it will be deleted.
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamQuestion> questions = new ArrayList<>();

    public Exam(Long id, String title, String discription, List<ExamQuestion> questions) {
        this.id = id;
        this.title = title;
        this.discription = discription;
        this.questions = questions;
    }

    public Exam() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public List<ExamQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ExamQuestion> questions) {
        this.questions = questions;
    }
}
