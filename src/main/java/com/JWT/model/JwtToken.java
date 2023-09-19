package com.JWT.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class JwtToken {

	private String accountType;
	private String jwtToken;
}
