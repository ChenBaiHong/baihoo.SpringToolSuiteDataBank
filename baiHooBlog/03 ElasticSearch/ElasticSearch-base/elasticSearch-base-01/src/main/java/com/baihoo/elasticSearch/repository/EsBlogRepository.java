package com.baihoo.elasticSearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.baihoo.elasticSearch.domain.EsBlog;

/**
 * 繼承全文檢索的倉庫接口類
 * 			Type Parameters: <T> <ID>
 * 										數據映射實體 ， 實體逐漸類型
 * @author Administrator
 *	
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog , String>{
	
	/**
	 * <p>關鍵字全文搜索</p>
	 * springBoot -ElasticSearch 提供更具映射对象实体智能匹配查询的功能，遵循默认的规则<br>
	 * 		曲意解釋：<br>
	 * 			分頁查詢博客EsBlog（去重），若title包含關鍵字或者summary包含關鍵字或者Content包含關鍵字匹配查詢，再最終獲得其結果集<br>
	 * @param title
	 * @param summary
	 * @param content
	 * @param page
	 * @return
	 */
	Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(String title , String summary , String content ,  Pageable pageable);
}
