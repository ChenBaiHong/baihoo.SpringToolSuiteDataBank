package com.baihoo.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baihoo.blog.domain.Authority;
import com.baihoo.blog.domain.User;
import com.baihoo.blog.service.AuthorityService;
import com.baihoo.blog.service.UserService;


/**
 * 主頁控制器
 * @author Administrator
 *
 */
@Controller
public class MainController {
	/**
	 * 当注册用户时默认的权限
	 */
	private static final Long ROLE_USER_AUTHORITY_ID = 2L;
	
	@Autowired
	@Qualifier("authorityService")
	private AuthorityService authorityService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
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
			model.addAttribute("loginError", true);
			model.addAttribute("errorMessage" ,"登陆失败，账号或密码错误！");
		}else {
			model.addAttribute("errorMessage" ,exception.getMessage());
		}
		return "login";
	}
	/**
	 * 跳转注册页面
	 * @return
	 */
	@GetMapping("/register")
	public String register() {
		
		return "register";
	}
	/**
	 * 用户注册
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/register")
	public String registerUser(@Valid User user) throws Exception {
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authorityService.findById(ROLE_USER_AUTHORITY_ID));
		user.setAuthorities(authorities);
		user.setPasswordEncoder(user.getPassword());
		userService.registerUser(user);
		return "redirect:/login";
	}
	/**
	 * 搜索
	 * @return
	 */
	@GetMapping("/search")
	public String search() {
		return "search";
	}
}
