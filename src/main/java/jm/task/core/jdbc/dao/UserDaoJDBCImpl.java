package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/MyDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root1234";

    public UserDaoJDBCImpl(){
        try {
            connection = Util.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "lastname VARCHAR(50) NOT NULL, "
                    + "age TINYINT NOT NULL, ");
        } catch (SQLException e) {
            System.out.println("ERR create");
            return;
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP if exists users");
        } catch (SQLException e) {
            System.out.println("ERR drop");
            return;
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO users " +
                    "(name, lastname, age) VALUES (name,lastname,age)");
        } catch (SQLException e) {
            System.out.println("ERR insert");
            return;
        }
    }

    public void removeUserById(long id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE users WHERE id = " + id);
        } catch (SQLException e) {
            System.out.println("ERR delete by id");
            return;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
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
            System.out.println("ERR drop");
            return null;
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE from USERS");
        } catch (SQLException e) {
            System.out.println("ERR drop");
            return;
        }
    }
}
