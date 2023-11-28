package com.hero.quizapp.service;

import com.hero.quizapp.dao.QuestionDao;
import com.hero.quizapp.model.Question;
import com.hero.quizapp.dao.QuizDao;
import com.hero.quizapp.model.QuestionWrapper;
import com.hero.quizapp.model.Quiz;
import com.hero.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<String> addQuiz(String category, Integer numQ, String title) {

        List<Question> question=questionDao.findRandomQuestionsByCategory(category,numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(question);
        quizDao.save(quiz);
        return new ResponseEntity<>("quiz is created",HttpStatus.CREATED);
    }

    public ResponseEntity<List<Quiz>> getQuiz() {
        return new ResponseEntity<>(quizDao.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer id) {

        try{

            Optional<Quiz> optionalQuiz=quizDao.findById(id);
            List<Question> question=optionalQuiz.get().getQuestions();
            List<QuestionWrapper> questionWrapper=new ArrayList<>();
            for(Question p: question){
                QuestionWrapper qw=new QuestionWrapper(p.getId(),p.getQuestionTitle(),p.getOption1(),p.getOption2(),p.getOption3(),p.getOption4());
                questionWrapper.add(qw);
            }
            return new ResponseEntity<>(questionWrapper,HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> quiz= quizDao.findById(id);
        List<Question> questions= quiz.get().getQuestions();
        int right=0;
        int i=0;
        for(Response p: responses){
            if(p.getResponse().equals(questions.get(i).getRightAnswer())) {
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
