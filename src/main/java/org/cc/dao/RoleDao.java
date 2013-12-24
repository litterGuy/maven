package org.cc.dao;

import java.util.List;

import org.cc.entity.RoleEntity;

public interface RoleDao {
	List<RoleEntity> getRoleByUserID(Integer userID);
	RoleEntity getUserRole(String roleName);
}
