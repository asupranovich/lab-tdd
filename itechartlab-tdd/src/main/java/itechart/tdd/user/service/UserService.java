package itechart.tdd.user.service;

import itechart.tdd.user.model.User;

public interface UserService {

    User getById(Long id);

    void save(User user);

}
