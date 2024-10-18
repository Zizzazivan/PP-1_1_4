package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static SessionFactory sessionFactory;
    private Util() {
    }

    static {
        try {
            Configuration configuration = new Configuration();

            // Указываем настройки подключения к базе данных
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/testDB?useSSL=false");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "root1234");

            // Указываем диалект для MySQL
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

            // Логирование SQL запросов
            configuration.setProperty("hibernate.show_sql", "true");

            // Управление схемой базы данных (например, update: обновляет схему)
            configuration.setProperty("hibernate.hbm2ddl.auto", "none");

            // Указываем классы сущностей
            configuration.addAnnotatedClass(User.class); // добавляем наш класс User как сущность

            // Создаем сервисный регистр и фабрику сессий
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Connection getConnection(String url, String user, String password) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
