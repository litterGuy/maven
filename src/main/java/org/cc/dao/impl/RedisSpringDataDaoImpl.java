package org.cc.dao.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.cc.dao.RedisSpringDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisSpringDataDaoImpl implements RedisSpringDataDao{
	private final static Logger logger = Logger.getLogger(RedisSpringDataDaoImpl.class);
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    private String appName;
    public RedisSpringDataDaoImpl(String appName){
            this.appName = appName;
    }
	public RedisSpringDataDaoImpl(){}
    
    /** -------------------->> pojo操作 <<-------------------- */
    public boolean set(String key, Object value) {
            if(key == null || value == null)
                    return false;
            redisTemplate.opsForValue().set(getUniqueKey(key), value);
            return true;
    }
    
    public Object get(String key) {
            return redisTemplate.opsForValue().get(getUniqueKey(key));
    }
    
    /** --------------->> list操作 <<--------------- */
    public boolean setList(String key, List<Object> list) {
            String uniqueKey = getUniqueKey(key);
            for (Object value: list)
            	redisTemplate.opsForList().rightPush(uniqueKey, value);
            return true;
    }
    public List<Object> getList(String key) {
            return getListByRange(key, 0, -1);
    }
    /**
     * 获取list长度
     * @param key
     * @return
     */
    public Long getListSize(String key) {  
    return redisTemplate.opsForList().size(getUniqueKey(key));  
}  
    /**
     * 按范围索引
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> getListByRange(String key, int start, int end){
            String uniqueKey = getUniqueKey(key);
            List<Object> list = redisTemplate.opsForList().range(uniqueKey, start, end);
            return list;
    }
    /**
     * 按索引赋值
     * @param key
     * @param index
     * @param value
     * @return
     */
    public boolean setByIndex(String key, long index, Object value){
    	redisTemplate.opsForList().set(getUniqueKey(key), index, value);        
            return true;
    }
    /**
     * 按索引取值
     * @param key
     * @param indexredisTemplate.opsForSet()
     * @return
     */
    public Object getByIndex(String key, long index){
            return redisTemplate.opsForList().index(getUniqueKey(key), index);
    }
    
    /**
     * 按索引删除
     */
    public boolean deleteByIndex(String key, long index, Object value){
    	redisTemplate.opsForList().remove(getUniqueKey(key), index, value);
            return true;
    }
    
    /** --------------------->> set操作 <<--------------------- */
    /**
     * 存set
     */
    public boolean addSet(String key, Set<Object> set){
            String uniqueKey = getUniqueKey(key);
            for(Object value: set)
            	redisTemplate.opsForSet().add(uniqueKey, value);
            return true;
    }
    /**
     * 取set
     */
    public Set<Object> getSet(String key){
            Set<Object> set = redisTemplate.opsForSet().members(getUniqueKey(key));
            return set;
    }
    /**
     * 获取set长度
     */
    public Long getSetSize(String key){
            return redisTemplate.opsForSet().size(getUniqueKey(key));
    }
    /**
     * 删除指定key中的set集合的某个元素
     */
    public boolean deleteElement(String key, Object value){
            return redisTemplate.opsForSet().remove(getUniqueKey(key), value) != null;
    }
    /**
     * 判断指定key中的set集合是否包含某元素
     */
    public boolean isMemBer(String key, Object value){
            return redisTemplate.opsForSet().isMember(getUniqueKey(key), value);
    }
    
    /** --------------------->> map操作 <<--------------------- */
    public boolean putAll(String key, Map<Object, Object> map){
    	redisTemplate.opsForHash().putAll(getUniqueKey(key), map);
            return true;
    }
    public Map<Object, Object> getMap(String key){
            return redisTemplate.opsForHash().entries(getUniqueKey(key));
    }
    
    /** --------------------->> zset操作 <<--------------------- */
    
    /** --------------------->> 通用操作 <<--------------------- */
    /**
     * 删除
     */
    public boolean delete(String key) {
            redisTemplate.opsForValue().getOperations().delete(getUniqueKey(key));
            return true;
    }
    /**
     * 清空缓存
     */
    public boolean clear() {
        return redisTemplate.execute(new RedisCallback<Object>() {
	        public String doInRedis(RedisConnection connection) throws DataAccessException {
	            connection.flushDb();
	            return "okay";
	        }
	    }).equals("okay");
    }
    /**
     * 设置失效时间
     * 时间单位为秒
     */
    public boolean expire(String key,Long timeout){
    	if(timeout == null){
    		Resource resource = new ClassPathResource("redis-config.properties");
    		Properties props;
			try {
				props = PropertiesLoaderUtils.loadProperties(resource);
				timeout = Long.parseLong(props.getProperty("redis.expire.default"));
			} catch (IOException e) {
				timeout = (long) 7200;
				logger.error("the redis-config.properties don`t contain redis.expire.default or the type is wrong");
			}
    	}
    	return redisTemplate.expire(getUniqueKey(key), timeout, TimeUnit.SECONDS);
    }
    /**
     * 设置到期时间
     */
    public boolean expireAt(String key,Date expireDate){
    	return redisTemplate.expireAt(getUniqueKey(key), expireDate);
    }
    /**
     * 确定key值是否存在
     */
    public boolean hasKey(String key){
    	return redisTemplate.hasKey(getUniqueKey(key));
    }
    /**
	 * make unique key by prepend cache name
	 * @param key: logic key
	 * @return the unique key with cache name
	 */        
	private String getUniqueKey(Object key) {
	    return new StringBuilder().append(this.appName).append("#").append(String.valueOf(key)).toString();
	}

}
