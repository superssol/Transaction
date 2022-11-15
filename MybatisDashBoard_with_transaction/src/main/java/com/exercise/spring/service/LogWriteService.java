package com.exercise.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.spring.dao.ITransaction3Dao;

@Service
public class LogWriteService implements ILogWriteService {

	@Autowired
	ITransaction3Dao dao3;
	
	@Override
	public int write(String customerId, int amount) {
		
		try {
			dao3.pay(customerId, amount);
			return 1;
		} catch (Exception e) {
			return 0;		
		}
		
	}

}
