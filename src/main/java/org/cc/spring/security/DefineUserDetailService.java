package org.cc.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.cc.entity.RoleEntity;
import org.cc.entity.UserEntity;
import org.cc.service.RoleService;
import org.cc.service.UserService;
import org.cc.utils.SysConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailService")
public class DefineUserDetailService implements UserDetailsService {
	private static final Logger logger = Logger.getLogger(DefineUserDetailService.class);
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		//根据用户名查找用户
				UserEntity user = userService.getByLoginName(userName);
				if (user == null) {
					//用户不存在，抛异常
					throw new UsernameNotFoundException("user not found");
				}
				//新的权限列表
				Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				//根据用户名查找对应的权限
				List<RoleEntity> roleList = new ArrayList<RoleEntity>();
				roleList = roleService.getRoleByUserID(user.getId());
				//将查找到的权限，封装成GrantedAuthorityImpl放到权限列表中
				for (RoleEntity role : roleList) {
					logger.debug(role.getName());
					authorities.add(new SimpleGrantedAuthority(role.getName()));
				}
				//返回认证用户
				/**
				 * org.springframework.security.core.userdetails.User
			     * @param username 用户名
			     * @param password 用户名密码
			     * @param enabled 如果账户可用设置为true
			     * @param accountNonExpired 如果账户未失效设置为true
			     * @param credentialsNonExpired 如果认证未失效设置为true
			     * @param accountNonLocked 如果账户未被锁定设置为true
			     * @param authorities 对应权限列表
			     */
				boolean enabled = true;
				if( user.getEnable() == SysConstant.USER_UNENABLED){
					enabled = false;
				}
				DefineUser userDetail = new DefineUser(user.getLoginName(), user.getPassWord(),enabled, true, true, true, authorities);
				userDetail.setUserID(user.getId());
				return userDetail;
	}
	
}
