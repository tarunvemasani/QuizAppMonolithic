package com.hero.quizapp.dao;

import com.hero.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "select * from Question q where q.category=:category limit :numQ",nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, Integer numQ);
}
