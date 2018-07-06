package com.example.vaadin.vaadinspringboot;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

	public TransactionDTO getTrnasactionDTO(String txnId){
		TransactionDTO dto = new TransactionDTO();
		dto.setMsisdn("9810936551");
		dto.setPrice("Rs 200");
		dto.setProductName("Cheap thrills ringtone");
		return dto;
	}
	
	public boolean validateOTP(TransactionDTO dto){
		return true;
	}
}
