package com.hripple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class AppController {
	 @RequestMapping("/dashboard")  
	 public ModelAndView getHome() {  
	  String string = "Congrats ! You are done with your first Spring Security configuration !";  
	  return new ModelAndView("dashboard", "string", string);  
	 }  
	 
	 
}
