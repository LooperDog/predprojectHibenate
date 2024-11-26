package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoHibernateImpl dao = new UserDaoHibernateImpl();

        dao.createUsersTable();

        dao.saveUser("John", "Doe", (byte) 25);
        dao.saveUser("Jane", "Smith", (byte) 30);
        dao.saveUser("Michael", "Brown", (byte) 35);
        dao.saveUser("Emily", "Davis", (byte) 40);

        dao.getAllUsers().forEach(System.out::println);

        dao.cleanUsersTable();
        dao.dropUsersTable();
    }
}

