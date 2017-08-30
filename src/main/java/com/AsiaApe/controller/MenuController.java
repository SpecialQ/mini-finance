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
import org.springframework.web.servlet.ModelAndView;

import com.AsiaApe.bo.MenuBo;
import com.AsiaApe.pojo.component.MenuTree;

@Controller
public class MenuController {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private MenuBo menuBo;
	
	/**
	 * 控制台工作区页面重定向
	 * 
	 * @param templateName 模板路径名
	 * @param selector 碎片模块
	 * @param selectNodeId 页面选择的对应树节点id
	 * @return 控制台页面模型
	 */
	@RequestMapping(value="redirect")
	public ModelAndView redirect(
			@RequestParam(value = "templateName", required = true) String templateName,
			@RequestParam(value = "selector", required = false) String selector,
			@RequestParam(value = "selectNodeId", required = false) String selectNodeId){
		logger.debug("MenuController.redirect to be invoke!");
		ModelAndView model = new ModelAndView();
		MenuTree authMenu = menuBo.getAuthorizationMenu(MenuTree.ROOT, selectNodeId);
		model.addObject("nodes", authMenu.getChildrenNode());
		model.addObject("menus", menuBo.toMenusArray(authMenu.getChildrenNode()));
		model.addObject("templateName", templateName);
		model.addObject("selector", selector);
		model.setViewName("console");
		return model;
	}
	
	/**
	 * 菜单查询
	 * 
	 * @param nodeName 查询的菜单名称，查询方式为模糊查询
	 * @return 相关菜单数json对象
	 */
	@ResponseBody
	@RequestMapping(value="getNodesByName", produces="application/json; charset=UTF-8", method=RequestMethod.GET)
	public String getNodesByName(
			@RequestParam(value = "nodeName", required = true) String nodeName){
		logger.debug("MenuController.getNodesByName to be invoke!");
		List<MenuTree> nodes = menuBo.searchAuthorizationNodes(nodeName);
		return menuBo.toData(nodes);
	}
	
}