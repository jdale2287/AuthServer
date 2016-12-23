package com.jdale.controller;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.jdale.service.JwtService;

@Controller
public class JwtController {
	
	private Logger logger = Logger.getLogger(JwtController.class); 
	@Value("${jwt.secret.key}")
    private String jwtSecretKey;
	@Value("${jwt.header.name}")
	private String jwtHeaderName;	 
	@Autowired 
	JwtService jwtService; 
	
	@RequestMapping(value = "/token/generate", method = RequestMethod.GET)
	public String generateJwt(Model model, HttpServletRequest request, HttpServletResponse response){		
		String result = "secured";
		//Generate the JWT using the Spring Authentication information
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			//Generate the JWT
			String username = auth.getName();
        	String id = UUID.randomUUID().toString();
        	long timetoLive = 60000L;
        	//Use the username from the authenticated user to generate the token so that it can be validated later
        	String jwt = jwtService.generateJWT(id, "issuer", username, timetoLive, this.jwtSecretKey); 
        	logger.info("################## Generated JWT: " + jwt);
            //Add the JWT to the HTTP header
        	response.addHeader(this.jwtHeaderName, jwt);
			//Add the JWT to the Spring view model so it can be displayed on the page
        	model.addAttribute("jwtModel", jwtService.getJwtModel());
		}
		return result;
	}
	
	@RequestMapping(value = "/token/validate", method = RequestMethod.POST)
	public String validateJwt(Model model, HttpServletRequest request, HttpServletResponse response){
		String result = "secured";
		//Validate the JWT using the Spring Authentication information
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			//Validate the JWT that exists in the model
			String username = auth.getName();			
			String jwt = jwtService.getJwtModel().getJwt();
        	//Use the username from the authenticated user to validate the token
			String message = "JWT is Valid!";			
			jwtService.validateJWT(jwt, "issuer", username, this.jwtSecretKey);			    
			logger.info("################## Done validating JWT: " + message);
			//Add the JWT to the Spring view model so it can be displayed on the page
        	model.addAttribute("jwtModel", jwtService.getJwtModel());
		}
		return result;
	}
}
