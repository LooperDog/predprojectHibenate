package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();

        userDao.saveUser("John", "Doe", (byte) 25);
        userDao.saveUser("Jane", "Smith", (byte) 30);
        userDao.saveUser("Michael", "Brown", (byte) 35);
        userDao.saveUser("Emily", "Davis", (byte) 40);

        userDao.getAllUsers().forEach(System.out::println);

        //userDao.cleanUsersTable();
        //userDao.dropUsersTable();
    }
}

