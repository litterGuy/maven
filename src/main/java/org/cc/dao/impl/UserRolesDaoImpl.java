package org.cc.dao.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.cc.dao.UserRolesDao;
import org.cc.entity.UserRolesEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class UserRolesDaoImpl implements UserRolesDao {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(UserRolesDaoImpl.class);
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
	public void add(UserRolesEntity userRoles) {
		sessionFactory.getCurrentSession().save(userRoles);
	}

}
