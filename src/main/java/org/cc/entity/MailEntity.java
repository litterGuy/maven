package org.cc.entity;

import java.util.Map;

/**
 * 邮件类
 * @author litterGuy
 *
 */
public class MailEntity {
	private String[] receivers;//收件人
	private String sender;//发件人
	private String subject;//邮件主题
	private String template;//邮件模版
	private Map<String, Object> map;//邮件数据
	
	public String[] getReceivers() {
		return receivers;
	}
	public void setReceivers(String[] receivers) {
		this.receivers = receivers;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
