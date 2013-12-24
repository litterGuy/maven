package org.cc.dao.impl;

import org.apache.log4j.Logger;
import org.cc.dao.MongoDBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class MongoDBDaoImpl implements MongoDBDao {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MongoDBDaoImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void save(Object obj) {
		mongoTemplate.save(obj);
	}

}
