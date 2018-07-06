package com.example.vaadin.vaadinspringboot;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

	public TransactionDTO getTrnasactionDTO(String txnId){
		TransactionDTO dto = new TransactionDTO();
		if("123123".equalsIgnoreCase(txnId)){
			dto.setMsisdn("9810936551");
			dto.setPrice("Rs 200");
			dto.setProductName("Cheap thrills ringtone");
		}else{
			dto.setMsisdn("11111111");
			dto.setPrice("Rs 111");
			dto.setProductName("Costly thrills");
		}
		return dto;
	}
	
	public boolean validateOTP(TransactionDTO dto){
		return true;
	}
}
