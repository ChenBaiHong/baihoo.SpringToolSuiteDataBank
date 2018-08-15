package com.baihoo.elasticSearch.repository;



import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.baihoo.elasticSearch.domain.EsBlog;

/**
 * EsBlogRepository 接口測試
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBlogRepositoryTest {
	@Autowired
	private EsBlogRepository esBlogRepository;
	
	@Before
	public void initEsRepositoryData() {
		//避免髒數據，清除數據
		esBlogRepository.deleteAll();
		EsBlog blog1 = new EsBlog("登鸛雀樓" , "王之渙-登鸛雀樓" , "白日依山盡，黃河入海流 。欲窮千里目 ， 更上一層樓。");
		EsBlog blog2 = new EsBlog("相思" , "王維-相思" , "紅豆生南國 ， 春來發幾枝。愿君多采擷 ， 此物最相思。");
		EsBlog blog3 = new EsBlog("靜夜思" , "李白-靜夜思" , "床前明月光 ， 疑是地上霜 。 舉頭望明月 ， 低頭思故鄉 。");
		esBlogRepository.save(blog1);
		esBlogRepository.save(blog2);
		esBlogRepository.save(blog3);
	}
	@Test
	public void testFindDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining() {
		Pageable page = PageRequest.of(0, 10);
		String title = "相思";
		String summary = "相思";
		String content = "相思";
		Page<EsBlog> pageResult = esBlogRepository
				.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content,
						page);
		//静态断言方法
		assertThat(pageResult.getTotalElements()).isEqualTo(2);
		System.out.println("Es===============================================Start");
		pageResult.getContent().forEach(e -> System.out.println(e.toString()));
		System.out.println("Es===============================================end");
		
	}

}
