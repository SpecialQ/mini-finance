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
	
    private static Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = null;
    
    @Autowired
    private AuthorizationDao authDao;
    
    public WebFilterSecurityMetadataSource init(){
    	loadResourceDefine();
    	return this;
    }
    
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
    
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			if (entry.getKey().matches(request)) {
				return entry.getValue();
			}
		}
		return null;
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
