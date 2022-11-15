package com.exercise.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exercise.spring.service.IBuyTicketService;


@Controller
public class MyController {
	
	@Autowired
	private IBuyTicketService Bservice;
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception {
		return "Transaction Manager (2)";
	}
	
	@RequestMapping("/buy_ticket")
	public String buy_ticket(Model model) {
		
		return "buy_ticket";
	}
	
	@RequestMapping("/buy_ticket_card")
	public String view(@RequestParam("consumerId") String consumerId,
			@RequestParam("amount") String amount,
			@RequestParam("error") String error,
			Model model
			) 
	{
		
		int nResult = Bservice.buy(consumerId, Integer.parseInt(amount), error);
		
		model.addAttribute("consumerId", consumerId);
		model.addAttribute("amount", amount);
		if (nResult == 1) {
			return "buy_ticket_end";
		}
		else {
			return "buy_ticket_error";
		}
	}
	
}
