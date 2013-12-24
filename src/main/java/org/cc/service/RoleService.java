package org.cc.service;

import java.util.List;

import org.cc.entity.RoleEntity;

public interface RoleService {
	List<RoleEntity> getRoleByUserID(Integer userID);
	RoleEntity getUserRole();
}
