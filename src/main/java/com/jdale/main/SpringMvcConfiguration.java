package com.jdale.main;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.jdale.controller.JwtController;
import com.jdale.model.JwtModel;
import com.jdale.service.JwtServiceImpl;

@Configuration
@ComponentScan(basePackageClasses = {JwtController.class, JwtServiceImpl.class, JwtModel.class})
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    //This method is used to configure the Spring MVC view controller that processes requests
    //from the underlying Dispatcher Servlet that handles all incoming HTTP requests
    public void addViewControllers(ViewControllerRegistry registry) {        
    	registry.addViewController("/").setViewName("index");
    	registry.addViewController("/unsecured").setViewName("unsecured");	// map unsecured requests to unsecured.html
        registry.addViewController("/secured").setViewName("secured");	// map secured requests to secured.html
        registry.addViewController("/login").setViewName("login");	// map login requests to login.html
    }
    
    /*
     * These methods are used to redirect all HTTP requests to HTTPS
     */
    @Bean 
    public EmbeddedServletContainerFactory servletContainer() { 
    	TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() { 
			@Override 
			protected void postProcessContext(Context context) { 
				SecurityConstraint securityConstraint = new SecurityConstraint(); 
				securityConstraint.setUserConstraint("CONFIDENTIAL"); 
				SecurityCollection collection = new SecurityCollection(); 
				collection.addPattern("/*");
				securityConstraint.addCollection(collection); 
				context.addConstraint(securityConstraint); 
			} 
    	};          
	    tomcat.addAdditionalTomcatConnectors(initiateHttpConnector()); 
	    return tomcat; 
    } 
        
    private Connector initiateHttpConnector() { 
    	Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol"); 
    	connector.setScheme("http"); 
    	connector.setPort(8080); 
    	connector.setSecure(false); 
    	connector.setRedirectPort(8443);      
    	return connector; 
   }
}
