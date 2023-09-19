package com.JWT.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix="app")
@Data
@Getter
@Setter
public class ApplicationConfig {
private String secretKey;
	
	private String privateKey;
	
	private String publicKey;

}
