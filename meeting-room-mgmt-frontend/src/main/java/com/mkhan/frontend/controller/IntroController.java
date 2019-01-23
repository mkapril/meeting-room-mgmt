package com.mkhan.frontend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IntroController {

	@RequestMapping(path="/")
	public String test(Model model) {
		
		return "redirect:/reservation/reservationForm";	
	}
	
}
