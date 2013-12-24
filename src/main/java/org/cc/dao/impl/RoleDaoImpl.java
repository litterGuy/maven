package org.cc.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.cc.dao.RoleDao;
import org.cc.entity.RoleEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class RoleDaoImpl implements RoleDao {
	private static final Logger logger = Logger.getLogger(RoleDaoImpl.class);
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
	public List<RoleEntity> getRoleByUserID(Integer userID) {
		String sql = "select role from RoleEntity role,UserRolesEntity userRole where role.id=userRole.roleID and userRole.userID = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setInteger(0, userID);
		try {
			return query.list();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
	public RoleEntity getUserRole(String roleName) {
		String sql = " from RoleEntity where name like ?";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setString(0,"%"+roleName+"%");
		return (RoleEntity) query.uniqueResult();
	}

}
