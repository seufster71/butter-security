package org.mangobutter.configuration;

import org.mangobutter.controller.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class MultiHttpSecurityConfig {
	
	@Configuration
	@Order(1)                                                        
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
		
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.antMatcher("/api/**") 
				.exceptionHandling()
					.authenticationEntryPoint(restAuthenticationEntryPoint)
				.and()
				.authorizeRequests()
					.antMatchers("/api/login/**").permitAll()
					.anyRequest().authenticated();
		}
	}

	@Configuration                                                   
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
	    private AccessDeniedHandler accessDeniedHandler;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
            .authorizeRequests()
				.antMatchers("/", "/login/**", "/libs/**", "/public/**").permitAll()
				.anyRequest().authenticated()
            .and()
            .formLogin()
				.loginPage("/login/login.html")
				.permitAll()
				.and()
            .logout()
            	.logoutUrl("/logout.html")
            	.invalidateHttpSession(true)
				.and()
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
		}
	}
}
