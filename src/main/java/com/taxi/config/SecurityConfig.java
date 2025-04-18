package com.taxi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/*
	 * @Bean PasswordEncoder passwordEncoder() { return
	 * PasswordEncoderFactories.createDelegatingPasswordEncoder(); }
	 */
	
	private CostumLogoutHandler  costumLogoutHandler;
	
	@Autowired
	public void setCostumLogoutHandler(CostumLogoutHandler costumLogoutHandler)
	{
		this.costumLogoutHandler=costumLogoutHandler;
	}
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(); // Use BCrypt hashing
	    }
	
	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.
		csrf(csrf->csrf.disable())
		
		.authorizeHttpRequests(authorize->authorize
				.requestMatchers("/admin/**").authenticated()
				.requestMatchers("/**").permitAll()
				)
		.formLogin(form->form
				.loginPage("/login")
				.permitAll()
				)
		.logout(logout->logout
				.addLogoutHandler(costumLogoutHandler)
				.logoutUrl("/dologout")
				);
		
		return httpSecurity.build();
	}

}
