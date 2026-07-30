package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestion_IdOrderById(Long questionId);
}
