package com.example.Habr_Parser_Project.repository;

import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean recordExists(long userId, long articleId) {
        String query = "SELECT COUNT(*) FROM user_bookmarks WHERE user_id = ? AND article_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, userId, articleId) > 0;
    }

    public void deleteRecord(long userId, long articleId) {
        String query = "DELETE FROM user_bookmarks WHERE user_id = ? AND article_id = ?";
        jdbcTemplate.update(query, userId, articleId);
    }

    public void insertRecord(long userId, long articleId) {
        String query = "INSERT INTO user_bookmarks (user_id, article_id) VALUES (?, ?)";
        jdbcTemplate.update(query, userId, articleId);
    }

    public void executeSomeQuery(long userId, long articleId) {
        if (recordExists(userId, articleId)) {
            deleteRecord(userId, articleId);
            System.out.println("Record deleted.");
        } else {
            insertRecord(userId, articleId);
            System.out.println("Record inserted.");
        }
    }
    public List<Long> getArticleIdsByUserId(long userId) {
        String sql = "SELECT article_id FROM user_bookmarks WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, userId);
    }
}
