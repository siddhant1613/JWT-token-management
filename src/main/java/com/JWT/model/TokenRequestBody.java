package com.JWT.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class TokenRequestBody {

	private String accountNumber;
	private String accountType;
	private String userType;
	
	
}
