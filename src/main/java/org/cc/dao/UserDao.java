package org.cc.dao;

import org.cc.entity.UserEntity;

public interface UserDao {
	UserEntity getByLoginName(String loginName);
	UserEntity getByEmail(String email);
	void save(UserEntity user);
	void update(UserEntity user);
	UserEntity getByOauthID(Long id);
}
