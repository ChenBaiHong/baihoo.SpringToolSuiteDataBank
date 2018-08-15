package com.baihoo.elasticSearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baihoo.elasticSearch.domain.EsBlog;
import com.baihoo.elasticSearch.repository.EsBlogRepository;

/**
 * EsBlog控制层
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/esBlogs")
public class EsBlogController {
	
	//注入
	@Autowired
	@Qualifier("esBlogRepository")
	private EsBlogRepository esBlogRepository;
	
	@GetMapping("/list")
	public List<EsBlog> list(@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "summary", required = true) String summary,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "page", defaultValue="0", required = true) Integer page,
			@RequestParam(value = "size", defaultValue="10" , required = true) Integer size
			) {
		
		Pageable pageable = PageRequest.of(page, size);
		//需要先在com.baihoo.elasticSearch.repository.EsBlogRepositoryTest.initEsRepositoryData()中初始化数据
		Page<EsBlog> pageEsBlog = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);
		return pageEsBlog.getContent();
	}
}
