package com.JWT.controller;

import java.net.http.HttpHeaders;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.JWT.model.JwtToken;
import com.JWT.model.TokenRequestBody;
import com.JWT.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestController
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/generate/jwt/token")
	public ResponseEntity<JwtToken> generateJwtToken(@RequestBody TokenRequestBody body,@RequestHeader(value="apiversion", required=true) String apiVersion,
			@RequestHeader(value="accountType", required=true) String accountType,@RequestHeader(value="messageId", required=true) String messageIds) throws NoSuchAlgorithmException, InvalidKeySpecException {
		tokenService.generateTokenUsingPrivateKey(body);
	//	HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(tokenService.generateTokenUsingPrivateKey(body),HttpStatus.OK);
	}
	
	@PostMapping("/validate/jwt/token")
	public Claims jwtTokenValidate(@RequestHeader(value="token", required=true) String jwtToken) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		return tokenService.parseJwt(jwtToken);
	}

}
