package com.AsiaApe.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AsiaApe.dao.UserDao;
import com.AsiaApe.pojo.security.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserBo {
	private static final Logger logger = LoggerFactory.getLogger(UserBo.class);
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 获取所有用户信息
	 */
	public List<User> getAllUser() {
		logger.debug("UserBo.getAuthorizationMenu to be invoke!");
		List<User> userLIst = userDao.getAllUser();
		System.out.println("to Data:" + userLIst.toString());
		return userLIst;
	}
	
	/**
	 * 转换用户信息为json对象
	 * @param users 用户信息列表
	 * @return json数据对象
	 */
	public String toData(List<User> users) {
		ObjectMapper mapper = new ObjectMapper();
		String data = null;
		try {
			data = mapper.writeValueAsString(users);
		} catch (JsonProcessingException e) {
			logger.error("数据转换异常：{}", e.getMessage());
			e.printStackTrace();
		}
		logger.debug("表格数据转换结果为：{}", data);
		return data;
	}
}
