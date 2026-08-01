package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findBySlug(String slug);

    @Query(value = """
        SELECT t.id, t.slug, t.title, t.description,
                               COUNT(q.id) AS question_count
                        FROM topics t
                                 LEFT JOIN questions q ON q.topic_id = t.id
                        GROUP BY t.id, t.slug, t.title, t.description
                        ORDER BY t.id
        """, nativeQuery = true)
    Optional<Topic> findByIdWithQuestions(@Param("id") Long id);
}
