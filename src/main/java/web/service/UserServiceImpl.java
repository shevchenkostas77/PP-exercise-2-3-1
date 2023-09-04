package web.service;

import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDaoImpl;

    public UserServiceImpl(UserDao userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    public void createUser(User user) {
        userDaoImpl.createUser(user);
    }

    @Override
    public User readUser(long id) {
        return userDaoImpl.readUser(id);
    }

    @Override
    public List<User> readAllUsers() {
        return userDaoImpl.readAllUsers();
    }

    @Override
    public void updateUser(User user) {
        userDaoImpl.updateUser(user);
    }

    @Override
    public void deleteUser(long id) {
        userDaoImpl.deleteUser(id);
    }
}
