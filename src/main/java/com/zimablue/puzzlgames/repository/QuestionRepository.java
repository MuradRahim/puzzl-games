package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q.id FROM Question q WHERE q.topic.id = :topicId ORDER BY q.sortOrder")
    List<Long> findQuestionIdsByTopicId(@Param("topicId") Long topicId);
}
