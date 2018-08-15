package com.baihoo.bootstrap.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baihoo.bootstrap.repository.UserRepository;

/**
 * 主頁控制器
 * @author Administrator
 *
 */
@Controller
public class MainController {
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	/**
	 * 根目錄控制
	 * @return
	 */
	@GetMapping("/")
	public String root() {
		return "redirect:/index";
	}
	/**
	 * 網站首頁
	 * @return
	 */
	@GetMapping("/index")
	public String index() {
		return "index"; //index.html
	}
	/**
	 * 登陸界面
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login"; //login.html
	}
	/**
	 * 403錯誤界面
	 * @return
	 */
	@GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
	/**
	 * 登陸錯誤，返回登陸頁面，并添加錯誤信息
	 * @param model
	 * @return
	 */
	@GetMapping("/login-error")
	public String loginError(Model model , HttpSession session , @RequestParam(value = "secError", required = true) Boolean secError) {
		model.addAttribute("loginError" , true);
		//獲取其service層獲取登陸用戶抛出的異常信息
		Exception exception = (Exception)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if("Bad credentials".equals(exception.getMessage())) {
			model.addAttribute("errorMessage" ,"登陆失败，账号或密码错误！");
		}else {
			model.addAttribute("errorMessage" ,exception.getMessage());
		}
		return "login";
	}
}
