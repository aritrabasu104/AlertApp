package com.decathlon.usecase.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.decathlon.usecase.model.Developer;
import com.decathlon.usecase.model.Team;
import com.decathlon.usecase.service.impl.AlertServiceImpl;
import com.decathlon.usecase.service.repository.DeveloperRepository;
import com.decathlon.usecase.service.repository.TeamRepository;

@SpringBootTest
public class AlertServiceImplTests {
	
	@Mock
	private DeveloperRepository developerRepository;
	
	@Mock
	private TeamRepository teamRepository;
	
	@InjectMocks
	private AlertServiceImpl alertServiceImpl;
	
	@Captor
	ArgumentCaptor<Team> acTeam;
	
	@Captor
	ArgumentCaptor<List<Developer>> acDevelopers;

	private EasyRandom generator = new EasyRandom();
	
	@Test
	public void shouldCreateTeam() {
		Team team = generator.nextObject(Team.class);
		
		when(teamRepository.save(team)).thenReturn(team);
		Team resultTeam = alertServiceImpl.createTeam(team);
		assertEquals(team, resultTeam);
		
		verify(teamRepository,times(1)).save(acTeam.capture());
		assertEquals(team, acTeam.getValue());
	}
	
	@Test
	public void shouldCreateTeamWithDevelopers() {
		Team team = generator.nextObject(Team.class);
		
		when(teamRepository.save(team)).thenReturn(team);
		Team resultTeam = alertServiceImpl.createTeam(team);
		assertEquals(team.getDevelopers(), resultTeam.getDevelopers());
		
		verify(teamRepository,times(1)).save(acTeam.capture());
		verify(developerRepository,times(1)).saveAll(acDevelopers.capture());
		assertEquals(team, acTeam.getValue());
		assertEquals(team.getDevelopers(), acDevelopers.getValue());		
	}
	
	@Test
	public void shouldCreateTeamWithSameName() {
		Team team = generator.nextObject(Team.class);
		when(teamRepository.save(team)).thenReturn(team);
		Team resultTeam = alertServiceImpl.createTeam(team);
		
		assertEquals(team.getName(), resultTeam.getName());
		verify(teamRepository,times(1)).save(acTeam.capture());
		assertEquals(team, acTeam.getValue());
	}
	
	
}
