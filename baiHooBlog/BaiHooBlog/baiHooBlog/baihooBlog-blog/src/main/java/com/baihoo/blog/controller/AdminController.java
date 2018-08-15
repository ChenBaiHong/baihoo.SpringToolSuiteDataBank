package com.baihoo.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baihoo.blog.vo.Menu;

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
	@GetMapping
	public ModelAndView admins(Model model) {
		List<Menu> list = new ArrayList<>();
		list.add(new Menu("用户管理", "/users"));
		list.add(new Menu("角色管理", "/roles"));
		list.add(new Menu("博客管理", "/blogs"));
		list.add(new Menu("评论管理", "/commits"));
		model.addAttribute("list", list);
		return new ModelAndView("admins/index" , "model" , model);
	}
}
