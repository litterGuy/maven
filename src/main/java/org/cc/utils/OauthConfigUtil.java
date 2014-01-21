package org.cc.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 读取配置文件
 * @author litterGuy
 */
public class OauthConfigUtil {
	
	private static  OauthConfigUtil config =null;
	
	private OauthConfigUtil(){}
	
	public static OauthConfigUtil getInstance() {
		if (null == config){
			config = new OauthConfigUtil();
		}
		return config;
	}
	
	private static Properties props;
	private static Resource resource;
	static{
		try {
			resource = new ClassPathResource(Oauth2Constants.OAUTH_CONFIG_FILE);
			props = PropertiesLoaderUtils.loadProperties(resource);
			System.err.print("how many time");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  String getValue(String key){
		if(props == null){
			try {
				props = PropertiesLoaderUtils.loadProperties(resource);
				System.err.print("how many time");
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		String value = props.getProperty(key);
		return value;
	}
	
}
