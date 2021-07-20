package com.wiprotest.parameswara.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response {
	private String status;
	private int errorCode;
	private String errorMessage;
	private String batchId;
	
}
