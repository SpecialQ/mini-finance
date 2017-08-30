package com.AsiaApe.dao;

import java.util.List;

import com.AsiaApe.pojo.security.Authorization;

public interface AuthorizationDao {
	/**
	 * 获取所有授权信息列表
	 */
	public List<Authorization> getAllAuthorizations();

}
