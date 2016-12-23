package com.jdale.service;

import com.jdale.model.JwtModel;

public interface JwtService {

	public String generateJWT(String id, String issuer, String subject, long timeToLiveMillis, String secretSigningKey);
	public void validateJWT(String jwt, String issuer, String subject, String secretSigningKey);
	public JwtModel getJwtModel();

}
