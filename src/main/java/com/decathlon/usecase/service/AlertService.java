package com.decathlon.usecase.service;

import com.decathlon.usecase.model.Team;

public interface AlertService {

	Team createTeam(Team team);
	
	void alertDeveloper(Long teamId);
}
