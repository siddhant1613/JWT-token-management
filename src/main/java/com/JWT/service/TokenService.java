package com.JWT.service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JWT.model.JwtToken;
import com.JWT.model.TokenRequestBody;
import com.JWT.util.ApplicationConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenService {
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	String secretKey = "plkmncbgsdggddgh";
	
	public JwtToken generateToken(TokenRequestBody body) {
		String jwtToken = Jwts.builder()
				.claim("accountNumber",body.getAccountNumber())
				.claim("accountType", body.getAccountType())
				.claim("userType", body.getUserType())
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from((Instant.now().plus(10,ChronoUnit.MINUTES))))
				.compact();
			    
     JwtToken jwtTokenObject = new JwtToken();
     jwtTokenObject.setJwtToken(jwtToken);
     jwtTokenObject.setAccountType(body.getAccountType());
     
     return jwtTokenObject;
		
	}
	
	
	public JwtToken generateTokenUsingSecret(TokenRequestBody body) {
		String jwtToken = Jwts.builder()
				.claim("accountNumber",body.getAccountNumber())
				.claim("accountType", body.getAccountType())
				.claim("userType", body.getUserType())
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from((Instant.now().plus(10,ChronoUnit.MINUTES))))
				.signWith(SignatureAlgorithm.HS256, applicationConfig.getSecretKey())
				.compact();
			    
     JwtToken jwtTokenObject = new JwtToken();
     jwtTokenObject.setJwtToken(jwtToken);
     jwtTokenObject.setAccountType(body.getAccountType());
     
     return jwtTokenObject;
		
	}
	
	public JwtToken generateTokenUsingPrivateKey(TokenRequestBody body) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String jwtToken = Jwts.builder()
				.claim("accountNumber",body.getAccountNumber())
				.claim("accountType", body.getAccountType())
				.claim("userType", body.getUserType())
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from((Instant.now().plus(10,ChronoUnit.MINUTES))))
				.signWith(SignatureAlgorithm.RS256, getPrivateKey())                     //HS256
				.compact();
			    
     JwtToken jwtTokenObject = new JwtToken();
     jwtTokenObject.setJwtToken(jwtToken);
     jwtTokenObject.setAccountType(body.getAccountType());
     
     return jwtTokenObject;
		
	}
	
	


	public Claims parseJwt(String jwtToken) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
		// TODO Auto-generated method stub
		Claims jwt = Jwts.parser()
				.setSigningKey(getPublicKey())
				.parseClaimsJws(jwtToken)
				.getBody();
		return jwt;
	}
	
	private  PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
	    String rsaPrivateKey = applicationConfig.getPrivateKey();

	    rsaPrivateKey = rsaPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "");
	    rsaPrivateKey = rsaPrivateKey.replace("-----END PRIVATE KEY-----", "");
	    rsaPrivateKey = rsaPrivateKey.replace("\n", "");

	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    PrivateKey privKey = kf.generatePrivate(keySpec);
	    return privKey;
	}	
	private  PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
	    String rsaPublicKey = applicationConfig.getPublicKey();
	    rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
	    rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");
	    rsaPublicKey = rsaPublicKey.replace("\n", "");
	    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    PublicKey publicKey = kf.generatePublic(keySpec);
	    return publicKey;
	}
	
	

}
