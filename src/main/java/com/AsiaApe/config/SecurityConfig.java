package com.AsiaApe.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import com.AsiaApe.security.manager.WebAccessDecisionManager;
import com.AsiaApe.security.manager.WebFilterSecurityMetadataSource;

/**
 * The name of the configureGlobal method is not important. 
 * However, it is important to only configure 
 * AuthenticationManagerBuilder in a class annotated with either 
 * @EnableWebSecurity, @EnableGlobalMethodSecurity, or @EnableGlobalAuthentication. 
 * Doing otherwise has unpredictable results.
 */
@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private WebAccessDecisionManager webAccessDecisionManager;
	@Autowired
	private WebFilterSecurityMetadataSource webFilterSecurityMetadataSource;
	
	private static final String SECRET = "yangshuqi";
	
	private static final PasswordEncoder passwordEncoder = new StandardPasswordEncoder(SECRET);
	
	
	public static final String USERS_BY_USERNAME_QUERY = "select username,password,enabled "
			+ "from user "
			+ "where username = ?";
	public static final String AUTHORITIES_BY_USERNAME_QUERY = "select username,role_name as authority "
			+ "from user u, role r, user_role_rela rela "
			+ "where u.id = rela.user_id "
				+ "and r.id = rela.role_id "
				+ "and u.username = ?";
	
	@Bean
	public FilterSecurityInterceptor webFilterSecurityInterceptor(){
		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		filterSecurityInterceptor.setAccessDecisionManager(webAccessDecisionManager);
		filterSecurityInterceptor.setSecurityMetadataSource(webFilterSecurityMetadataSource.init());
		return filterSecurityInterceptor;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/resources/**")
				.antMatchers("/login.do");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	        .authorizeRequests()
	            .anyRequest().authenticated()    //尚未匹配的任何URL要求用户进行身份验证
	            .and()
	        .formLogin()
	            .loginPage("/login.do").permitAll()    // 允许所有用户访问我们登录页
	            .loginProcessingUrl("/login")    // 指定一个POST请求用来验证用户
				.successForwardUrl("/redirect.do?"
						+ "templateName=homePage&"
						+ "selector=home")    // 设置登陆成功后的页面路径
				.and()
	        .logout()
	        	.permitAll()
	        	.and()
	        .httpBasic()
	        	.and()
	        .csrf().disable()
	        .addFilterBefore(webFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	.jdbcAuthentication()
        		.dataSource(dataSource)
        		.passwordEncoder(passwordEncoder)
        		.usersByUsernameQuery(USERS_BY_USERNAME_QUERY)
        		.authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
	}
}
