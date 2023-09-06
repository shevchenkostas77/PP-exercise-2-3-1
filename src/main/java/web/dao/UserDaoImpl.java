package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void createUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public User readUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> readAllUsers() {
        return entityManager
                .createQuery("SELECT u from User u", User.class)
                .getResultList();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        User user = readUser(id);
        entityManager.remove(user);
        entityManager.flush();
    }
}
