package org.cc.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.cc.dao.Oauth2TokenDao;
import org.cc.entity.Oauth2TokenEntity;
import org.cc.entity.RoleEntity;
import org.cc.entity.UserEntity;
import org.cc.entity.UserRolesEntity;
import org.cc.service.Oauth2Service;
import org.cc.service.RoleService;
import org.cc.service.UserRolesService;
import org.cc.service.UserService;
import org.cc.utils.Oauth2Constants;
import org.cc.utils.Oauth2HttpClientUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
@Service
public class Oauth2ServiceImpl implements Oauth2Service {
	
	private static final Logger logger = Logger.getLogger(Oauth2ServiceImpl.class);
	private static final String INIT_PASSWORD_SYMBOLS="123456";
	
	@Resource
	private Oauth2TokenDao tokenDao;
	@Resource
	private UserService userService;
	@Resource
	private UserRolesService userRolesService;
	@Resource
	private RoleService roleService;
	
	@Override
	public UserEntity save(Oauth2TokenEntity token) {
		Oauth2TokenEntity temp = tokenDao.getByUserID(token.getUser_id());
		UserEntity user = null;
		if(temp!=null){
			token.setId(temp.getId());
			//更新access中存放的信息
			tokenDao.update(token);
			user = userService.getByOauthID(token.getId());
		}else{
			token = this.downloadPic(token);
			tokenDao.save(token);
			//给用户建立一个默认用户名称
			user = this.getUser(token);
			userService.save(user);
			//给用户赋予权限
			this.initRole(user);
		}
		//为了下面的登陆将密码暂时还原成明文
		user.setPassWord(user.getLoginName()+INIT_PASSWORD_SYMBOLS);
		return user;
	}

	@Override
	public UserEntity handlerSort(String code, String type) throws Exception {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("grant_type",Oauth2Constants.GRANT_TYPE));
   	 	param.add(new BasicNameValuePair("code",code));
        param.add(new BasicNameValuePair("redirect_uri",Oauth2Constants.REDIRECT_URI));  
        String url = "";
        String responseJson="";
        if(type.equals(Oauth2Constants.OAUTH_TYPE_DOUBAN)){
        	param.add(new BasicNameValuePair("client_id", Oauth2Constants.CLIENT_ID));  
			param.add(new BasicNameValuePair("client_secret",Oauth2Constants.CLIENT_SECRET));  
			url += Oauth2Constants.OAUTH_TOKEN_DOUBAN;
			responseJson = Oauth2HttpClientUtil.postMethod(url, param
					,Oauth2Constants.URL_CONNECTION_CONN_TIMEOUT,Oauth2Constants.URL_CONNECTION_READ_TIMEOUT);
       	 
        }else if(type.equals(Oauth2Constants.OAUTH_TYPE_QQ)){
       	 	param.add(new BasicNameValuePair("client_id", Oauth2Constants.QQ_CLIENT_ID));  
            param.add(new BasicNameValuePair("client_secret",Oauth2Constants.QQ_CLIENT_SECRET));  
       	 	url += Oauth2Constants.OAUTH_TOKEN_QQ;
       	 	responseJson = Oauth2HttpClientUtil.postMethod(url, param
       	 			,Oauth2Constants.URL_CONNECTION_CONN_TIMEOUT,Oauth2Constants.URL_CONNECTION_READ_TIMEOUT);
       	 	responseJson = this.formatJson(responseJson);
       	 	
        }else if(type.equals(Oauth2Constants.OAUTH_TYPE_WEIBO)){
        	param.add(new BasicNameValuePair("client_id", Oauth2Constants.WEIBO_CLIENT_ID));  
			param.add(new BasicNameValuePair("client_secret",Oauth2Constants.WEIBO_CLIENT_SECRET));
			url += Oauth2Constants.OAUTH_TOKEN_WEIBO;
			responseJson = Oauth2HttpClientUtil.postMethod(url, param
					,Oauth2Constants.URL_CONNECTION_CONN_TIMEOUT,Oauth2Constants.URL_CONNECTION_READ_TIMEOUT);
        }else if(type.equals(Oauth2Constants.OAUTH_TYPE_BAIDU)){
        	param.add(new BasicNameValuePair("client_id", Oauth2Constants.BAIDU_CLIENT_ID));  
			param.add(new BasicNameValuePair("client_secret",Oauth2Constants.BAIDU_CLIENT_SECRET));
			url += Oauth2Constants.OAUTH_TOKEN_BAIDU;
			responseJson = Oauth2HttpClientUtil.postMethod(url, param
					,Oauth2Constants.URL_CONNECTION_CONN_TIMEOUT,Oauth2Constants.URL_CONNECTION_READ_TIMEOUT);
        }
        //发送get、post时发生错误抛出异常
        if(responseJson==null || responseJson.length()<=0){
        	logger.error("result json type is error");
        	throw new Exception("授权登陆失败，请刷新页面重试尝试");
        }
        Oauth2TokenEntity token = this.getOauth2Token(responseJson,type);
        //oauth有误返回null值 TODO 这部分为空时有误
        if(token == null){
        	logger.error("the token is null that can`t get");
        	return null;
        }
        //获取的信息令牌保存
        return this.save(token);
	}
	
	/**
	 * 创建默认用户
	 * @param token
	 * @return
	 */
	public UserEntity getUser(Oauth2TokenEntity token){
		UserEntity user = new UserEntity();
		user.setLoginName(token.getType()+token.getUser_id());
		//初始化默认密码
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String lawsPw = user.getLoginName()+INIT_PASSWORD_SYMBOLS;
		String pw = md5.encodePassword(lawsPw, user.getLoginName());
		user.setPassWord(pw);
		user.setName(token.getUser_name());
		user.setPicture(token.getPicture());
		user.setOauthID(token.getId());
		user.setRegisterDate(new Date());
		return user;
	}
	
	/**
	 * 默认用户附权限
	 * @param user
	 */
	public void initRole(UserEntity user){
		RoleEntity role = roleService.getUserRole();
		UserRolesEntity userRoles = new UserRolesEntity();
		userRoles.setRoleID(role.getId());
		userRoles.setUserID(user.getId());
		List<UserRolesEntity> list = new ArrayList<>();
		list.add(userRoles);
		userRolesService.addRoles(list);
	}
	/**
	 * 分类处理
	 * @param str
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Oauth2TokenEntity getOauth2Token(String str,String type) throws Exception{
		Oauth2TokenEntity token = new Oauth2TokenEntity();
		if(type.equals(Oauth2Constants.OAUTH_TYPE_DOUBAN)){
			token = this.handlerDouBan(str);
		}else if(type.equals(Oauth2Constants.OAUTH_TYPE_QQ)){
			token = this.handlerQQ(str);
		}else if(type.equals(Oauth2Constants.OAUTH_TYPE_WEIBO)){
			token = this.handlerWeiBo(str);
		}else if(type.equals(Oauth2Constants.OAUTH_TYPE_BAIDU)){
			token = this.handlerBaiDu(str);
		}
		return token;
	}
	
	//TODO 未对错误代码做处理
		private Oauth2TokenEntity handlerDouBan(String str) throws Exception{
			Oauth2TokenEntity token = new Oauth2TokenEntity();
			try {
				JSONObject json = JSON.parseObject(str);
				if(json.getString("access_token")==null || json.getString("access_token").length()<=0){
					throw new Exception("授权登陆失败，请刷新页面重试尝试");
				}
				token.setAccess_token(json.getString("access_token"));
				token.setUser_name(json.getString("douban_user_name"));
				token.setUser_id(json.getString("douban_user_id"));
				token.setExpires_in(json.getLong("expires_in"));
				token.setRefresh_token(json.getString("refresh_token"));
				token.setAccess_date(new Date());
				token.setType(Oauth2Constants.OAUTH_TYPE_DOUBAN);
				//获取个人的详细信息
				String url = Oauth2Constants.OAUTH_USERS_DOUBAN+token.getUser_id();
				String detail = Oauth2HttpClientUtil.getMethod(url
						,Oauth2Constants.URL_CONNECTION_CONN_TIMEOUT,Oauth2Constants.URL_CONNECTION_READ_TIMEOUT);
				
				JSONObject detailJson = JSON.parseObject(detail);
				token.setPicture(detailJson.getString("icon"));
			} catch (Exception e) {
				throw new Exception("授权登陆失败，请刷新页面重试尝试");
			}
			return token;
		}
		
		private Oauth2TokenEntity handlerQQ(String str) throws Exception{
			Oauth2TokenEntity user = new Oauth2TokenEntity();
			//此时出错信息为callback({"error":100021,"error_description":"get access token error"});
			try {
				JSONObject json = JSON.parseObject(str);
				user.setAccess_token(json.getString("access_token"));
				user.setExpires_in(json.getLong("expires_in"));
				user.setRefresh_token(json.getString("refresh_token"));
				user.setAccess_date(new Date());
				user.setType(Oauth2Constants.OAUTH_TYPE_QQ);
				//获取openid
				String url = Oauth2Constants.OAUTH_OPENID_QQ+"?access_token="+user.getAccess_token();
				String openidStr = Oauth2HttpClientUtil.getMethod(url
						,Oauth2Constants.URL_CONNECTION_CONN_TIMEOUT,Oauth2Constants.URL_CONNECTION_READ_TIMEOUT);
				openidStr = openidStr.substring(openidStr.indexOf("{"),openidStr.indexOf("}")+1);
				JSONObject openidJson = JSON.parseObject(openidStr);
				user.setUser_id(openidJson.getString("openid"));
				//获取个人详细信息
				String nextUrl = Oauth2Constants.OAUTH_USERS_QQ;
				nextUrl +="?access_token="+user.getAccess_token()+"&oauth_consumer_key="+
						Oauth2Constants.QQ_CLIENT_ID+"&openid="+user.getUser_id();
				String detail = Oauth2HttpClientUtil.getMethod(nextUrl
						,Oauth2Constants.URL_CONNECTION_CONN_TIMEOUT,Oauth2Constants.URL_CONNECTION_READ_TIMEOUT);
				
				JSONObject detailJson = JSON.parseObject(detail);
				user.setUser_name(detailJson.getString("nickname"));
				user.setPicture(detailJson.getString("figureurl_qq_1"));
			} catch (Exception e) {
				throw new Exception("授权登陆失败，请刷新页面重试尝试");
			}
			return user;
		}
		
		public Oauth2TokenEntity handlerWeiBo(String str) throws Exception{
			Oauth2TokenEntity user = new Oauth2TokenEntity();
			try {
				JSONObject json = JSON.parseObject(str);
				user.setAccess_token(json.getString("access_token"));
				user.setExpires_in(json.getLong("expires_in"));
				user.setUser_id(json.getString("uid"));
				user.setAccess_date(new Date());
				user.setType(Oauth2Constants.OAUTH_TYPE_WEIBO);
				
				//获取用户详细信息
				String nextUrl = Oauth2Constants.OAUTH_USERS_WEIBO+"?access_token="+
						user.getAccess_token()+"&uid="+user.getUser_id();
				String detail = Oauth2HttpClientUtil.getMethod(nextUrl
						,Oauth2Constants.URL_CONNECTION_CONN_TIMEOUT,Oauth2Constants.URL_CONNECTION_READ_TIMEOUT);
				
				JSONObject detailJson = JSON.parseObject(detail);
				user.setUser_name(detailJson.getString("name"));
				user.setPicture(detailJson.getString("profile_image_url"));
			} catch (Exception e) {
				throw new Exception("授权登陆失败，请刷新页面重试尝试");
			}
			
			return user;
		}
		
		/**
		 * TODO 
		 *因为这三个字段不知道何用，故此现在这记录、不存
		 *scope": "basic email",
	    "session_key": "ANXxSNjwQDugf8615OnqeikRMu2bKaXCdlLxn",
	    "session_secret": "248APxvxjCZ0VEC43EYrvxqaK4oZExMB",
		 * @throws Exception 
		 */
		public Oauth2TokenEntity handlerBaiDu(String str) throws Exception{
			Oauth2TokenEntity user = new Oauth2TokenEntity();
			try {
				JSONObject json = JSON.parseObject(str);
				user.setAccess_token(json.getString("access_token"));
				user.setExpires_in(json.getLong("expires_in"));
				user.setRefresh_token(json.getString("refresh_token"));
				user.setAccess_date(new Date());
				user.setType(Oauth2Constants.OAUTH_TYPE_BAIDU);
				//获取用户详细信息的部分
				String nextUrl = Oauth2Constants.OAUTH_USERS_BAIDU+"?access_token="+user.getAccess_token();
				String detail = Oauth2HttpClientUtil.getMethod(nextUrl
						,Oauth2Constants.URL_CONNECTION_CONN_TIMEOUT,Oauth2Constants.URL_CONNECTION_READ_TIMEOUT);
				
				JSONObject detailJson = JSON.parseObject(detail);
				user.setUser_name(detailJson.getString("uname"));
				user.setUser_id(detailJson.getString("uid"));
				user.setPicture(Oauth2Constants.OAUTH_PICTURE_BAIDU_SMALL+detailJson.getString("portrait"));
			} catch (Exception e) {
				throw new Exception("授权登陆失败，请刷新页面重试尝试");
			}
			
			return user;
		}
	
	public Oauth2TokenEntity downloadPic(Oauth2TokenEntity token) {
		//从远程下载图片，并替换为本地路径
		String remoteUrl = token.getPicture();
		String fileName = new Date().getTime()+".png";
		//TODO 上传路径暂时从配置文件中读取，有时间把上传路径这部分统一一下
		org.springframework.core.io.Resource resource = new ClassPathResource("tempFilel.properties");
		Properties props;
		String path = "";
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			path = props.getProperty("temp.file.physicalPath");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		if(!new File(path+"upload").exists()){
			new File(path+"upload").mkdirs();
		}
		String filePath = path+"upload"+File.separator+fileName;
		try {
			Oauth2HttpClientUtil.getMethodPicture(remoteUrl, filePath,Oauth2Constants.URL_CONNECTION_CONN_TIMEOUT
					,Oauth2Constants.URL_CONNECTION_READ_TIMEOUT);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		String virPath = "upload/"+fileName;
		token.setPicture(virPath);
		return token;
	}	
		
	/**
     * 将string转换成json字符串的格式
     */
    public String formatJson(String str){
    	String json = "{";
    	if(str.indexOf("&")>=0){
    		String[] temp = str.split("&");
        	for(int i=0;i<temp.length;i++){
        		String[] keyValue = temp[i].split("=");
        		if(i!=0){
        			json +=",";
        		}
        		json +="\""+keyValue[0]+"\""+":"+"\""+keyValue[1]+"\"";
        	}
    	}
    	json +="}";
    	return json;
    }
}
