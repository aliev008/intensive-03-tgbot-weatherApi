package com.aliev.tgbot.service;

import com.aliev.tgbot.dto.User;
import com.aliev.tgbot.service.database.DatabaseConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {

    private final DatabaseConnection databaseConnection;

    public DatabaseService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    // Метод для сохранения или обновления пользователя
    public void saveOrUpdateUser(Long userId, String firstName, String lastName, String username) {
        String query = "INSERT INTO telegram_user (id, firstName, lastName, username) " + "VALUES (?, ?, ?, ?) " + "ON CONFLICT (id) DO UPDATE " + "SET firstName = EXCLUDED.firstName, " + "lastName = EXCLUDED.lastName, " + "username = EXCLUDED.username";
        try (Connection connection = databaseConnection.connect(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения пользователя по его ID
    public User getUserById(Long userId) {
        String query = "SELECT * FROM telegram_user WHERE id = ?";
        try (Connection connection = databaseConnection.connect(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getLong("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUserExists(Long userId) {
        String query = "SELECT COUNT(*) FROM telegram_user WHERE id = ?";
        try (Connection connection = databaseConnection.connect(); PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Добавить город для пользователя
    public void addCityForUser(long userId, String cityName) {
        String query = "INSERT INTO cities (user_id, city_name, last_requested) " + "SELECT id, ?, NOW() FROM telegram_user WHERE id = ?";
        try (Connection connection = databaseConnection.connect();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cityName);
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Получить список городов, запрошенных пользователем
    public List<String> getUserCities(long telegramId) {
        List<String> cities = new ArrayList<>();
        String query = "SELECT DISTINCT city_name FROM cities c " +
                "JOIN telegram_user u ON c.user_id = u.id WHERE u.id = ?";
        try (Connection connection = databaseConnection.connect();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, telegramId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cities.add(rs.getString("city_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
