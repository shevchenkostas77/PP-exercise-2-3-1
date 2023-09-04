package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    User readUser(long id);

    List<User> readAllUsers();

    void updateUser(User user);

    void deleteUser(long id);
}
