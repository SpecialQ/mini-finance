package com.AsiaApe.security.manager;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component("webAccessDecisionManager")
public class WebAccessDecisionManager implements AccessDecisionManager {

	//检查用户是否够权限访问资源
    //参数authentication是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息
    //参数object是url
    //参数configAttributes所需的权限
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null)
            return;
		if(configAttributes.size() <= 0)
			return;
		
        Iterator<ConfigAttribute> authorities = configAttributes.iterator();
        String needAuthority = null;
        while(authorities.hasNext()){
            ConfigAttribute authority = authorities.next();
            if(authorities == null || (needAuthority = authority.getAttribute()) == null)
            	continue;
            for(GrantedAuthority ga : authentication.getAuthorities()){
                if(needAuthority.equals(ga.getAuthority().trim())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
