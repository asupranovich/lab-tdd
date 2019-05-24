package itechart.tdd.user.service.impl;

import itechart.tdd.user.dao.UserDao;
import itechart.tdd.user.model.User;
import itechart.tdd.user.model.UserNotFoundException;
import itechart.tdd.user.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public User getById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("User id should not be null");
        }

        try {
            return userDao.getById(id);
        } catch (UserNotFoundException e) {
            return null;
        }
    }

    @Override
    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }

        if (user.getId() == null) {
            userDao.create(user);
        } else {
            userDao.update(user);
        }
    }

}
