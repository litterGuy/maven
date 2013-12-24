package org.cc.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.cc.entity.ShortURLEntity;
import org.cc.utils.DateTimeUtil;
import org.cc.utils.SysConfigUtil;
import org.cc.utils.SysConstant;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
@Namespace(value="/shortURL")
public class ShortURLAction {
	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(ShortURLAction.class);
	
	private String param;
	private String type;
	private String redirectURL;
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Action(value="redirect",results={
			@Result(name="success",location="${redirectURL }",type="redirect"),
			@Result(name="error",location="/success.jsp")
	})
	public String redirect(){
		String result="error";
		if(StringUtils.isNotBlank( param)){
			ShortURLEntity shortEntity = mongoTemplate.findOne(new Query(Criteria.where("shortURL").is(param)), ShortURLEntity.class);
			if(shortEntity!=null){
				if(shortEntity.getType() == SysConstant.SHORT_URL_MAIL){
					//验证时间
					Date nowDate = new Date();
					Date expiresDate = DateTimeUtil.getRecentDay(shortEntity.getCreateTime(), Integer.parseInt(SysConfigUtil.getMailParam("mail.expiresTime")));
					if(nowDate.after(expiresDate)){
						type = "expires";
					}
				}
				redirectURL = shortEntity.getOriginURL();
				result = "success";
				mongoTemplate.remove(shortEntity);
			}else{
				type = "used";
			}
		}
		return result;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRedirectURL() {
		return redirectURL;
	}
	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
}
