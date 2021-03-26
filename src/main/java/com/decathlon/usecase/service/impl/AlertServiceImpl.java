package com.decathlon.usecase.service.impl;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.decathlon.usecase.dto.MessageDto;
import com.decathlon.usecase.error.custom.SmsServiceCommunicationException;
import com.decathlon.usecase.error.custom.TeamNotFoundException;
import com.decathlon.usecase.model.Developer;
import com.decathlon.usecase.model.Team;
import com.decathlon.usecase.service.AlertService;
import com.decathlon.usecase.service.MessageService;
import com.decathlon.usecase.service.repository.DeveloperRepository;
import com.decathlon.usecase.service.repository.TeamRepository;

import feign.FeignException;

@Service
public class AlertServiceImpl implements AlertService {

	@Value("${alert.api.uri}")
	private String ALERT_URI;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private DeveloperRepository developerRepository;

	@Autowired
	private MessageService messageService;

	@Override
	public Team createTeam(Team team) {
		Team teamWithId = teamRepository.save(team);
		team.getDevelopers().forEach(developer -> developer.setTeam(teamWithId));
		developerRepository.saveAll(team.getDevelopers());
		return team;
	}

	@Override
	public void alertDeveloper(Long teamId) {
		Optional<Team> teamOpt = teamRepository.findById(teamId);
		if (teamOpt.isEmpty())
			throw new TeamNotFoundException(teamId);
		List<Developer> developers = teamOpt.get().getDevelopers();
		Random rand = new Random();
		Developer chosenDev = developers.get(rand.nextInt(developers.size()));
		try {
			messageService.sendSms(URI.create(ALERT_URI), new MessageDto(chosenDev.getPhoneNumber()));
		} catch (FeignException e) {
			throw new SmsServiceCommunicationException(e.getMessage());
		}
	}

}
