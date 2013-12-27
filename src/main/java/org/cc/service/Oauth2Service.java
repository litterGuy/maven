package org.cc.service;

import org.cc.entity.Oauth2TokenEntity;
import org.cc.entity.UserEntity;

public interface Oauth2Service {
	UserEntity save(Oauth2TokenEntity token);
	UserEntity handlerSort(String code,String type) throws Exception;
}
