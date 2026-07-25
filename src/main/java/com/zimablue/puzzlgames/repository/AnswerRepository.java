package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.jdbc.DatabaseConnectionProvider;
import com.zimablue.puzzlgames.jdbc.DatabaseException;
import com.zimablue.puzzlgames.model.Answer;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnswerRepository {

    private final DatabaseConnectionProvider connectionProvider;

    public AnswerRepository(DatabaseConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public List<Answer> findByQuestionId(Long questionId) {
        String sql = """
                SELECT id, question_id, answer_text, is_correct
                FROM answers
                WHERE question_id = ?
                ORDER BY id
                """;

        List<Answer> answers = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, questionId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    answers.add(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при загрузке ответов вопроса: " + questionId);
        }

        return answers;
    }

    public Optional<Answer> findById(Long id) {
        String sql = """
                SELECT id, question_id, answer_text, is_correct
                FROM answers
                WHERE id = ?
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при поиске ответа: " + id);
        }
    }

    private Answer mapRow(ResultSet resultSet) throws SQLException {
        return Answer.builder()
                .id(resultSet.getLong("id"))
                .questionId(resultSet.getLong("question_id"))
                .answerText(resultSet.getString("answer_text"))
                .correct(resultSet.getBoolean("is_correct"))
                .build();
    }
}
