package com.decathlon.usecase.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.decathlon.usecase.dto.TeamRequestDto;
import com.decathlon.usecase.dto.TeamResponseDto;
import com.decathlon.usecase.model.Team;
import com.decathlon.usecase.service.AlertService;


@RestController
public class AlertController {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlertService alertService;
	
	
	@PostMapping("/team")
	public ResponseEntity<TeamResponseDto> register(@Valid @RequestBody TeamRequestDto teamRequestDto){
		Team team = modelMapper.map(teamRequestDto, Team.class);
		return ResponseEntity.ok(modelMapper.map(alertService.createTeam(team), TeamResponseDto.class));
	}
	
	@PostMapping("/{id}/alert")
	public ResponseEntity<?> alert(@Valid @PathVariable(name = "id") Long teamId){
		alertService.alertDeveloper(teamId);
		return ResponseEntity.ok().build();
	}
		
}
