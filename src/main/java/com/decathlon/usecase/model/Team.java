package com.decathlon.usecase.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Team {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank
	private String name;
	
	@OneToMany(mappedBy = "team")
	private List<Developer> developers;
}
