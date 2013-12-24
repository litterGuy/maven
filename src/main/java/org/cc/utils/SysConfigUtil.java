package org.cc.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class SysConfigUtil {
	private static final Logger logger = Logger.getLogger(SysConfigUtil.class);
	public static final String MAIL_SENDER = "mail.sendermail";
	public static final String MAIL_EXPIRES = "mail.expiresTime";
	
	public static String getMailParam(String key){
		Resource resource = new ClassPathResource("mail.properties");
		String result=null;
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			result = props.getProperty(key);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}
}
