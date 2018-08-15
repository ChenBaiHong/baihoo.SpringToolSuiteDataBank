package com.baihoo.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用戶主頁控制器 作用
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/userspace")
public class UserspaceController {
	/**
	 * 方法功能：<br>
	 * 		儅訪問的是一個用戶名的時，我們進入用戶的主頁<br>
	 * @param username
	 * @return
	 */
	@GetMapping("/{username}")
	public String userSpace(@PathVariable("username") String username) {
		System.out.println(username);
		//返回用戶頁面
		return "/bloguser";
	}
	
	/**
	 * 方法功能：<br>
	 * 		查看某個用戶下博客
	 * 
	 * @param username		用戶名稱
	 * @param order			用戶博客排序，默認根據最新排序
	 * @param category		用戶博客分類
	 * @param keyword		用戶博客關鍵字搜索
	 * @return
	 */
	@GetMapping("/{username}/blogs")
	public String listBlogsByOrder(@PathVariable("username") String username,
			@RequestParam(value = "order", required = false, defaultValue = "new") String order,
			@RequestParam(value = "category", required = false) Long category,
			@RequestParam(value = "keyword", required = false) String keyword) {
		
		if(category != null) {
			System.out.println("category:"+category);
			System.out.println("selflink:"+"redirect:/userspace/"+username+"/blogs?category="+category);
			//返回用戶頁面
			return "/bloguser";
		}else if(keyword != null && keyword.isEmpty()==false) {
			System.out.println("keyword:"+keyword);
			System.out.println("selflink:"+"redirect:/userspace/"+username+"/blogs?keyword="+keyword);
			//返回用戶頁面
			return "/bloguser";
		}
		System.out.println("order:"+order);
		System.out.println("selflink:"+"redirect:/userspace/"+username+"/blogs?order="+order);
		//返回用戶頁面
		return "/bloguser";
	}
	/**
	 * 方法功能：<br>
	 * 		根據某個博客的id，獲取博客文章<br>
	 * @param id
	 * @return
	 */
	@GetMapping("/{username}/blogs/{id}")
	public String listBlogsByOrder(@PathVariable("id") Long id) {
		System.out.println("blogId:"+id);
		//返回博客文章頁面
		return "/blog";
	}
	/**
	 * 方法功能：<br>
	 * 		同過url跳轉到博客編輯頁面
	 * @return
	 */
	@GetMapping("/{username}/blgos/edit")
	public String editBlog() {
		//博客編輯頁面
		return "/blogeidt";
	}
}
