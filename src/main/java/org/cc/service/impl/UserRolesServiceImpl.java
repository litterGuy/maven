package org.cc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.cc.dao.UserRolesDao;
import org.cc.entity.UserRolesEntity;
import org.cc.service.UserRolesService;
import org.springframework.stereotype.Service;

@Service
public class UserRolesServiceImpl  implements UserRolesService{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(UserRolesServiceImpl.class);
	@Resource
	private UserRolesDao dao;
	
	
	@Override
	public void addRoles(List<UserRolesEntity> list) {
		if(list==null || list.size()<=0){
			throw new RuntimeException("the role list is null");
		}
		for(UserRolesEntity userRoles : list){
			dao.add(userRoles);
		}
	}
	
}
