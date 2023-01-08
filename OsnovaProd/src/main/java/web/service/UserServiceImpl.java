package web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;


import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    @Transactional
    public void addUser(User user) {
         userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, User user) {
         userDao.updateUser(id, user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User showUser(int id) {
        return userDao.showUser(id);
    }


}
