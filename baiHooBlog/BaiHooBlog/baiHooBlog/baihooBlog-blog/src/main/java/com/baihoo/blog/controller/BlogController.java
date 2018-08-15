package com.baihoo.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 博客控制器 作用：起到前端搜索博客的功能
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {

	
	/**
	 * 
	 * @param order			博客排序，默認根據最新排序
	 * @param keyword		根據關鍵字過濾博客
	 * @return
	 */
	@GetMapping("/listBlogs")
	public String listBlogs(@RequestParam(value = "order", required = false, defaultValue = "new") String order,
			@RequestParam(value = "keyword", required = false) String keyword) {

		System.out.println("order:"+order+";keyword:"+keyword);
		
		return "redirect:/index?order="+order+"&keyword="+keyword;
	}
}
