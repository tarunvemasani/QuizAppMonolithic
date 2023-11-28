package com.hero.quizapp.controller;

import com.hero.quizapp.model.QuestionWrapper;
import com.hero.quizapp.model.Quiz;
import com.hero.quizapp.model.Response;
import com.hero.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> addQuiz(@RequestParam String category,@RequestParam Integer numQ, @RequestParam String title){
        return quizService.addQuiz(category,numQ,title);

    }

    @GetMapping("getQuiz")
    public ResponseEntity<List<Quiz>> getQuiz(){

        return quizService.getQuiz();
    }

    @GetMapping("getQuizById/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiZById(@PathVariable Integer id){
        return quizService.getQuizById(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);
    }
}
