package com.decathlon.usecase.service;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.decathlon.usecase.dto.MessageDto;
import com.decathlon.usecase.dto.MessageResponseDto;

@FeignClient(name="currencyService",url = "https://google.com/")
public interface MessageService {

	@PostMapping("")
	public MessageResponseDto sendSms(URI baseUri,@Valid @RequestBody MessageDto message);
}
