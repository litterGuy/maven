package org.cc.utils;

public interface Oauth2Constants {
	/**API Key*/
	public static final String CLIENT_ID = "01f2f590c96671f205d8e0b25c3355d6";
	/**Secret*/
	public static final String CLIENT_SECRET = "1d49d89fecead113";
	
	public static final String QQ_CLIENT_ID = "100583552";
	public static final String QQ_CLIENT_SECRET = "32f0ca0b5a7593bb4c77e32d084099b0";
	
	public static final String WEIBO_CLIENT_ID = "454236053";
	public static final String WEIBO_CLIENT_SECRET = "4bb384e2ced222af6b832e6c61ead27a";
	
	public static final String BAIDU_CLIENT_ID = "6Gylodo7KeDwb98t8ipRGhjI";
	public static final String BAIDU_CLIENT_SECRET = "749G6nZGqUe5jBhtYmHmAWdvC766TRCH";
	
	/**回调url*/
	public static final String REDIRECT_URI = "http://127.0.0.1:8080/maven/j_spring_security_check";
	
	public static final String GRANT_TYPE = "authorization_code";
	
	/**Oauth登陆平台类型*/
	public static final String OAUTH_TYPE_DOUBAN = "douban";
	public static final String OAUTH_TYPE_QQ = "qq";
	public static final String OAUTH_TYPE_WEIBO = "weibo";
	public static final String OAUTH_TYPE_BAIDU = "baidu";
	
	/**授权获取code的url*/
	public static final String OAUTH_AUTHORIZE_DOUBAN="https://www.douban.com/service/auth2/auth";
	public static final String OAUTH_AUTHORIZE_QQ="https://graph.qq.com/oauth2.0/authorize";
	public static final String OAUTH_AUTHORIZE_WEIBO="https://api.weibo.com/oauth2/authorize";
	public static final String OAUTH_AUTHORIZE_BAIDU="https://openapi.baidu.com/oauth/2.0/authorize";
	
	/**获取token的url*/
	public static final String OAUTH_TOKEN_DOUBAN="https://www.douban.com/service/auth2/token";
	public static final String OAUTH_TOKEN_QQ="https://graph.qq.com/oauth2.0/token";
	public static final String OAUTH_TOKEN_WEIBO="https://api.weibo.com/oauth2/access_token";
	public static final String OAUTH_TOKEN_BAIDU="https://openapi.baidu.com/oauth/2.0/token";
	
	/**获取授权用户的详细信息*/
	public static final String OAUTH_USERS_DOUBAN="http://api.douban.com/labs/bubbler/user/";
	public static final String OAUTH_USERS_QQ="https://graph.qq.com/user/get_user_info";
	public static final String OAUTH_USERS_WEIBO="https://api.weibo.com/2/users/show.json";
	public static final String OAUTH_USERS_BAIDU="https://openapi.baidu.com/rest/2.0/passport/users/getLoggedInUser";
	
	/**获取openid的url*/
	public static final String OAUTH_OPENID_QQ="https://graph.qq.com/oauth2.0/me";
	
	/**java模拟发送get、post请求时的连接时间、超时时间*/
	public static final Integer URL_CONNECTION_CONN_TIMEOUT = 3000;
	public static final Integer URL_CONNECTION_READ_TIMEOUT = 3000;
	
	
	/**百度帐号获取头像的链接前半部分*/
	public static final String OAUTH_PICTURE_BAIDU_SMALL="http://tb.himg.baidu.com/sys/portraitn/item/";
	public static final String OAUTH_PICTURE_BAIDU_LARGE="http://tb.himg.baidu.com/sys/portrait/item/";
}
