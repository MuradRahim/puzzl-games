package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.jdbc.DatabaseConnectionProvider;
import com.zimablue.puzzlgames.jdbc.DatabaseException;
import com.zimablue.puzzlgames.model.Topic;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TopicRepository {

    private final DatabaseConnectionProvider connectionProvider;

    public TopicRepository(DatabaseConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    /**
     * Запрос без параметров — используем обычный {@link Statement}.
     */
    public List<Topic> findAll() {
        String sql = """
                SELECT t.id, t.slug, t.title, t.description,
                       COUNT(q.id) AS question_count
                FROM topics t
                         LEFT JOIN questions q ON q.topic_id = t.id
                GROUP BY t.id, t.slug, t.title, t.description
                ORDER BY t.id
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

    /**
     * Запрос с параметром — используем {@link PreparedStatement}, чтобы безопасно подставить slug.
     */
    public Optional<Topic> findBySlug(String slug) {
        String sql = """
                SELECT t.id, t.slug, t.title, t.description,
                       COUNT(q.id) AS question_count
                FROM topics t
                         LEFT JOIN questions q ON q.topic_id = t.id
                WHERE t.slug = ?
                GROUP BY t.id, t.slug, t.title, t.description
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, slug);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при поиске темы по slug: " + slug);
        }
    }

    private Topic mapRow(ResultSet resultSet) throws SQLException {
        return Topic.builder()
                .id(resultSet.getLong("id"))
                .slug(resultSet.getString("slug"))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .questionCount(resultSet.getInt("question_count"))
                .build();
    }
}
