package com.baihoo.blog.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import com.baihoo.blog.convertor.CustomDateConverter;
import com.baihoo.blog.convertor.CustomTrimString;

/**
 * 
 * @author Administrator
 *
 */
@Configuration
public class Springmvc {
	
    /** 国际化文件路径 */
    @Value("${spring.messages.basename}")
    public String basename;
    
    /**
     * 
     * 通過轉換器服務類，添加自定義的轉換器
     * {@link ConversionServiceDeducer}
     * @return
     */
    @Bean
    public ConversionService conversionService() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new CustomDateConverter());
        service.addConverter(new CustomTrimString());
        return service;
    }
    /**
     * 用于解析消息的策略接口，支持这些消息的参数化和国际化。
     * 
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(basename);
        return messageSource;
    }
}
