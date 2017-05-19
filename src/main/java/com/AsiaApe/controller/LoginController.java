package com.AsiaApe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		logger.debug("LoginController.login to be invoke!");
		 ModelAndView model = new ModelAndView();
		 if (error != null) {
			 logger.debug("error message :" + error);
			 model.addObject("error", "Invalid username and password!");
		 }
		 if (logout != null) {
			 logger.debug("logout message :" + logout);
			 model.addObject("msg", "You've been logged out successfully.");
		 }
		 model.setViewName("login");
		return model;
	}

}
