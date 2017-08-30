package com.AsiaApe.bo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.AsiaApe.dao.MenuDao;
import com.AsiaApe.pojo.component.MenuTree;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MenuBo {

	private static final Logger logger = LoggerFactory.getLogger(MenuBo.class);

	@Autowired
	private MenuDao menuDao;
	
	/**
	 * 获取当前用户所拥有的菜单对象
	 * 
	 * @param nodeId 指定某节点ID，获取该节点下的菜单树
	 * @param selectNodeId 选择节点，将该ID对应的节点树选择状态设为true
	 * @return 
	 */
	public MenuTree getAuthorizationMenu(String nodeId, String selectNodeId) {
		logger.debug("MenuBo.getAuthorizationMenu to be invoke!");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		logger.debug("My name is :{}", userDetails.getUsername());
		List<MenuTree> menuList = menuDao.getAuthorizationMenu(userDetails.getUsername());
		if (selectNodeId != null) {
			for (MenuTree menuTree : menuList) {
				if (menuTree.compareId(selectNodeId)) {
					menuTree.getState().setSelected(true);
					break;
				}
			}
		}
		for (MenuTree menuTree : menuList) {
			if (menuTree.compareId(nodeId)) {
				setMenuChildNode(menuTree, menuList);
				return menuTree;
			}
		}
		return new MenuTree(nodeId);
	}
	
	/**
	 * 查询关键字对应的各相应菜单
	 * 
	 * @param nodeName 菜单关键字
	 * @return 各相应菜单集
	 */
	public List<MenuTree> searchAuthorizationNodes(String nodeName){
		logger.debug("MenuBo.getAuthorizationNode to be invoke!");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		logger.debug("My name is :{}", userDetails.getUsername());
		String searchInfo = "%"+nodeName+"%";
		List<MenuTree> menuList = menuDao.searchAuthorizationNodes(userDetails.getUsername(), searchInfo);
		return menuList;
	}
	
	/**
	 * 递归函数。
	 * 查找原菜单集合，查找目标菜单的子菜单，并设置。
	 * 
	 * @param targetNode 目标菜单
	 * @param sourceMenu 原菜单集
	 */
	private void setMenuChildNode(MenuTree targetNode, List<MenuTree> sourceMenu) {
		MenuTree souceNode = null;
		for (int i = 0; i < sourceMenu.size(); i++) {
			souceNode = sourceMenu.get(i);
			if (targetNode.compareId(souceNode.getParentId())) {
				targetNode.addChildNode(souceNode);
				setMenuChildNode(souceNode, sourceMenu);
			}
		}
	}
	
	/**
	 * 转换菜单为json对象
	 * 
	 * @param menuTrees 菜单数据对象集
	 * @return json数据对象
	 */
	public String toData(List<MenuTree> menuTrees) {
		ObjectMapper mapper = new ObjectMapper();
		String data = null;
		try {
			data = mapper.writeValueAsString(menuTrees);
		} catch (JsonProcessingException e) {
			logger.error("数据转换异常：{}", e.getMessage());
			e.printStackTrace();
		}
		logger.debug("菜单数据转换结果为：{}", data);
		return data;
	}
	
	/**
	 * 转换菜单树List为json数组，各节点一一转换保存，
	 * 此方法用于控制台页面的各根菜单及其对应菜单树展示
	 * 
	 * @param menuTrees 菜单数据对象集
	 * @return json数据对象
	 */
	public String[] toMenusArray(List<MenuTree> menuTrees) {
		ObjectMapper mapper = new ObjectMapper();
		String[] nodes = null;
		if (menuTrees != null && menuTrees.size() > 0) {
			nodes = new String[menuTrees.size()];
			for (int i = 0; i < menuTrees.size(); i++) {
				try {
					nodes[i] = mapper.writeValueAsString(menuTrees.get(i).getChildrenNode());
				} catch (JsonProcessingException e) {
					logger.error("数据转换异常：{}", e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return nodes;
	}
	
	/**
	 * 转换菜单为json对象（该方法暂未被使用）
	 * 
	 * @param menuTrees 菜单数据对象
	 * @return json数据对象
	 */
	public String toData(MenuTree... menuTrees ) {
		List<MenuTree> menuList = new ArrayList<MenuTree>();
		for (MenuTree menuTree : menuTrees) {
			menuList.add(menuTree);
		}
		return toData(menuList);
	}
}
