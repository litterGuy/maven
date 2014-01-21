package org.cc.utils;

import org.apache.commons.lang3.StringUtils;

public class OauthUtil {
	
	/**
	 * 获取需要替换的字符
	 * @param key
	 * @param plateType
	 * @return
	 */
	public static String getValue(String key,String plateType){
		if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(plateType)){
			String finalKey = key.replace("default",plateType);
			return OauthConfigUtil.getInstance().getValue(finalKey);
		}else{
			return null;
		}
	}
	
	/**
	 * 直接获取字符
	 */
	public static String getSimpleValue(String key){
		return OauthConfigUtil.getInstance().getValue(key);
	}
	
}
