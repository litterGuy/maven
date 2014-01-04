package org.cc.utils;

public interface SysConstant {
	/**帐号是否激活 是*/
	byte USER_VERIFY_YES = 0;
	/**帐号是否激活 否*/
	byte USER_VERIFY_NO = 1;
	/**短链接类型 邮件激活*/
	int SHORT_URL_MAIL=0;
	/**短连接类型 */
	int SHORT_URL_PASSWORD_MAIL=1;
	/**帐号可用*/
	int USER_ENABLED=0;
	/**帐号不可用*/
	int USER_UNENABLED=1;
}
