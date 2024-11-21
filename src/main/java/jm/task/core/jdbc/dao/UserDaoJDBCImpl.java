package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50), " +
            "lastName VARCHAR(50)," +
            "age TINYINT)";
    private static final String GET_ALL_SQL = "SELECT * FROM users";
    private static final String CLEAN_SQL = "TRUNCATE TABLE users";
    private static final String DELETE_SQL = "DELETE FROM users WHERE id = ?";
    private static final String DROP_SQL = "DROP TABLE IF EXISTS users";
    private static final String SAVE_SQL = "INSERT INTO Users (name, lastName, age) VALUES ( ?, ?, ?)";


    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_SQL);
            System.out.println("Таблица Users создана...");

        } catch (SQLException e) {
            System.err.println("Что-то не так, таблица не создана..." + e);
        }

    }
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_SQL);
            System.out.println("Таблица Users удалена...");
        } catch (SQLException e) {
            System.err.println("Что-то не так, таблица не удалена..." + e);
        }

    }

    public void saveUser (String name, String lastName, byte age)  {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            System.err.println("Пользователь с именем - " + name + "не был добавлен... " + e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            System.out.println("User с ID — " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Пользователь не удален..." + e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_SQL);
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Что-то пошло не так " + e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CLEAN_SQL);
            System.out.println("Таблица Users очищена...");
        } catch (SQLException e) {
            System.err.println("Таблица не очищена..."+ e);
        }
    }
}