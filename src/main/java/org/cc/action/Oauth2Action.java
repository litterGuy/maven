package org.cc.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.cc.utils.Oauth2Constants;
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
			if(type.equals(Oauth2Constants.OAUTH_TYPE_DOUBAN)){
				url +=Oauth2Constants.OAUTH_AUTHORIZE_DOUBAN;
				url +="?client_id="+Oauth2Constants.CLIENT_ID;
				url +="&redirect_uri="+Oauth2Constants.REDIRECT_URI;
				url +="&state="+Oauth2Constants.OAUTH_TYPE_DOUBAN;
				url +="&response_type=code";
			}else if(type.equals(Oauth2Constants.OAUTH_TYPE_QQ)){
				url +=Oauth2Constants.OAUTH_AUTHORIZE_QQ;
				url +="?response_type=code";
				url +="&client_id="+Oauth2Constants.QQ_CLIENT_ID;
				url +="&scope=get_simple_userinfo";
				url +="&redirect_uri="+Oauth2Constants.REDIRECT_URI;
				url +="&state="+Oauth2Constants.OAUTH_TYPE_QQ;
			}else if(type.equals(Oauth2Constants.OAUTH_TYPE_WEIBO)){
				url +=Oauth2Constants.OAUTH_AUTHORIZE_WEIBO;
				url +="?client_id="+Oauth2Constants.WEIBO_CLIENT_ID;
				url +="&redirect_uri="+Oauth2Constants.REDIRECT_URI;
				url +="&state="+Oauth2Constants.OAUTH_TYPE_WEIBO;
				url +="&response_type=code";
			}else if(type.equals(Oauth2Constants.OAUTH_TYPE_BAIDU)){
				url +=Oauth2Constants.OAUTH_AUTHORIZE_BAIDU;
				url +="?client_id="+Oauth2Constants.BAIDU_CLIENT_ID;
				url +="&redirect_uri="+Oauth2Constants.REDIRECT_URI;
				url +="&state="+Oauth2Constants.OAUTH_TYPE_BAIDU;
				url +="&response_type=code";
			}
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
