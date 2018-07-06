package com.example.vaadin.vaadinspringboot;

import lombok.Data;

@Data
public class TransactionDTO {

	private String txnId;
	
	private String msisdn;
	
	private String productName;
	
	private String price;
	
	private String otp;
	
}
