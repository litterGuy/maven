package org.cc.utils;

public interface Oauth2Constants {
	/**配置文件名称*/
	public static final String OAUTH_CONFIG_FILE = "oauth-config.properties";
	/**请求的连接时间和超时时间*/
	public static final String URL_CONNECTION_CONN_TIMEOUT = "oauth.url.connection.conn.timeout";
	public static final String URL_CONNECTION_READ_TIMEOUT = "oauth.url.connection.read.timeout";
	
	/**回调url*/
	public static final String REDIRECT_URI = "oauth.redirect.uri";
	/**获取authorize的类型*/
	public static final String GRANT_TYPE = "oauth.authorize.type";
	
	/**Oauth登陆平台类型*/
	public static final String OAUTH_TYPE_DOUBAN = "douban";
	public static final String OAUTH_TYPE_QQ = "qq";
	public static final String OAUTH_TYPE_WEIBO = "weibo";
	public static final String OAUTH_TYPE_BAIDU = "baidu";
	
	/**api key*/
	public static final String OAUTH_APIKEY = "oauth.default.apikey";
	/**secret key*/
	public static final String OAUTH_SECRETKEY = "oauth.default.secretkey";
	/**授权地址*/
	public static final String OAUTH_AUTHORIZE = "oauth.default.authorize";
	/**token地址*/
	public static final String OAUTH_TOKEN = "oauth.default.token";
	/**user信息地址*/
	public static final String OAUTH_USER = "oauth.default.user";
	/**获取openid的地址*/
	public static final String OAUTH_OPENID = "oauth.qq.openid";
	
	/**百度帐号获取头像的链接前半部分*/
	public static final String OAUTH_PICTURE_BAIDU_SMALL="oauth.baidu.portrait.small";
	public static final String OAUTH_PICTURE_BAIDU_LARGE="oauth.baidu.portrait.larger";
	
}
