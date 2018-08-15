package com.baihoo.jpa.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baihoo.jpa.domain.User;
import com.baihoo.jpa.repository.UserRepository;

/**
 * 用户控制层
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	/**
	 * 页面获取用户列表
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(Model model) {
		model.addAttribute("userList", userRepository.findAll());
		model.addAttribute("title", "用户管理");
		return new ModelAndView("users/list", "userModel", model);
	}

	/**
	 * 根据id查询用户并页面展示
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/view/{id}")
	public ModelAndView view(@PathVariable("id") Long id, Model model) {
		Optional<User>  opUser = userRepository.findById(id);
		model.addAttribute("user", opUser.get());
		model.addAttribute("title", "查看用户");
		return new ModelAndView("users/view", "userModel", model);
	}

	/**
	 * 获取创建表单页面
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/form")
	public ModelAndView createForm(Model model) {

		model.addAttribute("user", new User(null , null , null));
		model.addAttribute("title", "创建用户");
		return new ModelAndView("users/form", "userModel", model);
	}
	/**
	 * 保存用戶
	 * @param user
	 * @return
	 */
	@PostMapping("/submit")
	public ModelAndView saveOrUpdateUser(User user) {
		user = userRepository.save(user);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:list");// 重定向至list映射方法
		return mav;
	}
	/**
	 * 根据id查詢用戶帶參并跳轉到form頁面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/modify/{id}")
	public ModelAndView modifyUser(@PathVariable("id") Long id, Model model) {
		Optional<User>  opUser = userRepository.findById(id);
		model.addAttribute("user", opUser.get());
		model.addAttribute("title", "修改用户");
		return new ModelAndView("users/form", "userModel", model);
	}
	/**
	 * 根据id刪除用戶
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public ModelAndView deleteUser(@PathVariable("id") Long id, Model model) {
		userRepository.deleteById(id);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/user/list");// 重定向至list映射方法
		return mav;
	}
}
