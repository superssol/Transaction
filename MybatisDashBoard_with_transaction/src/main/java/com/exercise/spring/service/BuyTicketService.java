package com.exercise.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.exercise.spring.dao.ITransaction1Dao;
import com.exercise.spring.dao.ITransaction2Dao;

@Service
public class BuyTicketService implements IBuyTicketService {

	@Autowired
	ITransaction1Dao dao1;
	
	@Autowired
	ITransaction2Dao dao2;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	TransactionDefinition definition;
	
	@Override
	public int buy(String consumerId, int amount, String error) {
		
		TransactionStatus status = transactionManager.getTransaction(definition);
		
		try {
			dao1.pay(consumerId, amount);
			
			//의도적 에러발생
			if (error.equals("1")) {int n = 10/0;}
			dao2.pay(consumerId, amount);
			
			transactionManager.commit(status);
			return 1;
		} catch (Exception e) {
			System.out.println("[PlatformTransactionManager] Rollback");
			transactionManager.rollback(status);
			return 0;
		}
		
	}

}
