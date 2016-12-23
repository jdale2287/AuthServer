package com.jdale.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
				
	@Override
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 * This method is used to configure the Spring Security settings for the entire application (i.e. protected resources, login page, CSRF protection)
	 */
    protected void configure(HttpSecurity http) throws Exception {
		http       		
            .authorizeRequests()
            	//Allows access to all non-secured resources
                .antMatchers("/", "/unsecured", "/login", "/logout").permitAll()
                //All other requests need to be authenticated
                .anyRequest().authenticated()
            .and().formLogin()
            	//Login page used for authentication
                .loginPage("/login")
                .defaultSuccessUrl("/secured")                
            .and().logout()
            	//Destroys authentication by deleting session cookie 
            	.invalidateHttpSession(true)
            	.clearAuthentication(true)
            	.logoutUrl("/logout")
            	.logoutSuccessUrl("/login?logout")
            	.deleteCookies("JSESSIONID")                    
            .and().csrf()
            	//Prevents errors that occur from CSRF filter chain
            	.ignoringAntMatchers("/login", "/logout")	
            .and().sessionManagement()
            	//Only allows one session per user
            	.maximumSessions(1)     
            	.expiredUrl("/login?expired")
            	.maxSessionsPreventsLogin(false)            
            .and()
            	//Allows Spring Security to create a session if necessary
            	.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }

    @Autowired
    /*
     * This method is used to configure the user credentials for Spring Security authentication
     */
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN");
    }
}
