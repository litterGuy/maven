package org.cc.dao.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.cc.dao.UserDao;
import org.cc.entity.UserEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class UserDaoImpl implements UserDao {
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
	public UserEntity getByLoginName(String loginName) {
		String sql = "from UserEntity u where u.loginName = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setString(0, loginName);
		try {
			return (UserEntity) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
	public UserEntity getByEmail(String email) {
		String sql = "from UserEntity u where u.email = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setString(0, email);
		try {
			return (UserEntity) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
	public void save(UserEntity user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
	public void update(UserEntity user) {
		sessionFactory.getCurrentSession().update(user);
	}

}
