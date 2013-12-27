package org.cc.service;

import org.cc.entity.UserEntity;

public interface UserService {
	boolean login(UserEntity user);
	boolean validLoginName(String loginName);
	boolean validEmail(String email);
	void save(UserEntity user);
	UserEntity getByLoginName(String loginName);
	void update(UserEntity user);
	UserEntity getByOauthID(Long id);
}
