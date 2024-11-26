package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    private static final Configuration config = new Configuration().addAnnotatedClass(User.class);
    public static SessionFactory getSessionFactory() {
        return config.buildSessionFactory();
    }
}

