package com.wiprotest.parameswara.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wiprotest.parameswara.entity.Message;

public interface MessageRepository extends CrudRepository<Message, String>{
	@Query("{batchId :?0}") //SQL Equivalent : SELECT * FROM MESSAGE WHERE BATCHID=?
	List<Message> getMessagesByBatchId(String batchId);
	
}
