package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {


    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Smych", (byte)23);

        userService.saveUser("Van", "Darkholme", (byte)51);
        userService.saveUser("Steve", "Rambo", (byte)67);
        userService.saveUser("Billy", "Harrington", (byte)48);
        userService.removeUserById(3);
        userService.getAllUsers().stream().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
