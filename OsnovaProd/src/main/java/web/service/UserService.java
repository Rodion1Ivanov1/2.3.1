package web.service;

import web.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user);

    List<User> listUsers();

    void updateUser(int id, User user);

    void deleteUser(int id);

    User showUser(int id);

}
