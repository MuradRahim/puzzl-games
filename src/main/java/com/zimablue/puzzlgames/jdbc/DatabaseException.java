package com.zimablue.puzzlgames.jdbc;

/**
 * Классический способ получения соединения с БД через DriverManager.
 * Так работали приложения до появления пулов соединений и JdbcTemplate.
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException(String message) {
        super(message);
    }
}
