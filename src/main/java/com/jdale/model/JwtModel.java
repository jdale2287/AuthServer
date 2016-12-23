package com.jdale.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component 
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
/*
 * This class is used to represent the model that stores data between 
 * requests in the session on the server (i.e. JWT and validation message)
 */
public class JwtModel implements Serializable{
	
	private static final long serialVersionUID = 8194694282671994894L;
	
	private String jwt;
	private String validationMessage;
	
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getValidationMessage() {
		return validationMessage;
	}
	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	
	
}
