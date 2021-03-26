package com.decathlon.usecase.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class TeamRequestDto {
	
	@NotBlank
	private String name;
	
	
	@NotEmpty(message = "Team should have atleast one developer")
	@Valid
	private List<DeveloperDto> developers;

}
