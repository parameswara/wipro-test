package com.wiprotest.parameswara.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wiprotest.parameswara.entity.Message;
import com.wiprotest.parameswara.exception.InvalidRequestException;
import com.wiprotest.parameswara.exception.MessageNotfoundException;
import com.wiprotest.parameswara.model.Response;
import com.wiprotest.parameswara.service.MessageService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Validated
@RestController
@RequestMapping("/demo")
public class MessageController {
	@Autowired
	MessageService messageService;

	@GetMapping("/messages")
	@ApiOperation(value = "Fetch all the messages", notes = "All the messages should be listed", response = ResponseEntity.class)
	public ResponseEntity<List<Message>> getMessages() {
		List<Message> messageList = messageService.findAll();
		return new ResponseEntity<List<Message>>(messageList, HttpStatus.OK);
	}

	@ApiOperation(value = "Finds the messages by batchId", notes = "Provide a batchId to look up specific messages from the Messages", response = ResponseEntity.class)
	@GetMapping("/messages/{batchId}")
	public ResponseEntity<List<Message>> getMessages(
			@ApiParam(value = "batchId value for the Messages you need to retrieve", required = true) @PathVariable("batchId") String batchId) {
		List<Message> messages = messageService.findAllById(batchId);
		if (messages == null)
			throw new MessageNotfoundException();
		return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
	}

	@ApiOperation(value = "Finds the messages by id", notes = "Provide a id to look up specific message from the Messages", response = ResponseEntity.class)
	@GetMapping("/message")
	public ResponseEntity<Message> getMessage(
			@ApiParam(value = "id value for the Message you need to retrieve", required = true) @RequestParam(name = "id") String id) {
		Message message = messageService.findById(id);
		if (message == null)
			throw new MessageNotfoundException();
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

	@ApiOperation(value = "Post the messages in the array format", notes = "Provide a array of messages to create the messages with same batchId", response = Response.class)
	@PostMapping("/messages")
	public Response addMessage(@RequestBody @NotNull @Valid Map<String, List<Message>> messageMap) {
		List<Message> messageList = new ArrayList<>();
		messageList = messageMap.get("messages");
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString();
		Response response = null;
		try {
			if (messageList != null) {
				messageList.forEach(message -> {
					message.setBatchId(uuidStr);
				});
				messageService.createMessages(messageList);
				response = new Response("Success", 0, "", uuidStr);
			} else {
				response = new Response("Failed", 0, "424", uuidStr);
			}
		} catch (Exception e) {
			throw new InvalidRequestException(e);
		}
		return response;
	}

	@DeleteMapping("/messages")
	public ResponseEntity<?> deleteMessages() {

		try {
			messageService.deletMessages();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/messages/{id}")
	public ResponseEntity<Message> deleteMessage(String id) {
		Message message = messageService.findById(id);
		if (message == null)
			throw new MessageNotfoundException();
		messageService.deletMessage(id);
		return new ResponseEntity<Message>(message, HttpStatus.ACCEPTED);
	}
}
