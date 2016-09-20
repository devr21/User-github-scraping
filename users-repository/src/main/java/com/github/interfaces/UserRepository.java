package com.github.interfaces;

import java.util.UUID;

import com.github.model.User;

public interface UserRepository {

	void createUser(User user);
	void updateUser(User user);
	void removeUserById(UUID id);
	void removeUserId(String userGithubId);
	User getUserById(UUID id);
	User getUserByUsername(String username);
	void closeSession();
	long getUserCount();
	
}
