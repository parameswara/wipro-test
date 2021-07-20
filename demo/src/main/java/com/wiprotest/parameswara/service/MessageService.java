package com.wiprotest.parameswara.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import com.wiprotest.parameswara.entity.Message;

@Validated
public interface MessageService {

	public List<Message> findAll();

	public Message findById(String id);

	public void createMessages(@NonNull @Size(min = 1) @Valid List<Message> messageList);

	public void deletMessages();

	public void deletMessage(String id);

	public List<Message> findAllById(String id);

}
