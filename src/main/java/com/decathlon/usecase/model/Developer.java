package com.decathlon.usecase.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Developer {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank
	private String name;
	
	@NotBlank
	@Column(unique=true)
	@Pattern(regexp="(^$|[0-9]{10})")
	private String phoneNumber;
	
	@OneToOne
	private Team team;
}
