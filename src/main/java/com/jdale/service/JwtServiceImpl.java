package com.jdale.service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jdale.model.JwtModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService{

	@Autowired 
	JwtModel jwtModel; 

	/*
	 * This method is used to generate a new JWT
	 */
	public String generateJWT(String id, String issuer, String subject, long timeToLiveMillis, String secretSigningKey) {

	    //JWT signature algorithm
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	    //Get the current time
	    Date now = Date.from(Instant.now());

	    //Use a secret key to sign the JWT	    
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretSigningKey);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	    //Set the JWT claims
	    JwtBuilder builder = Jwts.builder();
	    builder.setId(id);
	    builder.setIssuedAt(now);
	    builder.setSubject(subject);
	    builder.setIssuer(issuer);
	    builder.signWith(signatureAlgorithm, signingKey);

	    //Set the JWT expiration
	    long expirationTime = now.getTime() + timeToLiveMillis;
	    Date expirationDate = new Date(expirationTime);	    
	    builder.setExpiration(expirationDate);
	    
	    //Build the JWT 
	    String jwt = builder.compact();
	    //Store the JWT in the session scoped model
	    this.jwtModel.setJwt(jwt);
	    this.jwtModel.setValidationMessage("");
	    return jwt;
	}
	
	/*
	 * This method is used to validate the JWT
	 */
	public void validateJWT(String jwt, String issuer, String subject, String secretSigningKey){	 
		String message = "JWT is Valid!";
	    try{
	    	//Attempt to parse the JWT using the secret key
		    Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretSigningKey)).parseClaimsJws(jwt).getBody();
		    //String jwtId = claims.getId();
		    String jwtSubject = claims.getSubject();
		    String jwtIssuer = claims.getIssuer();
		    Date jwtExpiration = claims.getExpiration();
		    Date now = Date.from(Instant.now());
		    //Check the expiration date
		    if(jwtExpiration.before(now)){
		    	throw new Exception("Invalid JWT. It has expired.");
		    }
		    //Check the Issuer
		    if(!(jwtIssuer != null && issuer != null && jwtIssuer.equals(issuer))){
		    	throw new Exception("Invalid JWT. The Issuers do not match.");
		    }
		    //Check the Subject
		    if(!(jwtSubject != null && subject != null && jwtSubject.equals(subject))){
		    	throw new Exception("Invalid JWT. The Subjects do not match.");
		    }
	    }catch (Exception e){
	    	message = e.getMessage();
	    }catch (Throwable t){
	    	message = t.getMessage();
	    }
	    this.jwtModel.setValidationMessage(message);
	}
	
	//Getter method for the JwtModel
	public JwtModel getJwtModel(){
		return this.jwtModel;
	}
}
