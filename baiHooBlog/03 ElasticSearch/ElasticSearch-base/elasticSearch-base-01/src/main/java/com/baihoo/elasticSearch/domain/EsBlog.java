package com.baihoo.elasticSearch.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
/**
 * 博客類
 * @author Administrator
 *
 */
@Document(indexName = "blog" , type="blog") //博客文檔 ，spring-elasticSearch該注解介紹：indexName索引的名稱,type類型
public class EsBlog  implements Serializable{
	/**
	 * @param id 主鍵
	 * @param title 標題
	 * @param summary 摘要
	 * @param content 内容
	 */
	@Id 
	private String id;
	@Column
	private String title;
	@Column
	private String summary;
	@Column
	private String content;
	protected EsBlog() {//JPA規範要求，防止直接使用
		
	}
	public EsBlog(String title, String summary, String content) {
		super();
		this.title = title;
		this.summary = summary;
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		
		return String.format("EsBlog[id='%s' , summary='%s' , title='%s' , content='%s']", 
									id , title , summary , content);
	}
}
