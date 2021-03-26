package com.decathlon.usecase.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class DeveloperDto {

	@NotBlank
	private String name;
	
	@NotBlank
	@Pattern(regexp="(^$|[0-9]{10})",message = "Phone number hould have 10 numbers")
	private String phoneNumber;
	
}
