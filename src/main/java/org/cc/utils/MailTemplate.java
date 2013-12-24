package org.cc.utils;

import org.cc.entity.UserEntity;

public class MailTemplate {
	/**
	 * 注册成功激活页
	 */
	public static String sendRegisterTemplate(String activateURL,UserEntity user){
		StringBuilder template = new StringBuilder();
		template.append("<div style='margin:10px auto;width:940px;font-size: 14px;line-height: 20px;color: #333;'>");
		template.append("<div style='display: block;padding-top: 30px;'>");
		template.append("<div style='padding: 60px;margin-bottom: 30px;font-size: 18px;font-weight: 200;line-height: 30px;color: inherit;background-color: #eeeeee;-webkit-border-radius: 6px;-moz-border-radius: 6px;border-radius: 6px;' >");
		template.append("<h2>注册成功</h2>");
		template.append("<p>您的账户已经注册成功，现在可以登录到官方网站平台。通过有效性验证后，才可参与互动社区的发帖、回复等操作。请点击以下地址:(链接有效期"+SysConfigUtil.getMailParam(SysConfigUtil.MAIL_EXPIRES)+"天)</p>");
		template.append("<div style='padding: 15px;margin-bottom: 20px;border: 1px solid transparent;border-radius: 4px;color: #3a87ad;background-color: #d9edf7;border-color: #bce8f1;;'>");
		//激活URL
		template.append("<a href='"+activateURL+"'>"+activateURL+"</a>");
		template.append("</div>");
		template.append("<p>如果您并没有进行上述操作，请忽略该邮件。您不需要退订或进行其他进一步的操作。此邮件为系统自动发出的邮件，请勿直接回复。</p>");
		template.append("</div>");
		template.append("</div>");
		template.append("</div>");
		template.append("");
		return template.toString();
	}
}
