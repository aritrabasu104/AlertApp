package com.decathlon.usecase.service.repository;

import org.springframework.data.repository.CrudRepository;

import com.decathlon.usecase.model.Developer;

public interface DeveloperRepository extends CrudRepository<Developer, Long> {

}
