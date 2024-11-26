package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                                "(id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                                "name VARCHAR(50), " +
                                "lastName VARCHAR(50)," +
                                "age TINYINT)").executeUpdate();
           session.getTransaction().commit();
        }catch (Exception e) {
            System.err.println("Что-то не так, таблица не создана..." +e);
        }


    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Что-то не так, таблица не удалена..." + e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new User());
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных.");
        } catch (Exception e) {
            System.err.println("Пользователь с именем - " + name + "не был добавлен... " + e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if(user != null) {
                session.delete(user);
            }
            tx.commit();
            System.out.println("User с ID — " + id + " удален.");
        }catch (Exception e) {
            System.err.println("Пользователь не удален..." + e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            users = session.createQuery(criteria).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Что-то пошло не так " + e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            System.err.println("Таблица не очистилась... Ошибка" +e);
        }

    }
}
