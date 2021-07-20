package com.wiprotest.parameswara.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wiprotest.parameswara.entity.Message;
import com.wiprotest.parameswara.repository.MessageRepository;

/**
 * Message Post, Get and Delete operations are implemented 
 * Post request processed through Kafka producer and consumer
 * @author Parameswara
 *
 */
@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	KafkaTemplate<String, String> template;

	@Override
	public List<Message> findAll() {
		return (List<Message>) messageRepository.findAll();
	}

	@Override
	public Message findById(String id) {
		Optional<Message> message = messageRepository.findById(id);
		return message.get();
	}

	@Override
	public void createMessages(List<Message> messageList) {
		Gson gson = new Gson();
		String messagesStr = gson.toJson(messageList, new TypeToken<ArrayList<Message>>() {
		}.getType());
		template.send("demo", messagesStr);
	}

	@Override
	public void deletMessages() {
		messageRepository.deleteAll();
	};

	@Override
	public void deletMessage(String id) {
		messageRepository.deleteById(id);
		;
	}

	@Override
	public List<Message> findAllById(String batchId) {
		List<Message> messages = messageRepository.getMessagesByBatchId(batchId);
		return messages;
	};

	@KafkaListener(id = "myId", topics = "demo")
	public void listen(String in) {
		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(in);
		List<Message> messages = new ArrayList<Message>();
		jsonArray.forEach(message -> {
			Gson gson = new Gson();
			Message parseMessage = gson.fromJson(message, Message.class);
			LocalDate date = LocalDate.now();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			parseMessage.setRequestId(new StringBuffer(timestamp.toString()).append("-")
					.append(date.getDayOfWeek().toString()).toString());
			messages.add(parseMessage);
		});
		List<Message> resMessages = (List<Message>) messageRepository.saveAll(messages);
	}

}
