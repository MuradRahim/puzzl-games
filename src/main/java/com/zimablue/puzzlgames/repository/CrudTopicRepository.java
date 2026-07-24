package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.jdbc.DatabaseConnectionProvider;
import com.zimablue.puzzlgames.jdbc.DatabaseException;
import com.zimablue.puzzlgames.model.Topic;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class CrudTopicRepository {
    private final DatabaseConnectionProvider connectionProvider;

    public CrudTopicRepository(DatabaseConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Topic createTopic(Topic topic) {
        String sql = """
            INSERT INTO topics (slug, title, description, questionCount) 
            VALUES (?, ?, ?, ?)
            """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 1. Подставляем параметры в запрос
            statement.setString(1, topic.getSlug());
            statement.setString(2, topic.getTitle());
            statement.setString(3, topic.getDescription());
            statement.setInt(4, topic.getQuestionCount());

            // 2. Выполняем вставку
            statement.executeUpdate();

            // 3. Извлекаем сгенерированный ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    topic.setId(generatedKeys.getLong(1)); // Назначьте сгенерированный ID объекту
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при создании темы", e);
        }

        return topic;
    }

    public List<Topic> getAllTopics() {
        return null;
    }
}
