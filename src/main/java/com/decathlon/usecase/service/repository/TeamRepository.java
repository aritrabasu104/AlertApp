package com.decathlon.usecase.service.repository;

import org.springframework.data.repository.CrudRepository;

import com.decathlon.usecase.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {

}
