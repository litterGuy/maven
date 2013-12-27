package org.cc.dao.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.cc.dao.Oauth2TokenDao;
import org.cc.entity.Oauth2TokenEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class Oauth2TokenDaoImpl implements Oauth2TokenDao {
	private static final Logger logger = Logger.getLogger(Oauth2TokenDaoImpl.class);
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
	public void save(Oauth2TokenEntity oauth2Token) {
		sessionFactory.getCurrentSession().save(oauth2Token);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
	public void update(Oauth2TokenEntity oauth2Token) {
		sessionFactory.getCurrentSession().update(oauth2Token);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
	public Oauth2TokenEntity getByUserID(String user_id) {
		String sql = "from Oauth2TokenEntity token where token.user_id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setString(0, user_id);
		try {
			return (Oauth2TokenEntity) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
