package com.zimablue.puzzlgames.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Фабрика JDBC-соединений через DriverManager.
 * <p>
 * В старых Java-приложениях именно так открывали {@link Connection}:
 * загружали драйвер и вызывали {@link DriverManager#getConnection(String, String, String)}.
 */
@Component
public class DatabaseConnectionProvider {

    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;

    public DatabaseConnectionProvider(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driver-class-name}") String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    public Connection getConnection() {
        try {
            // Раньше драйвер регистрировали явно через Class.forName(...)
            Class.forName(driverClassName);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("JDBC-драйвер не найден: " + driverClassName);
        } catch (SQLException e) {
            throw new DatabaseException("Не удалось подключиться к базе данных");
        }
    }
}
