package com.AsiaApe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.AsiaApe.pojo.component.MenuTree;

public interface MenuDao {
	public List<MenuTree> getAuthorizationMenu(String username);
	
	public List<MenuTree> getAuthorizationNode(@Param("username") String username, @Param("nodeId") String nodeId);
	
	public List<MenuTree> searchAuthorizationNodes(@Param("username") String username, @Param("nodeName") String nodeName);
	
}
