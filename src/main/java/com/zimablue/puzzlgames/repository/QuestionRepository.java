package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.model.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestionRepository {

    private static final RowMapper<Question> ROW_MAPPER = (rs, rowNum) -> Question.builder()
            .id(rs.getLong("id"))
            .topicId(rs.getLong("topic_id"))
            .questionText(rs.getString("question_text"))
            .sortOrder(rs.getInt("sort_order"))
            .build();

    private final JdbcTemplate jdbcTemplate;

    public QuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findQuestionIdsByTopicId(Long topicId) {
        String sql = """
                SELECT id
                FROM questions
                WHERE topic_id = ?
                ORDER BY sort_order
                """;
        return jdbcTemplate.queryForList(sql, Long.class, topicId);
    }

    public Optional<Question> findById(Long id) {
        String sql = """
                SELECT id, topic_id, question_text, sort_order
                FROM questions
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER, id).stream().findFirst();
    }
}
