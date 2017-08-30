package com.AsiaApe.security.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.AsiaApe.dao.AuthorizationDao;
import com.AsiaApe.pojo.security.Authorization;
import com.AsiaApe.pojo.security.Role;

@Component("webFilterSecurityMetadataSource")
public class WebFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private static final Logger logger = LoggerFactory.getLogger(WebFilterSecurityMetadataSource.class);
	
	/**
	 * 各URL请求及对应权限仓库
	 */
    private static Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = null;
    
    @Autowired
    private AuthorizationDao authDao;
    
    public WebFilterSecurityMetadataSource init(){
    	loadResourceDefine();
    	return this;
    }
    
    /**
     * 初始化加载所有系统授权信息。
     */
	private void loadResourceDefine() {
		resourceMap = new HashMap<RequestMatcher, Collection<ConfigAttribute>>();
		List<Authorization> authorizations = authDao.getAllAuthorizations();
		for (Authorization auth : authorizations) {
			Collection<ConfigAttribute> ConfigAttributes = new ArrayList<ConfigAttribute>();
			List<Role> roles = auth.getRoles();
			for (Role role : roles) {
				ConfigAttribute configAttribute = new SecurityConfig(role.getRoleName());
				ConfigAttributes.add(configAttribute);
			}
			RequestMatcher requestMatcher = new AntPathRequestMatcher(auth.getPath());
			resourceMap.put(requestMatcher, ConfigAttributes);
			logger.debug("加载授权资源：{}，授权角色：{}", requestMatcher.toString(), roles.toString());
		}
	}
    
	/**
	 * 获取所有对应路径的授权角色，
	 * 若无对应授权角色，则抛出AccessDeniedException的403错误。
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		Collection<ConfigAttribute> result = new ArrayList<ConfigAttribute>();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			if (entry.getKey().matches(request)) {
				result.addAll(entry.getValue());
			}
		}
		if (result.size() <= 0) {
			throw new AccessDeniedException("no right");
		}
		return result;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
