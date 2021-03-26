package com.decathlon.usecase.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class MessageDto {

	@JsonProperty("phone_number")
	@NotBlank
	private String phoneNumber;
	
}
