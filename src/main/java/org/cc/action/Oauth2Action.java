package org.cc.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.cc.utils.Oauth2Constants;
import org.cc.utils.OauthUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype") 
public class Oauth2Action {
	private String url="";
	
	@Action(value = "/oauth2/authorize", results = {
			@Result(name = "success", location = "${url }",type="redirect")
		 }) 
	public String oauth2(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");//第三方登陆的类型
		if(type!=null && type.length()>0){
			url +=OauthUtil.getValue(Oauth2Constants.OAUTH_AUTHORIZE, type);
			url +="?client_id="+OauthUtil.getValue(Oauth2Constants.OAUTH_APIKEY, type);
			url +="&redirect_uri="+OauthUtil.getSimpleValue(Oauth2Constants.REDIRECT_URI);
			url +="&state="+type;
			url +="&response_type=code";
		}
		return "success";
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
