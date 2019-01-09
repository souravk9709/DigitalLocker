package com.dfrozensoft.locker.blockchain.api;

import com.dfrozensoft.locker.model.User;

public interface UserApi {
	boolean userExists(String username);

	void create(User user);

	User get(String username);
}
