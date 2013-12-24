package org.cc.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="shortURLEntity")
public class ShortURLEntity {
	@Indexed
	private String id;
	private String shortURL;
	private String originURL;
	private Date createTime;
	private int type;//类型
	public String getShortURL() {
		return shortURL;
	}
	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}
	public String getOriginURL() {
		return originURL;
	}
	public void setOriginURL(String originURL) {
		this.originURL = originURL;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
