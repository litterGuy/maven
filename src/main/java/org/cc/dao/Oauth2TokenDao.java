package org.cc.dao;

import org.cc.entity.Oauth2TokenEntity;

public interface Oauth2TokenDao {
	void save(Oauth2TokenEntity oauth2Token);
	void update(Oauth2TokenEntity oauth2Token);
	Oauth2TokenEntity getByUserID(String user_id);
}
