package com.exercise.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.exercise.spring.dao.ITransaction1Dao;
import com.exercise.spring.dao.ITransaction2Dao;

@Service
public class BuyTicketService implements IBuyTicketService {

	@Autowired
	ITransaction1Dao dao1;
	
	@Autowired
	ITransaction2Dao dao2;
	
//	@Autowired
//	PlatformTransactionManager transactionManager;
//	
//	@Autowired
//	TransactionDefinition definition;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int buy(String consumerId, int amount, String error) {
		
//		TransactionStatus status = transactionManager.getTransaction(definition);
		
		try {
			transactionTemplate.execute( new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
				
					dao1.pay(consumerId, amount);
					
					//의도적 에러발생
					if (error.equals("1")) {int n = 10/0;}
					dao2.pay(consumerId, amount);
					
				}
			});
			// 기존 매니저 커밋 삭제
			return 1;			
		} catch (Exception e) {
			System.out.println("[PlatformTransactionManager] Rollback");
			// 기존 매니저 롤백 삭제
			return 0;		
		}
			
	}

}
