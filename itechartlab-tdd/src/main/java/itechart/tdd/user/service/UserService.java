package itechart.tdd.user.service;

import itechart.tdd.user.model.User;

public interface UserService {

	User getUserById(Long id);
	
	void save(User user);
	
}
