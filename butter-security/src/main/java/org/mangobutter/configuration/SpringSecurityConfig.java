package org.mangobutter.configuration;

import org.mangobutter.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
					.antMatchers("/", "/login/**", "/api/login/**", "/public/**").permitAll()
					.anyRequest().authenticated()
                .and()
                .formLogin()
					.loginPage("/login/login.html")
					.permitAll()
					.and()
                .logout()
                .logoutUrl("/logout.html")
					.and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    // check from custom provider
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider);
        
    }
}
