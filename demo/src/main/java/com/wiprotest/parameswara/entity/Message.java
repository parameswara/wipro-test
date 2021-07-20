package com.wiprotest.parameswara.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message{

	public static final String COLLECTION_NAME = "message";

	@ApiModelProperty(notes = "The unique id of the message")
	@Id
	private String id;
	@ApiModelProperty(notes = "The unique id of the message batch")
	private String batchId;
	@ApiModelProperty(notes = "The message's name")
	@NotEmpty(message = "Name cannot be empty.")
	private String name;
	@ApiModelProperty(notes = "The year in 4 digits")
	@Min(1000)
	@Max(9999)
	private int year;
	@ApiModelProperty(notes = "The count from 0 to 999")
	@Min(0)
	@Max(999)
	private int count;
	@ApiModelProperty(notes = "The requestId from kafka request")
	private String requestId;

}
