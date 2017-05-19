package com.AsiaApe.security.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The name of the configureGlobal method is not important. 
 * However, it is important to only configure 
 * AuthenticationManagerBuilder in a class annotated with either 
 * @EnableWebSecurity, @EnableGlobalMethodSecurity, or @EnableGlobalAuthentication. 
 * Doing otherwise has unpredictable results.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private DataSource dataSource;
	
	public static final String USERS_BY_USERNAME_QUERY = "select username,password,enabled "
			+ "from user "
			+ "where username = ?";
	public static final String AUTHORITIES_BY_USERNAME_QUERY = "select username,role_name as authority "
			+ "from user u, role r, user_role_rela rela "
			+ "where u.id = rela.user_id "
				+ "and r.id = rela.role_id "
				+ "and u.username = ?";
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("Using SecurityConfig(HttpSecurity).");
		http
	        .authorizeRequests()
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	            .loginPage("/login.do").permitAll()
	            .loginProcessingUrl("/login")
	            .successForwardUrl("/views/Success.html")
	            .and()
	        .logout()
	        	.permitAll()
	        	.and()
	        .httpBasic()
	        	.and()
	        .csrf().disable();
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		logger.debug("# SecurityConfig.configureGlobal(AuthenticationManagerBuilder auth)");
        auth
        	.jdbcAuthentication()
        		.dataSource(dataSource)
        		.usersByUsernameQuery(USERS_BY_USERNAME_QUERY)
        		.authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
	}
}
