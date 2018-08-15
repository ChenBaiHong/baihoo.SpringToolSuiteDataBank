package com.baihoo.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 後臺管理員控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admins")
public class AdminController {
	
	
	/**
	 * 獲取後臺管理主頁面
	 * @param model
	 * @return
	 */
	public ModelAndView listUsers(Model model) {
		
		return new ModelAndView("admins/index" , "menuList" , model);
	}
}
