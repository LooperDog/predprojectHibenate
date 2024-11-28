package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mydb_predproject";
    private static final String USER = "LooperDog";
    private static final String PASSWORD = "WDAM!8Dm8prCriT";

    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Вы подключены");
            }
        } catch (SQLException sqlE) {
            System.out.println("Вы не подключены...Ошибка" + sqlE);
        }
        return connection;
    }

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties sitting = new Properties();
                sitting.put("hibernate.connection.url", URL);
                sitting.put("hibernate.connection.username", USER);
                sitting.put("hibernate.connection.password", PASSWORD);
                sitting.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                sitting.put("hibernate.show_sql", "true");
                sitting.put("hibernate.hbm2ddl.auto", "update");
                configuration.setProperties(sitting);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed." + ex);
            }
        }
        return sessionFactory;
    }
}

