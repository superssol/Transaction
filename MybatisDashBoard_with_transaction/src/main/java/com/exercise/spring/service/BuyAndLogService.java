package com.exercise.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class BuyAndLogService {

	@Autowired
	BuyTicketService buyTicketService;
	
	@Autowired
	LogWriteService logWriteService;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	public int buy(String consumerId, int amount, String error) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					buyTicketService.buy(consumerId, amount, error);
					
					if (error.equals("2")) { int n = 10/0; }
					
					logWriteService.write(consumerId, amount);					
				}
			});
			
			return 1;
		} catch (Exception e) {
			System.out.println("[Transaction Propagarion] Rollback");
			return 0;
		}
	}
	
}
