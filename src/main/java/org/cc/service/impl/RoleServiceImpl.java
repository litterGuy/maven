package org.cc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cc.dao.RoleDao;
import org.cc.entity.RoleEntity;
import org.cc.service.RoleService;
import org.springframework.stereotype.Service;
@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleDao dao;
	
	@Override
	public List<RoleEntity> getRoleByUserID(Integer userID) {
		return dao.getRoleByUserID(userID);
	}

	@Override
	public RoleEntity getUserRole() {
		return dao.getUserRole("ROLE_USER");
	}

}
