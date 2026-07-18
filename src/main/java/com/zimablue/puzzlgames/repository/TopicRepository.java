package com.zimablue.puzzlgames.repository;

import com.zimablue.puzzlgames.model.Topic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TopicRepository {

    private static final RowMapper<Topic> ROW_MAPPER = (rs, rowNum) -> Topic.builder()
            .id(rs.getLong("id"))
            .slug(rs.getString("slug"))
            .title(rs.getString("title"))
            .description(rs.getString("description"))
            .questionCount(rs.getInt("question_count"))
            .build();

    private final JdbcTemplate jdbcTemplate;

    public TopicRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Topic> findAll() {
        String sql = """
                SELECT t.id, t.slug, t.title, t.description,
                       COUNT(q.id) AS question_count
                FROM topics t
                         LEFT JOIN questions q ON q.topic_id = t.id
                GROUP BY t.id, t.slug, t.title, t.description
                ORDER BY t.id
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Optional<Topic> findBySlug(String slug) {
        String sql = """
                SELECT t.id, t.slug, t.title, t.description,
                       COUNT(q.id) AS question_count
                FROM topics t
                         LEFT JOIN questions q ON q.topic_id = t.id
                WHERE t.slug = ?
                GROUP BY t.id, t.slug, t.title, t.description
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER, slug).stream().findFirst();
    }
}
