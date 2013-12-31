package org.cc.spring.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cc.entity.UserEntity;
import org.cc.service.Oauth2Service;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

public class DefineUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{  
    //用户名  
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";  
    //密码  
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";  
    //需要回调的URL 自定义参数  
    public static final String SPRING_SECURITY_FORM_REDERICT_KEY = "spring-security-redirect";  
     
    @Resource
    private Oauth2Service oauth2Service;
    
    /**  
     * @deprecated If you want to retain the username, cache it in a customized {@code AuthenticationFailureHandler}  
     */  
    @Deprecated  
    public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";  

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;  
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;  
    private String redirectParameter = SPRING_SECURITY_FORM_REDERICT_KEY;  
    @SuppressWarnings("unused")
	private boolean postOnly = true;  

    //~ Constructors ===================================================================================================  

    public DefineUsernamePasswordAuthenticationFilter() {  
       super();  
    }  

    //~ Methods ========================================================================================================  

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {  
    	String code = request.getParameter("code");//返回的code
        String state = request.getParameter("state");//平台类型
        String error = request.getParameter("error");//错误码，主要为应用授权时拒绝授权
//    	if (postOnly && !request.getMethod().equals("POST")) {  
//            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());  
//        }
        UserEntity user = new UserEntity();
        if((error!=null && error.length()>0)){
       	 throw new AuthenticationServiceException(null);
        }
        if(request.getParameter("code")!=null&&request.getParameter("code").length()>0){  
       	try {
       		user = oauth2Service.handlerSort(code, state);
			} catch (Exception e) {
				throw new AuthenticationServiceException("授权失败，请刷新页面重新尝试");
			}
       	 if(user==null){
       		 throw new AuthenticationServiceException("授权失败，请刷新页面重新尝试");
       	 }
        }
        String username = obtainUsername(request);  
        String password = obtainPassword(request);  
        String redirectUrl = obtainRedercitUrl(request); 
      //此时的登陆为第三方授权登陆
        if(code!=null && code.length()>0){
       	 username = user.getLoginName();
       	 password = user.getPassWord();
        }
        if (username == null) {  
            username = "";  
        }  

        if (password == null) {  
            password = "";  
        }  
        //自定义回调URL，若存在则放入Session  
        if(redirectUrl != null && !"".equals(redirectUrl)){  
            request.getSession().setAttribute("callCustomRediretUrl", redirectUrl);  
        }  
        username = username.trim();
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        //为oauth登录，自定义部分
        DefineUsernamePasswordAuthenticationToken authRequest = new DefineUsernamePasswordAuthenticationToken(username, password,state);
        // Allow subclasses to set the "details" property  
        setDetails(request, authRequest);  
        return this.getAuthenticationManager().authenticate(authRequest);  
    }  

    /** 
     * Enables subclasses to override the composition of the password, such as by including additional values 
     * and a separator.<p>This might be used for example if a postcode/zipcode was required in addition to the 
     * password. A delimiter such as a pipe (|) should be used to separate the password and extended value(s). The 
     * <code>AuthenticationDao</code> will need to generate the expected password in a corresponding manner.</p> 
     * 
     * @param request so that request attributes can be retrieved 
     * 
     * @return the password that will be presented in the <code>Authentication</code> request token to the 
     *         <code>AuthenticationManager</code> 
     */  
    protected String obtainPassword(HttpServletRequest request) {  
        return request.getParameter(passwordParameter);  
    }  

    /** 
     * Enables subclasses to override the composition of the username, such as by including additional values 
     * and a separator. 
     * 
     * @param request so that request attributes can be retrieved 
     * 
     * @return the username that will be presented in the <code>Authentication</code> request token to the 
     *         <code>AuthenticationManager</code> 
     */  
    protected String obtainUsername(HttpServletRequest request) {  
        return request.getParameter(usernameParameter);  
    }  
      
      
    protected String obtainRedercitUrl(HttpServletRequest request) {  
        return request.getParameter(redirectParameter);  
    }  

    /** 
     * Provided so that subclasses may configure what is put into the authentication request's details 
     * property. 
     * 
     * @param request that an authentication request is being created for 
     * @param authRequest the authentication request object that should have its details set 
     */  
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {  
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));  
    }  

    /** 
     * Sets the parameter name which will be used to obtain the username from the login request. 
     * 
     * @param usernameParameter the parameter name. Defaults to "j_username". 
     */  
    public void setUsernameParameter(String usernameParameter) {  
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");  
        this.usernameParameter = usernameParameter;  
    }  

    /** 
     * Sets the parameter name which will be used to obtain the password from the login request.. 
     * 
     * @param passwordParameter the parameter name. Defaults to "j_password". 
     */  
    public void setPasswordParameter(String passwordParameter) {  
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");  
        this.passwordParameter = passwordParameter;  
    }  

    /** 
     * Defines whether only HTTP POST requests will be allowed by this filter. 
     * If set to true, and an authentication request is received which is not a POST request, an exception will 
     * be raised immediately and authentication will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method 
     * will be called as if handling a failed authentication. 
     * <p> 
     * Defaults to <tt>true</tt> but may be overridden by subclasses. 
     */  
    public void setPostOnly(boolean postOnly) {  
        this.postOnly = postOnly;  
    }  

  
}  