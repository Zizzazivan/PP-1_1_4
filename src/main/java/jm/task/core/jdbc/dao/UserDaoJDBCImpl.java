package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public Connection getConnection() {
        return connection;
    }

    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/MyDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root1234";
    private PreparedStatement insertStatement;
    private PreparedStatement deleteStatement;

    public UserDaoJDBCImpl(){
        try {
            connection = Util.getConnection(URL,USER,PASSWORD);
            insertStatement = connection.prepareStatement("INSERT INTO users " +
                    "(name, lastname, age) VALUES (?,?,?)");
            deleteStatement = connection.prepareStatement("DELETE from users WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "lastname VARCHAR(50) NOT NULL, "
                    + "age TINYINT NOT NULL) ");
        } catch (SQLException e) {
            System.out.println("ERR create");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DROP TABLE if exists users");
        } catch (SQLException e) {
            System.out.println("ERR drop");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            insertStatement.setString(1, name);
            insertStatement.setString(2, lastName);
            insertStatement.setByte(3, age);
            insertStatement.executeUpdate();
            System.out.println("User с именем " +
                    name + " name добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("ERR insert");
        }

    }

    public void removeUserById(long id) {
        try {
            deleteStatement.setLong(1,id);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERR delete by id");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("ERR getall");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE from USERS");
        } catch (SQLException e) {
            System.out.println("ERR delete table");
        }
    }
}
