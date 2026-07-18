package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.model.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AnswerRepository {

    private static final RowMapper<Answer> ROW_MAPPER = (rs, rowNum) -> Answer.builder()
            .id(rs.getLong("id"))
            .questionId(rs.getLong("question_id"))
            .answerText(rs.getString("answer_text"))
            .correct(rs.getBoolean("is_correct"))
            .build();

    private final JdbcTemplate jdbcTemplate;

    public AnswerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Answer> findByQuestionId(Long questionId) {
        String sql = """
                SELECT id, question_id, answer_text, is_correct
                FROM answers
                WHERE question_id = ?
                ORDER BY id
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER, questionId);
    }

    public Optional<Answer> findById(Long id) {
        String sql = """
                SELECT id, question_id, answer_text, is_correct
                FROM answers
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER, id).stream().findFirst();
    }
}
