package com.AsiaApe.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.AsiaApe.bo.UserBo;
import com.AsiaApe.pojo.security.User;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserBo userBo;
	
	/**
	 * 获取所有用户信息
	 * 
	 * @return 用户数据集的json对象
	 */
	@ResponseBody
	@RequestMapping(value="getAllUser", produces="application/json; charset=UTF-8", method=RequestMethod.GET)
	public String getAllUser(){
		logger.debug("UserController.getAllUser to be invoke!");
		List<User> users = userBo.getAllUser();
		return userBo.toData(users);
	}
	
	@ResponseBody
	@RequestMapping(value="addUser", produces="application/json; charset=UTF-8", method=RequestMethod.POST)
	public String addUser(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "alias", required = false) String alias,
			@RequestParam(value = "sex", required = false) String sex){
		logger.debug("UserController.addUser to be invoke!");
		
		logger.debug("username is {},password is {},phone is {},email is {},address is {},alias is {},sex is {}",username,password,phone,email,address,alias,sex);
		return "test code";
	}
}
