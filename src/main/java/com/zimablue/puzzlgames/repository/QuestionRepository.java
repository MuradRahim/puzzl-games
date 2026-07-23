package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.jdbc.DatabaseConnectionProvider;
import com.zimablue.puzzlgames.jdbc.DatabaseException;
import com.zimablue.puzzlgames.model.Question;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionRepository {

    private final DatabaseConnectionProvider connectionProvider;

    public QuestionRepository(DatabaseConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public List<Long> findQuestionIdsByTopicId(Long topicId) {
        String sql = """
                SELECT id
                FROM questions
                WHERE topic_id = ?
                ORDER BY sort_order
                """;

        List<Long> questionIds = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, topicId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    questionIds.add(resultSet.getLong("id"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при загрузке вопросов темы: " + topicId, e);
        }

        return questionIds;
    }

    public Optional<Question> findById(Long id) {
        String sql = """
                SELECT id, topic_id, question_text, sort_order
                FROM questions
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
            throw new DatabaseException("Ошибка при поиске вопроса: " + id, e);
        }
    }

    private Question mapRow(ResultSet resultSet) throws SQLException {
        return Question.builder()
                .id(resultSet.getLong("id"))
                .topicId(resultSet.getLong("topic_id"))
                .questionText(resultSet.getString("question_text"))
                .sortOrder(resultSet.getInt("sort_order"))
                .build();
    }
}
