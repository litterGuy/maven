package org.cc.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.cc.dao.MongoDBDao;
import org.cc.entity.MailEntity;
import org.cc.entity.RoleEntity;
import org.cc.entity.ShortURLEntity;
import org.cc.entity.UserEntity;
import org.cc.entity.UserRolesEntity;
import org.cc.service.RoleService;
import org.cc.service.UserRolesService;
import org.cc.service.UserService;
import org.cc.spring.security.DefineUser;
import org.cc.utils.MailTemplate;
import org.cc.utils.MailUtil;
import org.cc.utils.ShortURLUtil;
import org.cc.utils.SysConfigUtil;
import org.cc.utils.SysConstant;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;


@Controller
@Namespace("/login")
@Scope("prototype") 
public class LoginAction extends ActionSupport{
	private static final long serialVersionUID = -819406853760504924L;

	private final static Logger logger = Logger.getLogger(LoginAction.class);
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private UserRolesService userRolesService;
	@Resource
	private MailUtil mailUtil;
	@Resource
	private MongoDBDao mongoDBDao;
	
	private UserEntity user;
	private String loginStatus;
	private String loginName;
	private String type;
	
	@Action(value = "login", results = {
															@Result(name = "success", location = "/user/index.do",type="redirect"),
															@Result(name = "error", location = "/login.jsp")
														 }) 
	public String login(){
		String result = "error";
         Authentication   auth   =  SecurityContextHolder.getContext().getAuthentication();
         if(auth.getPrincipal()   instanceof   DefineUser)      
         {      
        	 DefineUser userDetails = (DefineUser) auth.getPrincipal();
        	 if(userDetails!=null){
     			result = "success";
     		}
         }
		return result;
	}
	
	@Action(value="validLoginName")
	public void validLoginName(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");// 设置输出为文字流
		response.setCharacterEncoding("UTF-8");
		boolean flag = false;
		if(StringUtils.isNotBlank(loginName)){
			flag = userService.validLoginName(loginName);
		}
		try {
			response.getWriter().print(flag);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	@Action(value="validEmail")
	public void validEmail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/plain");// 设置输出为文字流
		response.setCharacterEncoding("UTF-8");
		String email = request.getParameter("email");
		boolean flag = false;
		if(StringUtils.isNotBlank(email)){
			flag = userService.validEmail(email);
		}
		try {
			response.getWriter().print(flag);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	@Action(value="register",
			 interceptorRefs = { @InterceptorRef("token"),
			@InterceptorRef("defaultStack") }, 
			results = {
			@Result(name = "success", location = "/success.jsp"),
			@Result(name = "error", location = "/register.jsp"),
			@Result(name = "invalid.token", location = "/success.jsp",type="redirect")
		 })
	public String register(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserEntity user = new UserEntity();
		user.setLoginName(loginName);
		user.setName(name);
		user.setEmail(email);
		//用用户名做盐值加密
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		user.setPassWord(md5.encodePassword(password, loginName));
		Date nowDate = new Date();
		user.setRegisterDate(nowDate);
		user.setVerify(SysConstant.USER_VERIFY_NO);
		user.setEnable(SysConstant.USER_ENABLED);
		userService.save(user);
		//给用户附权限
		RoleEntity role = roleService.getUserRole();
		UserRolesEntity userRoles = new UserRolesEntity();
		userRoles.setRoleID(role.getId());
		userRoles.setUserID(user.getId());
		List<UserRolesEntity> list = new ArrayList<>();
		list.add(userRoles);
		userRolesService.addRoles(list);
		try {
			//url编码存库
			String originUrl = "/login/verify.do?loginName="+user.getLoginName()+"&token="+nowDate.getTime();
			String shortUrl = ShortURLUtil.shortUrl(originUrl)[0];
			ShortURLEntity shortUrlEntity = new ShortURLEntity();
			shortUrlEntity.setOriginURL(originUrl);
			shortUrlEntity.setShortURL(shortUrl);
			shortUrlEntity.setCreateTime(nowDate);
			shortUrlEntity.setType(SysConstant.SHORT_URL_MAIL);
			//MongoDB入库
			mongoDBDao.save(shortUrlEntity);
			String mailRUL = "http://localhost:8080/maven/user/mail/"+shortUrl;
			String template = MailTemplate.sendRegisterTemplate(mailRUL, user);
			MailEntity mail = new MailEntity();
			mail.setTemplate(template);
			mail.setReceivers(new String[]{user.getEmail()});
			mail.setSubject("Email地址验证");
			mail.setSender(SysConfigUtil.getMailParam(SysConfigUtil.MAIL_SENDER));
			mailUtil.sendRegisterMail(mail);
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}
		return "success";
	}
	@Action(value = "verify", results = {
			@Result(name = "result", location = "/success.jsp")
		 }) 
	public String verify(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String loginName = request.getParameter("loginName");
		if(StringUtils.isNotBlank(loginName)){
			UserEntity user = userService.getByLoginName(loginName);
			user.setVerify(SysConstant.USER_VERIFY_YES);
			userService.update(user);
			type = "activated";
		}else{
			type = "error";
		}
		return "result";
	}
	
	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
