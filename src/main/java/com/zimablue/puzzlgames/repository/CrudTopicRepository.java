package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.jdbc.DatabaseConnectionProvider;
import com.zimablue.puzzlgames.jdbc.DatabaseException;
import com.zimablue.puzzlgames.model.Topic;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
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
            throw new DatabaseException("Ошибка при создании темы");
        }

        return topic;
    }

    public Topic updateTopic(Topic topic) {
        String sql = """
        UPDATE topics
        SET slug = ?, 
            title = ?, 
            description = ?
        WHERE id = ?
        """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Подставляем параметры
            statement.setString(1, topic.getSlug());
            statement.setString(2, topic.getTitle());
            statement.setString(3, topic.getDescription());
            statement.setLong(4, topic.getId());

            // Выполняем обновление
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new DatabaseException(
                    "Тема с id = " + topic.getId() + " не найдена");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при обновлении темы");
        }

        return topic;
    }

    public List<Topic> getAllTopics() {
        String sql = """
                SELECT * FROM topics 
                """;

        List<Topic> topics = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                topics.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при загрузке списка тем");
        }

        return topics;
    }

    private Topic mapRow(ResultSet resultSet) throws SQLException {
        return Topic.builder()
            .id(resultSet.getLong("id"))
            .slug(resultSet.getString("slug"))
            .title(resultSet.getString("title"))
            .description(resultSet.getString("description"))
            .build();
    }
}
