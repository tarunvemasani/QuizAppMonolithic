package com.hero.quizapp.service;

import com.hero.quizapp.model.Question;
import com.hero.quizapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            Optional<Question> addOptionalQuestion = questionDao.findById(question.getId());
            if(addOptionalQuestion.isPresent()){
                return new ResponseEntity<>(question.getId()+" is already present",HttpStatus.CONFLICT);
            }

            questionDao.save(question);
            return new ResponseEntity<>(question.getId()+" is added",HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error adding question ", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try{
            Optional<Question> optionalQuestion = questionDao.findById(id);
            if(optionalQuestion.isPresent()){
                questionDao.deleteById(id);
                return new ResponseEntity<>(id+ " is deleted",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(id +" is not found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting Question",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateQuestion(Integer id, Question question) {
        try{
            Optional<Question> updateQuestion = questionDao.findById(id);
            if(updateQuestion.isPresent())
            {
                Question existing = updateQuestion.get();
                existing.setId(question.getId());
                existing.setCategory(question.getCategory());
                existing.setDifficultylevel(question.getDifficultylevel());
                existing.setOption1(question.getOption1());
                existing.setOption2(question.getOption2());
                existing.setOption3(question.getOption3());
                existing.setOption4(question.getOption4());
                existing.setQuestionTitle(question.getQuestionTitle());
                existing.setRightAnswer(question.getRightAnswer());

                questionDao.save(existing);
                return new ResponseEntity<>("Question is updated", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Question id is not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Question is not updated", HttpStatus.BAD_REQUEST);
        }

    }
}
