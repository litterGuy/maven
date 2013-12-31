package org.cc.spring.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
/**
 * 自定义该token，增加oauth时第三方平台信息
 * @author litterGuy
 *
 */
public class DefineUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = -1868053670682020911L;

	private Object principal;
	private Object credentials;
	private Object type;
	
	public DefineUsernamePasswordAuthenticationToken(Object principal,
			Object credentials) {
		super(principal, credentials);
	}
	
	public DefineUsernamePasswordAuthenticationToken(Object principal,
			Object credentials,Object type) {
		super(null, type);
        this.principal = principal;
        this.credentials = credentials;
		this.type = type;
		setAuthenticated(false);
	}

	public Object getPrincipal() {
		return principal;
	}

	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

	public Object getCredentials() {
		return credentials;
	}

	public void setCredentials(Object credentials) {
		this.credentials = credentials;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}
	
}
