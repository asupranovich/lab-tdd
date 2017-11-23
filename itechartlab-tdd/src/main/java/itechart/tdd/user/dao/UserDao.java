package itechart.tdd.user.dao;

import itechart.tdd.user.model.User;

public interface UserDao {

	User getById(Long id);
	
	void create(User user);
	
	void update(User user);
	
}
