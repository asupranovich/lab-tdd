package itechart.tdd.user.dao;

import itechart.tdd.user.model.User;
import itechart.tdd.user.model.UserNotFoundException;

public interface UserDao {

	User getById(Long id) throws UserNotFoundException;
	
	void create(User user);
	
	void update(User user);
	
}
