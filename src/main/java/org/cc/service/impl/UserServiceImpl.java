package org.cc.service.impl;

import javax.annotation.Resource;

import org.cc.dao.UserDao;
import org.cc.entity.UserEntity;
import org.cc.service.UserService;
import org.cc.utils.MD5;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	@Override
	public boolean login(UserEntity user) {
		UserEntity result = userDao.getByLoginName(user.getLoginName());
		if(result!=null &&  result.getPassWord().equals(MD5.toMD5(user.getPassWord()))){
			return true;
		}
		return false;
	}
	@Override
	public boolean validLoginName(String loginName) {
		UserEntity result = userDao.getByLoginName(loginName);
		if(result!=null){
			return false;
		}
		return true;
	}
	@Override
	public boolean validEmail(String email) {
		UserEntity user = userDao.getByEmail(email);
		if(user!=null){
			return false;
		}
		return true;
	}
	@Override
	public void save(UserEntity user) {
		userDao.save(user);
	}
	@Override
	public UserEntity getByLoginName(String loginName) {
		return userDao.getByLoginName(loginName);
	}
	@Override
	public void update(UserEntity user) {
		userDao.update(user);
	}

}
