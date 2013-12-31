package org.cc.spring.security;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
/**
 * 自定义验证方式
 * @author litterGuy
 *
 */
public class DefineDaoAuthenticationProvider  extends DaoAuthenticationProvider{

	@SuppressWarnings("deprecation")
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		Object salt = null;

        if (this.getSaltSource() != null) {
            salt = this.getSaltSource().getSalt(userDetails);
        }

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
        }
        DefineUsernamePasswordAuthenticationToken token = (DefineUsernamePasswordAuthenticationToken) authentication;
        //oauth登陆 不对比密码
        if(token!=null && token.getType() == null){
        	String presentedPassword = authentication.getCredentials().toString();

            if (!getPasswordEncoder().isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
                logger.debug("Authentication failed: password does not match stored value");

                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
            }
        }
	}
	
}
