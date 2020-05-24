package com.company.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationProvider authenticationProvider;
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private MyUserDetailsService userDetailService;

	@Bean
	public AuthenticationManager authenticationManager() {
		System.out.println("ConfigSecurity : get-authenticationManager");
		return new ProviderManager(Arrays.asList(authenticationProvider,authProvider()));
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilter() {
		System.out.println("ConfigSecurity : get-authenticationTokenFilter");
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new JwtSucessHandler());
		return filter;
	}
	@Bean
	public UserNamePwdAuthFilter authenticationFilter() throws Exception {
		UserNamePwdAuthFilter filter = new UserNamePwdAuthFilter();
	    filter.setAuthenticationManager(authenticationManager());
	  
	    return filter;
	}
	@Bean
	public AuthenticationProvider authProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setUserDetailsService(userDetailService);
	    provider.setPasswordEncoder(passwordEncoder());
	    return provider;
	}
	/*
	 * @Bean public HeaderAuthenticationFilter authenticationHeaderFilter() {
	 * System.out.println("ConfigSecurity : get-HeaderAuthenticationFilter");
	 * HeaderAuthenticationFilter filter = new HeaderAuthenticationFilter();
	 * filter.setAuthenticationManager(authenticationManager());
	 * filter.setAuthenticationSuccessHandler(new JwtSucessHandler()); return
	 * filter; }
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ConfigSecurity : configure");
		http.csrf().disable().authorizeRequests().antMatchers("/token/**")
		.authenticated()
		.antMatchers("/v1/**")
		.authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
		http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();
		super.configure(http);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailService);
	
		super.configure(auth);
	}

	   @Bean
        public PasswordEncoder passwordEncoder() {
		   return new BCryptPasswordEncoder();
	}
}
