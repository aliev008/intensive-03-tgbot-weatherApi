package com.aliev.tgbot.service.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class DatabaseConnection {
    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.password}")
    private String PASSWORD;
    @Value("${database-name}")
    private String DB_NAME;

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

//    public void insertData(String someData) {
//        String sql = "INSERT INTO " + DB_NAME + " (column_name) VALUES (?)";
//
//        try (Connection connection = connect();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setString(1, someData);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Пример выборки данных
//    public void getData() {
//        String sql = "SELECT * FROM " + DB_NAME;
//
//        try (Connection connection = connect();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String data = resultSet.getString("column_name");
//                System.out.println("ID: " + id + ", Data: " + data);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
