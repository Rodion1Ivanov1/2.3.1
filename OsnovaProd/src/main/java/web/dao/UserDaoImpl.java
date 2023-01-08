package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return entityManager.createQuery("From User", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
        entityManager.flush();

    }

    @Override
    public void updateUser(int id, User user) {
        User user2 = entityManager.find(User.class, id);
        user2.setFirstName(user.getFirstName());
        user2.setLastName(user.getLastName());
        user2.setEmail(user.getEmail());
        entityManager.flush();

    }

    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        entityManager.flush();
    }

    @Override
    public User showUser(int id) {
        return entityManager.find(User.class, id);
    }

}
